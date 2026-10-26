package api.dto;

import java.time.LocalDate;

import api.model.EnumStatusMatricula;

public record MatriculaAlunoTurmaResponseDTO(
        Long idMatricula,
        Long idAluno,
        Long idCurso,
        LocalDate dataMatricula,
        EnumStatusMatricula status,
        Long idTurma
) {
}