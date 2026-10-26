package api.mapper;

import org.springframework.stereotype.Component;

import api.dto.AlunoRequestDTO;
import api.dto.AlunoResponseDTO;
import api.model.Aluno;
import api.model.Pessoa;

@Component 
public class AlunoMapper {
    
    public AlunoResponseDTO toResponse(Aluno aluno) {
        return new AlunoResponseDTO(
            aluno.getId(),
            aluno.getPessoa().getNome(),
            aluno.getPessoa().getCpf(),
            aluno.getPessoa().getEmail(),
            aluno.getMatricula()
        );
    }

    public Aluno toEntity(Pessoa pessoa, String matricula) {
        Aluno aluno = new Aluno(pessoa);
        aluno.setMatricula(matricula);
        return aluno;
    }

    public void toUpdate(Aluno aluno, AlunoRequestDTO alunoRequestDTO) {

        if (alunoRequestDTO.cpf() != null) {
            
        }

    }

}
