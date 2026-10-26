package api.dto;

import java.util.List;

public record ModuloComMateriasResponseDTO(
    long id,
    long idCurso,
    String nome,
    String descricao,
    int cargaHoraria,
    int ordem,
    List<MateriaResponse> materias
) {
}
