package api.service;

import api.dto.AtualizarModuloDTO;
import api.dto.ModuloRequestDTO;
import api.dto.ModuloResponse;
import api.mapper.ModuloMapper;
import api.model.Curso;
import api.model.Modulo;
import api.repository.CursoRepository;
import api.repository.MateriaRepository;
import api.repository.ModuloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;
    private final MateriaRepository materiaRepository;
    private final ModuloMapper mapper;

    public ModuloService(ModuloRepository moduloRepository, CursoRepository cursoRepository,
                         MateriaRepository materiaRepository, ModuloMapper mapper) {
        this.moduloRepository = moduloRepository;
        this.cursoRepository = cursoRepository;
        this.materiaRepository = materiaRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ModuloResponse criar(ModuloRequestDTO request) {
        Curso curso = cursoRepository.findById(request.idCurso())
                .orElseThrow(() -> new CursoNaoEncontradoException(request.idCurso()));

        if (moduloRepository.existsByCurso_IdAndOrdem(request.idCurso(), request.ordem())) {
            throw new RegraDeNegocioException(
                    "A ordem " + request.ordem() + " já está ocupada por outro módulo deste curso.");
        }

        Modulo modulo = mapper.toEntity(request, curso);
        return mapper.toResponse(moduloRepository.save(modulo));
    }

    @Transactional(readOnly = true)
    public List<ModuloResponse> listar() {
        return mapper.toResponseList(moduloRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ModuloResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarModulo(id));
    }

    @Transactional
    public ModuloResponse atualizar(Long id, AtualizarModuloDTO request) {
        Modulo modulo = buscarModulo(id);
        if (request.ordem() != null
                && moduloRepository.existsByCurso_IdAndOrdemAndIdNot(
                        modulo.getCurso().getId(), request.ordem(), id)) {
            throw new RegraDeNegocioException(
                    "A ordem " + request.ordem() + " já está ocupada por outro módulo deste curso.");
        }

        mapper.updateEntity(modulo, request);
        return mapper.toResponse(moduloRepository.save(modulo));
    }

    @Transactional
    public void excluir(Long id) {
        Modulo modulo = buscarModulo(id);
        if (materiaRepository.existsByModulo_Id(id) || moduloRepository.existeTurmaAssociada(id)) {
            throw new RegraDeNegocioException("Não é possível excluir um módulo que possui matérias ou turmas.");
        }
        moduloRepository.delete(modulo);
    }

    private Modulo buscarModulo(Long id) {
        return moduloRepository.findById(id)
                .orElseThrow(() -> new ModuloNaoEncontradoException(id));
    }
}
