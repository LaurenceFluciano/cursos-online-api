package api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.dto.PageResponse;
import api.dto.ProfessorRequestDTO;
import api.dto.ProfessorResponseDTO;
import api.mapper.PageMapper;
import api.mapper.ProfessorMapper;
import api.model.Pessoa;
import api.model.Professor;
import api.repository.PessoaRepository;
import api.repository.ProfessorRepository;

@Service 
public class ProfessorService {

    private final PessoaRepository pessoaRepository;
    private final ProfessorRepository professorRepository;

    private final ProfessorMapper professorMapper;
    private final PageMapper pageMapper;

    public ProfessorService(
        PessoaRepository pessoaRepository, 
        ProfessorRepository professorRepository, 
        ProfessorMapper professorMapper, 
        PageMapper pageMapper
    ) {
        this.pessoaRepository = pessoaRepository;
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
        this.pageMapper = pageMapper;
    }

    public ProfessorResponseDTO cadastrar(ProfessorRequestDTO requestDTO) {
        Pessoa pessoa = pessoaRepository.findByCpfOrEmail(requestDTO.cpf(), requestDTO.email())
            .map(pessoaExistente -> {

                if (!pessoaExistente.getCpf().equals(requestDTO.cpf())) {
                    throw new RegraDeNegocioException("O e-mail informado já está em uso por outra pessoa.");
                }

                if (!pessoaExistente.getEmail().equals(requestDTO.email())) {
                    throw new RegraDeNegocioException("O CPF informado já está em uso por outra pessoa.");
                }

                return pessoaExistente;
            })
            .orElseGet(() -> {
                Pessoa novaPessoa = new Pessoa(
                    requestDTO.cpf(),
                    requestDTO.nome(),
                    requestDTO.email()
                );
                return pessoaRepository.save(novaPessoa);
            });

        boolean jaEProfessor = professorRepository.existsByPessoaId(pessoa.getId());
        if (jaEProfessor) {
            throw new RegraDeNegocioException("Esta pessoa já está cadastrada como professor.");
        }

        Professor professor = this.professorMapper.toEntity(pessoa);

        professorRepository.save(professor);

        return this.professorMapper.toResponse(professor);
    }


    public PageResponse<ProfessorResponseDTO> listar(Pageable pageable) {
        Page<Professor> professores = this.professorRepository.findAll(pageable);
        return this.pageMapper.toResponse(professores, this.professorMapper::toResponse);
    }

    public ProfessorResponseDTO pegarPorId(Long id) {
        return this.professorMapper.toResponse(
            this.professorRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Professor não encontrado."))
        );
    }
}
