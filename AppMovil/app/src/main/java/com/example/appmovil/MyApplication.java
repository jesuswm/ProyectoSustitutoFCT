package com.example.appmovil;

import android.app.Application;

public class MyApplication {
    private static String tokens;
    private static String urlAutentificar="http://192.168.1.126:8080/RestProyect/rest/Usuarios/Autentificar";
    private static String urlRegistrar="http://192.168.1.126:8080/RestProyect/rest/Usuarios/Crear";
    private static String urlInfoUsuario="http://192.168.1.126:8080/RestProyect/rest/Usuarios/MiUsuario?token=";
    private static String urlPostsPropios="http://192.168.1.126:8080/RestProyect/rest/Posts/Propios?token=";
    private static String urlComentarios="http://192.168.1.126:8080/RestProyect/rest/Comentarios?token=";
    private static String urlBuscar="http://192.168.1.126:8080/RestProyect/rest/Usuarios/Buscar?token=";
    private static String urlAmigos="http://192.168.1.126:8080/RestProyect/rest/Amigos?token=";
    private static String urlPeticiones="http://192.168.1.126:8080/RestProyect/rest/Peticiones?token=";

    public static String getUrlAmigos() {
        return urlAmigos;
    }

    public static String getUrlPeticiones() {
        return urlPeticiones;
    }

    public static String getUrlBuscar() {
        return urlBuscar;
    }

    public static String getTokens() {
        return tokens;
    }

    public static void setTokens(String tokens) {
        MyApplication.tokens = tokens;
    }

    public static String getUrlAutentificar() {
        return urlAutentificar;
    }

    public static String getUrlRegistrar() {
        return urlRegistrar;
    }

    public static String getUrlInfoUsuario() {
        return urlInfoUsuario;
    }

    public static String getUrlPostsPropios() {
        return urlPostsPropios;
    }

    public static String getUrlComentarios() {
        return urlComentarios;
    }

}
