package api.dto;

import api.model.EnumTipoPeriodoTurma;
import api.model.EnumTurnoTurma;

import java.time.LocalDate;

public record TurmaResponseDTO(
        Long id,
        Long idModulo,
        Long idProfessor,
        String periodoLetivo,
        EnumTipoPeriodoTurma tipoPeriodo,
        EnumTurnoTurma turno,
        Integer vagas,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}