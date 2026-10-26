package api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ModuloRequestDTO(
    @NotNull(message = "O id do curso é obrigatório")
    @Positive(message = "O id do curso deve ser maior que zero")
    Long idCurso,

    @NotBlank(message = "O nome do módulo é obrigatório")
    @Size(min = 2, max = 128, message = "O nome do módulo deve conter entre 2 e 128 caracteres")
    String nome,

    @Size(min = 16, max = 1024, message = "A descrição do módulo deve conter entre 16 e 1024 caracteres")
    String descricao,

    @NotNull(message = "A carga horária do módulo é obrigatória")
    @Positive(message = "A carga horária do módulo deve ser maior que zero")
    Integer cargaHoraria,

    @NotNull(message = "A ordem do módulo é obrigatória")
    @Positive(message = "A ordem do módulo deve ser maior que zero")
    Integer ordem
) {
}
