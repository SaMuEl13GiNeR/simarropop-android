package com.sapato.simarropop.pojo;

import java.io.Serializable;

public class Categoria implements Serializable {
    private Long id;
    private String tipo;

    public Categoria(Long id) {
        this.id = id;
    }

    public Categoria() {

    }

    public Categoria(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
