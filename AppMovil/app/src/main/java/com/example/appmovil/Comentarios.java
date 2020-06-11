package com.example.appmovil;

import java.util.Date;

public class Comentarios {
    private int id;
    private int idCreador;
    private int idPost;
    private String contenido;
    private String autor;
    private Date fecha;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdCreador() {
        return idCreador;
    }
    public void setIdCreador(int idCreador) {
        this.idCreador = idCreador;
    }
    public int getIdPost() {
        return idPost;
    }
    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }
    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
}
