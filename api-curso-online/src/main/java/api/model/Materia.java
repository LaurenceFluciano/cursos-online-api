package api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "materia",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_modulo", "nome"}))
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_modulo", nullable = false)
    private Modulo modulo;

    @Size(min = 2, max = 128, message = "O nome da matéria deve conter entre 2 e 128 caracteres")
    @Column(name = "nome", nullable = false)
    private String nome;

    public Materia() {

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {this.id = id;}

    public Modulo getModulo() {
        return modulo;
    }
    public void setModulo(Modulo modulo) {this.modulo = modulo;}

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {this.nome = nome;}


}
