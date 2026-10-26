package api.service;

import api.dto.AtualizarTurmaRequestDTO;
import api.dto.PageResponse;
import api.dto.TurmaRequestDTO;
import api.dto.TurmaResponseDTO;
import api.mapper.PageMapper;
import api.mapper.TurmaMapper;
import api.model.EnumTipoPeriodoTurma;
import api.model.Modulo;
import api.model.Professor;
import api.model.Turma;
import api.repository.ModuloRepository;
import api.repository.ProfessorRepository;
import api.repository.TurmaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final ModuloRepository moduloRepository;
    private final ProfessorRepository professorRepository;

    private final TurmaMapper turmaMapper;
    private final PageMapper pageMapper;

    public TurmaService(
            TurmaRepository turmaRepository,
            ModuloRepository moduloRepository,
            ProfessorRepository professorRepository,
            TurmaMapper turmaMapper,
            PageMapper pageMapper
    ) {
        this.turmaRepository = turmaRepository;
        this.moduloRepository = moduloRepository;
        this.professorRepository = professorRepository;
        this.turmaMapper = turmaMapper;
        this.pageMapper = pageMapper;
    }

    @Transactional
    public TurmaResponseDTO cadastrar(TurmaRequestDTO request) {
        Modulo modulo = buscarModulo(request.idModulo());
        Professor professor = buscarProfessor(request.idProfessor());

        validarVagas(request.vagas());
        validarDataInicio(request.dataInicio());
        validarDataFim(request.dataInicio(), request.dataFim(), request.tipoPeriodo());

        Turma turma = new Turma();
        turma.setModulo(modulo);
        turma.setProfessor(professor);
        turma.setTipoPeriodo(request.tipoPeriodo());
        turma.setTurno(request.turno());
        turma.setVagas(request.vagas());
        turma.setDataInicio(request.dataInicio());
        turma.setDataFim(request.dataFim());
        turma.setPeriodoLetivo(gerarPeriodoLetivo(request.dataInicio(), request.tipoPeriodo()));

        return this.turmaMapper.toResponse(this.turmaRepository.save(turma));
    }

    @Transactional(readOnly = true)
    public PageResponse<TurmaResponseDTO> listar(Pageable pageable) {
        return this.pageMapper.toResponse(
                this.turmaRepository.findAll(pageable),
                this.turmaMapper::toResponse
        );
    }

    @Transactional(readOnly = true)
    public TurmaResponseDTO pegarPorId(Long id) {
        return this.turmaMapper.toResponse(buscarTurma(id));
    }

    @Transactional
    public TurmaResponseDTO atualizar(Long id, AtualizarTurmaRequestDTO request) {
        Turma turma = buscarTurma(id);

        if (request.idProfessor() != null) {
            turma.setProfessor(buscarProfessor(request.idProfessor()));
        }

        LocalDate dataInicio = (request.dataInicio() != null) ? request.dataInicio() : turma.getDataInicio();
        LocalDate dataFim = (request.dataFim() != null) ? request.dataFim() : turma.getDataFim();
        if (request.dataInicio() != null) {
            validarDataInicio(request.dataInicio());
        }

        if (request.vagas() != null) {
            validarVagas(request.vagas());
            turma.setVagas(request.vagas());
        }

        if (dataInicio != null || dataFim != null) {
            validarDataFim(dataInicio, dataFim, turma.getTipoPeriodo());
        }

        if (request.dataInicio() != null) {
            turma.setDataInicio(request.dataInicio());
        }
        if (request.dataFim() != null) {
            turma.setDataFim(request.dataFim());
        }

        turma.setPeriodoLetivo(gerarPeriodoLetivo(dataInicio != null ? dataInicio : turma.getDataInicio(), turma.getTipoPeriodo()));

        return this.turmaMapper.toResponse(this.turmaRepository.save(turma));
    }

    @Transactional
    public void deletar(Long id) {
        this.turmaRepository.delete(buscarTurma(id));
    }

    private Turma buscarTurma(Long id) {
        return this.turmaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma não encontrada."));
    }

    private Modulo buscarModulo(Long id) {
        return this.moduloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Módulo não encontrado."));
    }

    private Professor buscarProfessor(Long id) {
        return this.professorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Professor não encontrado."));
    }

    private void validarVagas(Integer vagas) {
        if (vagas == null || vagas <= 0) {
            throw new RegraDeNegocioException("As vagas devem ser maiores que zero.");
        }
    }

    private void validarDataInicio(LocalDate dataInicio) {
        if (dataInicio != null && dataInicio.isBefore(LocalDate.now())) {
            throw new RegraDeNegocioException("A data de início deve ser maior ou igual a hoje.");
        }
    }

    private void validarDataFim(LocalDate dataInicio, LocalDate dataFim, EnumTipoPeriodoTurma tipo) {
        if (dataFim == null) {
            return;
        }
        if (dataInicio == null) {
            throw new RegraDeNegocioException("Informe a data de início antes da data de fim.");
        }
        if (!dataFim.isAfter(dataInicio)) {
            throw new RegraDeNegocioException("A data de fim deve ser posterior à data de início.");
        }
        if (dataFim.isAfter(dataInicio.plusMonths(tipo.getMeses()))) {
            throw new RegraDeNegocioException(
                    "A data de fim excede a duração do período (" + tipo.name().toLowerCase() + ")."
            );
        }
    }

    private String gerarPeriodoLetivo(LocalDate dataInicio, EnumTipoPeriodoTurma tipo) {
        LocalDate base = (dataInicio != null) ? dataInicio : LocalDate.now();
        int numero = (base.getMonthValue() - 1) / tipo.getMeses() + 1;
        return base.getYear() + "/" + numero;
    }
}
