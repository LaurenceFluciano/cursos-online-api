package api.dto;

import api.model.Materia;

public class MateriaResponse {

    private long id;
    private long idModulo;
    private String nome;

    public MateriaResponse() {
    }

    public MateriaResponse(Materia materia) {
        this.id = materia.getId();
        this.idModulo = materia.getModulo().getId();
        this.nome = materia.getNome();
    }

    public long getId() { return id; }
    public long getIdModulo() { return idModulo; }
    public String getNome() { return nome; }
}
