package api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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

    @Column(name = "nome", nullable = false, length = 128)
    private String nome;

    protected Materia() {
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public Modulo getModulo() { return modulo; }
    public void setModulo(Modulo modulo) { this.modulo = modulo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
