package api.dto;

import api.model.EnumTipoPeriodoTurma;
import api.model.EnumTurnoTurma;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record TurmaRequestDTO(
        @NotNull(message = "O id do módulo é obrigatório")
        @Positive(message = "O id do módulo deve ser maior que zero")
        Long idModulo,

        @NotNull(message = "O id do professor é obrigatório")
        @Positive(message = "O id do professor deve ser maior que zero")
        Long idProfessor,

        @NotNull(message = "O tipo de período é obrigatório")
        EnumTipoPeriodoTurma tipoPeriodo,

        @NotNull(message = "O turno é obrigatório")
        EnumTurnoTurma turno,

        @NotNull(message = "A quantidade de vagas é obrigatória")
        @Positive(message = "As vagas devem ser maiores que zero")
        Integer vagas,

        LocalDate dataInicio,
        LocalDate dataFim
) {
}
