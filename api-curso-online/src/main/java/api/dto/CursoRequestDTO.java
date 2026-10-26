package api.dto;

import api.model.Curso.enumStatusCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CursoRequestDTO(
    @NotBlank(message = "O título do curso é obrigatório")
    @Size(min = 2, max = 128, message = "O título do curso deve conter entre 2 e 128 caracteres")
    String titulo,

    @Size(min = 2, max = 255, message = "O subtítulo do curso deve conter entre 2 e 255 caracteres")
    String subtitulo,

    @Size(min = 16, max = 1024, message = "O resumo do curso deve conter entre 16 e 1024 caracteres")
    String resumo,

    @NotBlank(message = "O slug do curso é obrigatório")
    @Size(min = 2, max = 128, message = "O slug do curso deve conter entre 2 e 128 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "O slug deve conter somente caracteres alfanuméricos e hífen")
    String slug,

    @NotNull(message = "O status do curso é obrigatório")
    enumStatusCurso status
) {
}
