package api.service;

import java.time.Year;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import api.dto.AlunoRequestDTO;
import api.dto.AlunoResponseDTO;
import api.dto.PageResponse;
import api.mapper.AlunoMapper;
import api.mapper.PageMapper;
import api.model.Aluno;
import api.model.Pessoa;
import api.repository.AlunoRepository;
import api.repository.PessoaRepository;

@Service 
public class AlunoService {
    
    private final PessoaRepository pessoaRepository;
    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;
    private final PageMapper pageMapper;

    public AlunoService(PessoaRepository pessoaRepository, AlunoRepository alunoRepository, AlunoMapper alunoMapper, PageMapper pageMapper) {
        this.pessoaRepository = pessoaRepository;
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
        this.pageMapper = pageMapper;
    }

    public AlunoResponseDTO cadastrar(AlunoRequestDTO requestDTO) {
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

        boolean jaEAluno = alunoRepository.existsByPessoaId(pessoa.getId());
        if (jaEAluno) {
            throw new RegraDeNegocioException("Esta pessoa já está cadastrada como aluno.");
        }

        Aluno aluno = this.alunoMapper.toEntity(pessoa, geradorDeMatricula(pessoa.getId()));
        alunoRepository.save(aluno);

        return this.alunoMapper.toResponse(aluno);
    }


    public PageResponse<AlunoResponseDTO> listar(Pageable pageable) {
        Page<Aluno> alunos = this.alunoRepository.findAll(pageable);
        return this.pageMapper.toResponse(alunos, this.alunoMapper::toResponse);
    }

    public AlunoResponseDTO pegarPorId(Long id) {
        return this.alunoMapper.toResponse(
            this.alunoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado."))
        );
    }


    // MELHORIAS-01
    //
    // Separar em um service ou camada especifica
    // Melhoróar a logica de geração de matrícula
    //
    private String geradorDeMatricula(Long idPessoa) {
        int anoAtual = Year.now().getValue();
        
        Random random = new Random();
        int digitoPeriodo = random.nextInt(3);
        
        int numeroAleatorio = random.nextInt(10000); 
        String quatroAleatorios = String.format("%04d", numeroAleatorio);
        
        return String.valueOf(anoAtual) + 
            digitoPeriodo + 
            idPessoa + 
            quatroAleatorios;
    }
}
