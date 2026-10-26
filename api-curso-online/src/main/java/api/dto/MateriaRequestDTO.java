package api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MateriaRequestDTO(
    @NotNull(message = "O id do módulo é obrigatório")
    @Positive(message = "O id do módulo deve ser maior que zero")
    Long idModulo,

    @NotBlank(message = "O nome da matéria é obrigatório")
    @Size(min = 2, max = 128, message = "O nome da matéria deve conter entre 2 e 128 caracteres")
    String nome
) {
}
