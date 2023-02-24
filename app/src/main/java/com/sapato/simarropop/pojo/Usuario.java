package com.sapato.simarropop.pojo;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {
    private Long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasenya;
    private String avatar;
    private float latitud;
    private float longitud;
    /*private List<Articulo> listaArticulos;
    private List<Mensaje> mensajesEmisor;
    private List<Mensaje> mensajesReceptor;*/

    public Usuario(){

    }

    public Usuario(Long id){
        this.id = id;
    }

    public Usuario(Long id, String nombre, String apellidos, String correo, String contrasenya, String avatar, float latitud, float longitud) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasenya = contrasenya;
        this.avatar = avatar;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Usuario(String nombre){
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
/*
    public List<Articulo> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulo> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public List<Mensaje> getMensajesEmisor() {
        return mensajesEmisor;
    }

    public void setMensajesEmisor(List<Mensaje> mensajesEmisor) {
        this.mensajesEmisor = mensajesEmisor;
    }

    public List<Mensaje> getMensajesReceptor() {
        return mensajesReceptor;
    }

    public void setMensajesReceptor(List<Mensaje> mensajesReceptor) {
        this.mensajesReceptor = mensajesReceptor;
    }*/

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasenya='" + contrasenya + '\'' +
                ", avatar='" + avatar + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}