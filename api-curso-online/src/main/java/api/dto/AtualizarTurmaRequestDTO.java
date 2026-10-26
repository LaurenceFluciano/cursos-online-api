package api.dto;

import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AtualizarTurmaRequestDTO(
        @Positive(message = "O id do professor deve ser maior que zero")
        Long idProfessor,

        @Positive(message = "As vagas devem ser maiores que zero")
        Integer vagas,

        LocalDate dataInicio,
        LocalDate dataFim
) {
}
