package api.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AtualizarModuloDTO(
    @Size(min = 2, max = 128, message = "O nome do módulo deve conter entre 2 e 128 caracteres")
    String nome,

    @Size(min = 16, max = 1024, message = "A descrição do módulo deve conter entre 16 e 1024 caracteres")
    String descricao,

    @Positive(message = "A carga horária do módulo deve ser maior que zero")
    Integer cargaHoraria,

    @Positive(message = "A ordem do módulo deve ser maior que zero")
    Integer ordem
) {
}
