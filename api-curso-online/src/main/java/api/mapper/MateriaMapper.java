package api.mapper;

import api.dto.MateriaRequestDTO;
import api.dto.MateriaResponse;
import api.model.Materia;
import api.model.Modulo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MateriaMapper {

    public Materia toEntity(MateriaRequestDTO request, Modulo modulo) {
        return new Materia(modulo, request.nome());
    }

    public MateriaResponse toResponse(Materia materia) {
        return new MateriaResponse(materia);
    }

    public List<MateriaResponse> toResponseList(Iterable<Materia> materias) {
        List<MateriaResponse> respostas = new ArrayList<>();
        for (Materia materia : materias) {
            respostas.add(toResponse(materia));
        }
        return List.copyOf(respostas);
    }
}
