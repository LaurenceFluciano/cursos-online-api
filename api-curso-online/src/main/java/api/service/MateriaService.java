package api.service;

import api.dto.MateriaRequestDTO;
import api.dto.MateriaResponse;
import api.mapper.MateriaMapper;
import api.model.Curso;
import api.model.Materia;
import api.model.Modulo;
import api.repository.MateriaRepository;
import api.repository.ModuloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// CAMADA: Service
// RESPONSABILIDADE: regras de negócio dos materias.
// REGRAS: C3
// ROTAS ATENDIDAS: as de /materias/{id}  em docs/contrato-da-api.md.
// SITUAÇÃO: montada. A lógica entra quando a rota for implementada.
@Service
public class MateriaService {

    private final MateriaRepository materiaRepository;
    private final ModuloRepository moduloRepository;
    private final MateriaMapper mapper;

    public MateriaService(MateriaRepository materiaRepository, ModuloRepository moduloRepository,
                          MateriaMapper mapper) {
        this.materiaRepository = materiaRepository;
        this.moduloRepository = moduloRepository;
        this.mapper = mapper;
    }

    @Transactional
    public MateriaResponse criar(MateriaRequestDTO request) {
        Modulo modulo = moduloRepository.findById(request.idModulo())
                .orElseThrow(() -> new ModuloNaoEncontradoException(request.idModulo()));

        if (materiaRepository.existsByModulo_IdAndNome(request.idModulo(), request.nome())) {
            throw new RegraDeNegocioException(
                    "Já existe uma matéria com esse nome dentro do módulo informado.");
        }

        Materia materia = mapper.toEntity(request, modulo);
        return mapper.toResponse(materiaRepository.save(materia));
    }

    @Transactional(readOnly = true)
    public List<MateriaResponse> listar() {
        return mapper.toResponseList(materiaRepository.findAll());
    }

    @Transactional(readOnly = true)
    public MateriaResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarMateria(id));
    }

    @Transactional
    public void excluir(Long id) {
        Materia materia = buscarMateria(id);
        Curso curso = materia.getModulo().getCurso();
        if (curso.getStatus() == Curso.enumStatusCurso.PUBLICADO
                && materiaRepository.countByModulo_Curso_Id(curso.getId()) == 1) {
            throw new RegraDeNegocioException(
                    "Não é possível remover a última matéria de um curso publicado.");
        }
        materiaRepository.delete(materia);
    }

    private Materia buscarMateria(Long id) {
        return materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaNaoEncontradaException(id));
    }
}
