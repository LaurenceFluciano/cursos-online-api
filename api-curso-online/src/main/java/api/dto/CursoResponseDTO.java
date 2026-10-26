package api.dto;

import api.model.Curso;

public class CursoResponseDTO {
    private long id;
    private String titulo;
    private String subtitulo;
    private String resumo;
    private String slug;
    private String status;

    public CursoResponseDTO() {

    }

    public CursoResponseDTO(Curso curso) {
        this.id = curso.getId();
        this.titulo = curso.getTitulo();
        this.subtitulo = curso.getSubtitulo();
        this.resumo = curso.getResumo();
        this.slug = curso.getSlug();
        this.status = curso.getStatus().name();
    }

    public long getId() {return id;}
    public String getTitulo() {return titulo;}
    public String getSubtitulo() {return subtitulo;}
    public String getResumo() {return resumo;}
    public String getSlug() {return slug;}
    public String getStatus() {return status;}

}
