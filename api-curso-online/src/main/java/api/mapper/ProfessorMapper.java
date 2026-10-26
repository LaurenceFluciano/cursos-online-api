package api.mapper;

import org.springframework.stereotype.Component;

import api.dto.ProfessorResponseDTO;
import api.model.Pessoa;
import api.model.Professor;

@Component 
public class ProfessorMapper {
    
    public ProfessorResponseDTO toResponse(Professor professor) {
        return new ProfessorResponseDTO(
            professor.getId(),
            professor.getPessoa().getNome(),
            professor.getPessoa().getCpf(),
            professor.getPessoa().getEmail()
        );
    }

    public Professor toEntity(Pessoa pessoa) {
        Professor professor = new Professor(pessoa);
        return professor;
    }
}
