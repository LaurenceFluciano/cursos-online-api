package api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MatricularAlunoRequestDTO(
        @NotNull(message = "O id do aluno é obrigatório")
        @Positive(message = "O id do aluno deve ser maior que zero")
        Long idAluno,

        @NotNull(message = "O id do curso é obrigatório")
        @Positive(message = "O id do curso deve ser maior que zero")
        Long idCurso
) {
}
