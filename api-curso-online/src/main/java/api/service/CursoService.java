package api.service;

import api.dto.AtualizarCursoRequestDTO;
import api.dto.CursoCompletoResponseDTO;
import api.dto.CursoRequestDTO;
import api.dto.CursoResponseDTO;
import api.dto.ModuloComMateriasResponseDTO;
import api.mapper.CursoMapper;
import api.mapper.MateriaMapper;
import api.mapper.ModuloMapper;
import api.model.Curso;
import api.repository.CursoRepository;
import api.repository.MateriaRepository;
import api.repository.ModuloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final ModuloRepository moduloRepository;
    private final MateriaRepository materiaRepository;
    private final CursoMapper mapper;
    private final ModuloMapper moduloMapper;
    private final MateriaMapper materiaMapper;

    public CursoService(CursoRepository cursoRepository, ModuloRepository moduloRepository,
                        MateriaRepository materiaRepository, CursoMapper mapper,
                        ModuloMapper moduloMapper, MateriaMapper materiaMapper) {
        this.cursoRepository = cursoRepository;
        this.moduloRepository = moduloRepository;
        this.materiaRepository = materiaRepository;
        this.mapper = mapper;
        this.moduloMapper = moduloMapper;
        this.materiaMapper = materiaMapper;
    }

    @Transactional
    public CursoResponseDTO criar(CursoRequestDTO request) {
        if (cursoRepository.existsBySlug(request.slug())) {
            throw new RegraDeNegocioException("Já existe um curso com o slug: " + request.slug());
        }
        if (request.status() != Curso.enumStatusCurso.RASCUNHO) {
            throw new RegraDeNegocioException(
                    "Um curso deve ser criado como RASCUNHO e só pode mudar de status depois de cadastrado.");
        }

        Curso curso = mapper.toEntity(request);
        return mapper.toResponse(cursoRepository.save(curso));
    }

    @Transactional(readOnly = true)
    public List<CursoResponseDTO> listar() {
        return mapper.toResponseList(cursoRepository.findAll());
    }

    @Transactional(readOnly = true)
    public CursoCompletoResponseDTO buscarPorId(Long id) {
        return montarRespostaCompleta(buscarCurso(id));
    }

    @Transactional(readOnly = true)
    public CursoCompletoResponseDTO buscarPorSlug(String slug) {
        Curso curso = cursoRepository.findBySlug(slug)
                .orElseThrow(() -> new CursoNaoEncontradoException(slug));
        return montarRespostaCompleta(curso);
    }

    @Transactional(readOnly = true)
    public CursoCompletoResponseDTO buscarPorIdentificador(String identificador) {
        try {
            return buscarPorId(Long.valueOf(identificador));
        } catch (NumberFormatException | CursoNaoEncontradoException excecao) {
            return buscarPorSlug(identificador);
        }
    }

    @Transactional
    public CursoResponseDTO atualizar(Long id, AtualizarCursoRequestDTO request) {
        Curso curso = buscarCurso(id);
        if (request.slug() != null && cursoRepository.existsBySlugAndIdNot(request.slug(), id)) {
            throw new RegraDeNegocioException("Já existe um curso com o slug: " + request.slug());
        }
        validarMudancaDeStatus(curso, request.status());

        mapper.updateEntity(curso, request);
        return mapper.toResponse(cursoRepository.save(curso));
    }

    @Transactional
    public void excluir(Long id) {
        Curso curso = buscarCurso(id);
        if (moduloRepository.existsByCurso_Id(id)) {
            throw new RegraDeNegocioException("Não é possível excluir um curso que possui módulos.");
        }
        cursoRepository.delete(curso);
    }

    private Curso buscarCurso(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoNaoEncontradoException(id));
    }

    private void validarMudancaDeStatus(Curso curso, Curso.enumStatusCurso novoStatus) {
        if (novoStatus == null || novoStatus == curso.getStatus()) {
            return;
        }
        if (novoStatus == Curso.enumStatusCurso.PUBLICADO) {
            boolean possuiModulo = moduloRepository.existsByCurso_Id(curso.getId());
            boolean possuiMateria = materiaRepository.existsByModulo_Curso_Id(curso.getId());
            if (!possuiModulo || !possuiMateria) {
                throw new RegraDeNegocioException(
                        "Um curso só pode ser publicado quando tiver pelo menos um módulo e uma matéria.");
            }
        }
        if (novoStatus == Curso.enumStatusCurso.ARQUIVADO
                && curso.getStatus() != Curso.enumStatusCurso.PUBLICADO) {
            throw new RegraDeNegocioException("Um curso só pode ser arquivado depois de ter sido publicado.");
        }
    }

    private CursoCompletoResponseDTO montarRespostaCompleta(Curso curso) {
        List<ModuloComMateriasResponseDTO> modulos = moduloRepository.findAllByCurso_Id(curso.getId())
                .stream()
                .map(modulo -> moduloMapper.toCompleteResponse(modulo,
                        materiaMapper.toResponseList(materiaRepository.findAllByModulo_Id(modulo.getId()))))
                .toList();
        return mapper.toCompleteResponse(curso, modulos);
    }
}
