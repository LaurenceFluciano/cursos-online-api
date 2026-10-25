package api.service;

import java.time.Year;
import java.util.Random;

import org.springframework.stereotype.Service;

import api.dto.AlunoRequestDTO;
import api.dto.AlunoResponseDTO;
import api.mapper.AlunoMapper;
import api.model.Aluno;
import api.model.Pessoa;
import api.repository.AlunoRepository;
import api.repository.PessoaRepository;

@Service 
public class AlunoService {
    
    private final PessoaRepository pessoaRepository;
    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;

    public AlunoService(PessoaRepository pessoaRepository, AlunoRepository alunoRepository, AlunoMapper alunoMapper) {
        this.pessoaRepository = pessoaRepository;
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
    }

    public AlunoResponseDTO cadastrar(AlunoRequestDTO requestDTO) {
        Pessoa pessoa = pessoaRepository.findByCpf(requestDTO.cpf())
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
