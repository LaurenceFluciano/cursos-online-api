package api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessorRequestDTO(
     
    @NotBlank 
    @Size(min = 2, max = 255, message = "Nome deve ter no mínimo 2 e no máximo 255 caracteres.") 
    String nome,
    
    @NotBlank
    @Size(min = 11, max = 11, message = "Cpf deve ter 11 caracteres.")
    String cpf,

    @Email 
    @NotBlank 
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres.") 
    String email
) {
    
}
