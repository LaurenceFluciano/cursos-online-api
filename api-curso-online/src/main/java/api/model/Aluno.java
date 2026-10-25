
@entity
@table(name = "aluno")
public class aluno{

    @Id
    @Generatedvalue(strategy = Generationtype.IDENTITY )
    private long Id

    @Column (name = cpf, nullable = false, unique = true )
    private String cpf

    @Column (name = nome, nullable = false)
    private String nome

    @Column (name = email, nullable = false, unique = true )
    private String email

    @Column (name = matricula, nullable = false, unique = true )
    private String matricula

    public aluno () {

    }

    public aluno(String cpf, String nome, String email, String matricula){
        this.cpf = cpf
        this.nome = nome
        this.email = email
        this.matricula = matricula
    }

public long getId{
    return Id:
}

public long setId(long id){
    this.id = id;
}

public String getCpf{
    return cpf:
}

public String setCpf(String cpf){
    this.cpf = cpf;
}

public String getNome{
    return nome:
}

public String setNome(String nome){
    this.nome = nome;
}

public String getEmail{
    return email:
}

public String setEmail(String email){
    this.email = email;
}

public String getMatricula{
    return matriculal:
}

public String setMatricula(String matricula){
    this.matricula = matricula;
}




}