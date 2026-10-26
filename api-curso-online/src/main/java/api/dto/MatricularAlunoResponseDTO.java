package api.dto;

import api.model.EnumStatusMatricula;

import java.time.LocalDate;

public record MatricularAlunoResponseDTO(
        Long idMatricula,
        Long idAluno,
        Long idCurso,
        Long idTurma,
        LocalDate dataMatricula,
        EnumStatusMatricula status
) {
}
