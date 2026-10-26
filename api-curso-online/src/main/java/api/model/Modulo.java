package api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "modulo",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_curso", "ordem"})
)
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "curso", nullable = false)
    private Curso curso;

    @Size(min = 2, max = 128, message = "O nome do modulo deve conter entre 2 e 128 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Size(min = 16, max =1024, message = "a descrição do modulo deve conter entre 16 e 1024 caracteres")
    @Column(name = "descricao", nullable = true)
    private String descricao;

    @Min(value = 1, message = "A carga horária do modulo deve ser maior que 0")
    @Column(name = "cargaHoraria", nullable = false)
    private int cargaHoraria;

    @Min(value = 1, message = "A ordem do modulo deve ser maior que 0")
    @Column(name = "ordem", nullable = false)
    private int ordem;

    public Modulo() {

    }

    public long getId() { return id; }
    public void setId(long Id){ this.id=Id; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso Curso){ this.curso=Curso; }

    public String getNome() { return nome; }
    public void setNome(String Nome){ this.nome=Nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String Descricao){ this.descricao=Descricao; }

    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria( int cargaHoraria) {this.cargaHoraria=cargaHoraria; }

    public int getOrdem() { return ordem; }
    public void setOrdem(int ordem) { this.ordem = ordem; }

}
