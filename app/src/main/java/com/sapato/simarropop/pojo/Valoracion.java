package com.sapato.simarropop.pojo;

import java.io.Serializable;

public class Valoracion implements Serializable {
    private Long id;
    private int estrellas;
    private String opinion;
    private Usuario usuarioEmisor;
    private Usuario usuarioReceptor;

    public Valoracion(Long id, int estrellas, String opinion, Usuario usuarioEmisor, Usuario usuarioReceptor) {
        this.id = id;
        this.estrellas = estrellas;
        this.opinion = opinion;
        this.usuarioEmisor = usuarioEmisor;
        this.usuarioReceptor = usuarioReceptor;
    }

    public Valoracion(int estrellas, String opinion, Usuario usuarioEmisor, Usuario usuarioReceptor) {
        this.estrellas = estrellas;
        this.opinion = opinion;
        this.usuarioEmisor = usuarioEmisor;
        this.usuarioReceptor = usuarioReceptor;
    }

    public Valoracion(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Usuario getUsuarioEmisor() {
        return usuarioEmisor;
    }

    public void setUsuarioEmisor(Usuario usuarioEmisor) {
        this.usuarioEmisor = usuarioEmisor;
    }

    public Usuario getUsuarioReceptor() {
        return usuarioReceptor;
    }

    public void setUsuarioReceptor(Usuario usuarioReceptor) {
        this.usuarioReceptor = usuarioReceptor;
    }
}
