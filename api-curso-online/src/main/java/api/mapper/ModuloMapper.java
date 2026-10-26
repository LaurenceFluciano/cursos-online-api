package api.mapper;

import api.dto.AtualizarModuloDTO;
import api.dto.MateriaResponse;
import api.dto.ModuloComMateriasResponseDTO;
import api.dto.ModuloRequestDTO;
import api.dto.ModuloResponse;
import api.model.Curso;
import api.model.Modulo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModuloMapper {

    public Modulo toEntity(ModuloRequestDTO request, Curso curso) {
        return new Modulo(curso, request.nome(), request.descricao(), request.cargaHoraria(), request.ordem());
    }

    public ModuloResponse toResponse(Modulo modulo) {
        return new ModuloResponse(modulo);
    }

    public ModuloComMateriasResponseDTO toCompleteResponse(Modulo modulo, List<MateriaResponse> materias) {
        return new ModuloComMateriasResponseDTO(modulo.getId(), modulo.getCurso().getId(), modulo.getNome(),
                modulo.getDescricao(), modulo.getCargaHoraria(), modulo.getOrdem(), List.copyOf(materias));
    }

    public List<ModuloResponse> toResponseList(Iterable<Modulo> modulos) {
        List<ModuloResponse> respostas = new ArrayList<>();
        for (Modulo modulo : modulos) {
            respostas.add(toResponse(modulo));
        }
        return List.copyOf(respostas);
    }

    public void updateEntity(Modulo modulo, AtualizarModuloDTO request) {
        if (request.nome() != null) modulo.setNome(request.nome());
        if (request.descricao() != null) modulo.setDescricao(request.descricao());
        if (request.cargaHoraria() != null) modulo.setCargaHoraria(request.cargaHoraria());
        if (request.ordem() != null) modulo.setOrdem(request.ordem());
    }
}
