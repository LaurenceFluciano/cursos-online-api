package api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "Curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, max = 128, message = "O titulo do curso deve conter entre 2 e 128 caracteres")
    @Column(name = "titulo", nullable = false, unique = false)
    private String titulo;

    @Size(min = 2, max = 255, message = "O subtítulo do curso deve conter entre 2 e 255 caracteres")
    @Column(name = "subtitulo", nullable = true, unique = false)
    private String subtitulo;

    @Size(min = 16, max = 1024, message = "O resumo do curso deve conter entre 16 e 1024 caracteres")
    @Column(name = "resumo", nullable = true, unique = false)
    private String resumo;

    @Size(min = 2, max = 128, message = "O slug do curso deve conter entre 2 e 128 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "O slug do curso deve conter somente caracteres alfanuméricos e '-' ")
    @Column(name = "slug", nullable = false, unique = false)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, unique = false)
    private enumStatusCurso status;

    public enum enumStatusCurso {
        RASCUNHO,
        PUBLICADO,
        ARQUIVADO
    }

    public Curso() {

    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getTitulo() {return titulo;}
    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getSubtitulo() {return subtitulo;}
    public void setSubtitulo(String subtitulo) {this.subtitulo = subtitulo;}

    public String getResumo() {return resumo;}
    public void setResumo(String resumo) {this.resumo = resumo;}

    public String getSlug() {return slug;}
    public void setSlug(String slug) {this.slug = slug;}

    public enumStatusCurso getStatus() {return status;}
    public void setStatus(enumStatusCurso status) {this.status = status;}

}
