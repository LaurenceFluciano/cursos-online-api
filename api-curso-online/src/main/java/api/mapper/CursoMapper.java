package api.mapper;

import api.dto.AtualizarCursoRequestDTO;
import api.dto.CursoCompletoResponseDTO;
import api.dto.CursoRequestDTO;
import api.dto.CursoResponseDTO;
import api.dto.ModuloComMateriasResponseDTO;
import api.model.Curso;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CursoMapper {

    public Curso toEntity(CursoRequestDTO request) {
        return new Curso(request.titulo(), request.subtitulo(), request.resumo(), request.slug(), request.status());
    }

    public CursoResponseDTO toResponse(Curso curso) {
        return new CursoResponseDTO(curso);
    }

    public CursoCompletoResponseDTO toCompleteResponse(Curso curso, List<ModuloComMateriasResponseDTO> modulos) {
        return new CursoCompletoResponseDTO(curso.getId(), curso.getTitulo(), curso.getSubtitulo(),
                curso.getResumo(), curso.getSlug(), curso.getStatus().name(), List.copyOf(modulos));
    }

    public List<CursoResponseDTO> toResponseList(Iterable<Curso> cursos) {
        List<CursoResponseDTO> respostas = new ArrayList<>();
        for (Curso curso : cursos) {
            respostas.add(toResponse(curso));
        }
        return List.copyOf(respostas);
    }

    public void updateEntity(Curso curso, AtualizarCursoRequestDTO request) {
        if (request.titulo() != null) curso.setTitulo(request.titulo());
        if (request.subtitulo() != null) curso.setSubtitulo(request.subtitulo());
        if (request.resumo() != null) curso.setResumo(request.resumo());
        if (request.slug() != null) curso.setSlug(request.slug());
        if (request.status() != null) curso.setStatus(request.status());
    }
}
