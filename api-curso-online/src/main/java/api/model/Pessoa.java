package api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "pessoa")
public class Pessoa {
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column (name = "cpf", nullable = false, unique = true )
    private String cpf;

    @Column (name = "nome", nullable = false)
    private String nome;

    @Column (name = "email", nullable = false, unique = true )
    private String email;

    protected Pessoa() { }

    public Pessoa(String cpf, String nome, String email) {
        this.setCpf(cpf);
        this.setNome(nome);
        this.setEmail(email);
    }

    public Long getId(){
        return Id;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
