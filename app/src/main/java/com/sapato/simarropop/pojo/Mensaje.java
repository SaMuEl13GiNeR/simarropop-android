package com.sapato.simarropop.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Mensaje implements Serializable {
    private Long id;
    private String contenido;
    private Timestamp hora;
    private Usuario usuarioEmisor;
    private Usuario usuarioReceptor;

    public Mensaje(){}

    public Mensaje(Long id, String contenido, Timestamp hora, Usuario usuarioEmisor, Usuario usuarioReceptor) {
        this.id = id;
        this.contenido = contenido;
        this.hora = hora;
        this.usuarioEmisor = usuarioEmisor;
        this.usuarioReceptor = usuarioReceptor;
    }

    public Mensaje(String contenido, Timestamp hora, Usuario usuarioEmisor, Usuario usuarioReceptor) {
        this.contenido = contenido;
        this.hora = hora;
        this.usuarioEmisor = usuarioEmisor;
        this.usuarioReceptor = usuarioReceptor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getHora() {
        return hora;
    }

    public void setHora(Timestamp hora) {
        this.hora = hora;
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