package api.service;

import api.dto.AtualizarStatusMatriculaDTO;
import api.dto.MatricularAlunoRequestDTO;
import api.dto.MatricularAlunoResponseDTO;
import api.dto.MatriculaAlunoTurmaResponseDTO;
import api.dto.PageResponse;
import api.mapper.MatriculaAlunoTurmaMapper;
import api.mapper.PageMapper;
import api.model.Aluno;
import api.model.Curso;
import api.model.EnumStatusMatricula;
import api.model.MatriculaAlunoTurma;
import api.model.Turma;
import api.repository.AlunoRepository;
import api.repository.CursoRepository;
import api.repository.MatriculaAlunoTurmaRepository;
import api.repository.TurmaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaAlunoTurmaService {

    private static final List<EnumStatusMatricula> STATUS_QUE_OCUPAM_VAGA =
            List.of(EnumStatusMatricula.MATRICULADO, EnumStatusMatricula.ATIVO);

    private final MatriculaAlunoTurmaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final TurmaRepository turmaRepository;

    private final MatriculaAlunoTurmaMapper matriculaMapper;
    private final PageMapper pageMapper;

    public MatriculaAlunoTurmaService(
            MatriculaAlunoTurmaRepository matriculaRepository,
            AlunoRepository alunoRepository,
            CursoRepository cursoRepository,
            TurmaRepository turmaRepository,
            MatriculaAlunoTurmaMapper matriculaMapper,
            PageMapper pageMapper
    ) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
        this.turmaRepository = turmaRepository;
        this.matriculaMapper = matriculaMapper;
        this.pageMapper = pageMapper;
    }

    @Transactional
    public MatricularAlunoResponseDTO cadastrar(MatricularAlunoRequestDTO request) {
        Aluno aluno = buscarAluno(request.idAluno());
        Curso curso = buscarCurso(request.idCurso());

        validarAlunoJaMatriculadoEmTurmaAtiva(aluno);
        Turma turma = buscarTurmaDisponivelParaCurso(curso, aluno);

        MatriculaAlunoTurma matricula = new MatriculaAlunoTurma();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        matricula.setDataMatricula(LocalDate.now());
        matricula.setStatus(EnumStatusMatricula.MATRICULADO);

        MatriculaAlunoTurma salvo = this.matriculaRepository.save(matricula);
        return new MatricularAlunoResponseDTO(
                salvo.getId(),
                aluno.getId(),
                curso.getId(),
                turma.getId(),
                salvo.getDataMatricula(),
                salvo.getStatus()
        );
    }

    @Transactional(readOnly = true)
    public PageResponse<MatriculaAlunoTurmaResponseDTO> listar(Pageable pageable) {
        return this.pageMapper.toResponse(
                this.matriculaRepository.findAll(pageable),
                this.matriculaMapper::toResponse
        );
    }

    @Transactional(readOnly = true)
    public MatriculaAlunoTurmaResponseDTO pegarPorId(Long id) {
        return this.matriculaMapper.toResponse(buscarMatricula(id));
    }

    @Transactional
    public MatriculaAlunoTurmaResponseDTO atualizarStatus(Long id, AtualizarStatusMatriculaDTO request) {
        MatriculaAlunoTurma matricula = buscarMatricula(id);

        if (matricula.getStatus() == EnumStatusMatricula.CONCLUIDO) {
            throw new RegraDeNegocioException("Matrícula concluída não pode mudar de status.");
        }

        boolean voltaAOcuparVaga = STATUS_QUE_OCUPAM_VAGA.contains(request.status())
                && !STATUS_QUE_OCUPAM_VAGA.contains(matricula.getStatus());
        if (voltaAOcuparVaga) {
            validarVagaDisponivel(matricula.getTurma());
        }

        matricula.setStatus(request.status());
        return this.matriculaMapper.toResponse(this.matriculaRepository.save(matricula));
    }

    @Transactional
    public void deletar(Long id) {
        this.matriculaRepository.delete(buscarMatricula(id));
    }

    private MatriculaAlunoTurma buscarMatricula(Long id) {
        return this.matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula não encontrada."));
    }

    private Aluno buscarAluno(Long id) {
        return this.alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado."));
    }

    private Curso buscarCurso(Long id) {
        return this.cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado."));
    }

    private Turma buscarTurmaDisponivelParaCurso(Curso curso, Aluno aluno) {
        List<Turma> turmas = this.turmaRepository.findByModuloCursoId(curso.getId());
        if (turmas.isEmpty()) {
            throw new RegraDeNegocioException("Não existe turma aberta para o curso informado.");
        }

        for (Turma turma : turmas) {
            if (!turmaAberta(turma)) {
                continue;
            }
            validarProfessorNaoEhAluno(aluno, turma);
            validarMatriculaDuplicada(aluno.getId(), turma.getId());
            validarVagaDisponivel(turma);
            validarTurnoJaOcupado(aluno, turma);
            return turma;
        }

        throw new RegraDeNegocioException("Não há turma disponível para o aluno no curso informado.");
    }

    private boolean turmaAberta(Turma turma) {
        if (turma.getDataInicio() == null || turma.getDataInicio().isAfter(LocalDate.now())) {
            return false;
        }
        return turma.getDataFim() == null || !turma.getDataFim().isBefore(LocalDate.now());
    }

    private void validarProfessorNaoEhAluno(Aluno aluno, Turma turma) {
        if (aluno.getPessoa() == null || turma.getProfessor() == null || turma.getProfessor().getPessoa() == null) {
            return;
        }
        if (turma.getProfessor().getPessoa().getId().equals(aluno.getPessoa().getId())) {
            throw new RegraDeNegocioException("Professor não pode se matricular na turma que está ministrando.");
        }
    }

    private void validarMatriculaDuplicada(Long idAluno, Long idTurma) {
        if (this.matriculaRepository.existsByAlunoIdAndTurmaId(idAluno, idTurma)) {
            throw new RegraDeNegocioException("O aluno já está matriculado nesta turma.");
        }
    }

    private void validarTurnoJaOcupado(Aluno aluno, Turma turma) {
        if (this.matriculaRepository.existsByAlunoIdAndStatusInAndTurmaTurnoAndTurmaPeriodoLetivo(
                aluno.getId(),
                STATUS_QUE_OCUPAM_VAGA,
                turma.getTurno(),
                turma.getPeriodoLetivo())) {
            throw new RegraDeNegocioException("O aluno já está matriculado em outra turma do mesmo turno/periodo.");
        }
    }

    private void validarVagaDisponivel(Turma turma) {
        long ocupadas = this.matriculaRepository.countByTurmaIdAndStatusIn(turma.getId(), STATUS_QUE_OCUPAM_VAGA);
        if (ocupadas >= turma.getVagas()) {
            throw new RegraDeNegocioException("A turma não possui vagas disponíveis.");
        }
    }

    private void validarAlunoJaMatriculadoEmTurmaAtiva(Aluno aluno) {
        if (this.matriculaRepository.existsByAlunoIdAndStatusIn(aluno.getId(), STATUS_QUE_OCUPAM_VAGA)) {
            throw new RegraDeNegocioException("O aluno já está matriculado em outra turma ativa.");
        }
    }
}
