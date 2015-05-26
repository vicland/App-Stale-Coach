package com.example.alumno.helloworld.Modelo;

import java.io.Serializable;

/**
 * Created by damonfor on 26/02/2015.
 */
public class Deporte implements Serializable{
    private int id_deporte;
    private String nombre;
    private String fondo;
    private String campo;

    public Deporte(int id_deporte, String nombre, String fondo, String campo) {
        this.id_deporte = id_deporte;
        this.nombre = nombre;
        this.fondo = fondo;
        this.campo = campo;
    }

    public int getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(int id_deporte) {
        this.id_deporte = id_deporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFondo() {
        return fondo;
    }

    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}