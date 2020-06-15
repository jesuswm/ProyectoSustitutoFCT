package com.example.appmovil;

public class Peticiones {
    private int id;
    private int idSolicitante;
    private int idSolicitado;
    String nombreSolicitante;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdSolicitante() {
        return idSolicitante;
    }
    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
    public int getIdSolicitado() {
        return idSolicitado;
    }
    public void setIdSolicitado(int idSolicitado) {
        this.idSolicitado = idSolicitado;
    }
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }
    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }
}
