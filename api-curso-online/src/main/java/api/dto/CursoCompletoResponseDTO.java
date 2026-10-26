package api.dto;

import java.util.List;

public record CursoCompletoResponseDTO(
    long id,
    String titulo,
    String subtitulo,
    String resumo,
    String slug,
    String status,
    List<ModuloComMateriasResponseDTO> modulos
) {
}
