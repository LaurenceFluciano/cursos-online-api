package api.dto;

public record ProfessorResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email
) {
    
}
