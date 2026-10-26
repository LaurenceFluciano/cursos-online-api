package api.dto;

import api.model.Modulo;

public class ModuloResponse {

    private long id;
    private long idCurso;
    private String nome;
    private String descricao;
    private int cargaHoraria;
    private int ordem;

public ModuloResponse(){

    }
     public ModuloResponse(Modulo modulo){
    this.id = modulo.getId();
    this.idCurso = modulo.getCurso().getId();
    this.nome = modulo.getNome();
    this.descricao = modulo.getDescricao();
    this.cargaHoraria = modulo.getCargaHoraria();
    this.ordem = modulo.getOrdem();

     }

     public long getId () {return id;}
    public long getIdCurso() { return idCurso;}
    public String getNome() { return nome;}
    public String getDescricao() { return descricao;}
    public int getCargaHoraria() { return cargaHoraria;}
    public int getOrdem() { return ordem;}
}
