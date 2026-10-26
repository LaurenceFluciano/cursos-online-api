package api.dto;

public record AlunoResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    String matricula
) {
    
}
