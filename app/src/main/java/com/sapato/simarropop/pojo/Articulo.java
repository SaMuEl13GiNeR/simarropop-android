package com.sapato.simarropop.pojo;


import java.io.Serializable;

public class Articulo implements Serializable {
    private Long id;
    private String titulo;
    private int likes;
    private String descripcion;
    private float precio;
    private String estado;
    private String foto;
    private boolean isVendido;
    private Usuario usuarioVendedor;
    private Usuario usuarioComprador;
    private Categoria categoria;


    public Articulo(){

    }


    public Articulo(Long id, String titulo, int likes, String descripcion, float precio, String estado, String foto, boolean isVendido, Usuario usuarioVendedor, Usuario usuarioComprador, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.likes = likes;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.foto = foto;
        this.isVendido = isVendido;
        this.usuarioVendedor = usuarioVendedor;
        this.usuarioComprador = usuarioComprador;
        this.categoria = categoria;
    }

    public Articulo(String titulo, int likes, String descripcion, float precio, String estado, String foto, boolean isVendido, Usuario usuarioVendedor, Categoria categoria) {
        this.titulo = titulo;
        this.likes = likes;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.foto = foto;
        this.isVendido = isVendido;
        this.usuarioVendedor = usuarioVendedor;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isVendido() {
        return isVendido;
    }

    public void setVendido(boolean vendido) {
        isVendido = vendido;
    }

    public Usuario getUsuarioVendedor() {
        return usuarioVendedor;
    }

    public void setUsuarioVendedor(Usuario usuarioVendedor) {
        this.usuarioVendedor = usuarioVendedor;
    }

    public Usuario getUsuarioComprador() {
        return usuarioComprador;
    }

    public void setUsuarioComprador(Usuario usuarioComprador) {
        this.usuarioComprador = usuarioComprador;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", likes=" + likes +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                ", foto='" + foto + '\'' +
                ", isVendido=" + isVendido +
                ", usuarioVendedor=" + usuarioVendedor +
                ", usuarioComprador=" + usuarioComprador +
                ", categoria=" + categoria +
                '}';
    }
}