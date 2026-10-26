package api.dto;


import api.model.EnumStatusMatricula;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusMatriculaDTO(
        @NotNull EnumStatusMatricula status
) {
}