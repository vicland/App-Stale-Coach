package com.example.alumno.helloworld.Modelo;

import java.io.Serializable;

/**
 * Created by damonfor on 05/03/2015.
 */
public class CategoriaEntrenamiento implements Serializable{
    private int id_cat;
    private String nombre;
    private String descripcion;
    private String foto;
    private Deporte deporte;

    public CategoriaEntrenamiento(int id_cat, String nombre, String descripcion, String foto, Deporte deporte) {
        this.id_cat = id_cat;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.deporte = deporte;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
}
