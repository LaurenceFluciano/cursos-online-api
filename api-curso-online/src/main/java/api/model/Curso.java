package api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "titulo", nullable = false, length = 128)
    private String titulo;

    @Column(name = "subtitulo", nullable = true, length = 255)
    private String subtitulo;

    @Column(name = "resumo", nullable = true, length = 1024)
    private String resumo;

    @Column(name = "slug", nullable = false, unique = true, length = 128)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 64)
    private enumStatusCurso status;

    public enum enumStatusCurso {
        RASCUNHO,
        PUBLICADO,
        ARQUIVADO
    }

    protected Curso() {
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSubtitulo() { return subtitulo; }
    public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

    public String getResumo() { return resumo; }
    public void setResumo(String resumo) { this.resumo = resumo; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public enumStatusCurso getStatus() { return status; }
    public void setStatus(enumStatusCurso status) { this.status = status; }
}
