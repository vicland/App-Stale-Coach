package com.example.alumno.helloworld.Modelo;

import java.io.Serializable;

/**
 * Created by damonfor on 05/03/2015.
 */
public class Alineacion implements Serializable{
    private int id_alin;
    private String nombre;
    private Deporte id_deporte;
    private String imagen_miniatura;
    private String descripcion;

    public Alineacion(int id_alin, String nombre, Deporte id_deporte, String imagen_miniatura, String descripcion) {
        this.id_alin = id_alin;
        this.nombre = nombre;
        this.id_deporte = id_deporte;
        this.imagen_miniatura = imagen_miniatura;
        this.descripcion = descripcion;
    }

    public int getId_alin() {
        return id_alin;
    }

    public void setId_alin(int id_alin) {
        this.id_alin = id_alin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Deporte getId_deporte() {
        return id_deporte;
    }

    public void setId_deporte(Deporte id_deporte) {
        this.id_deporte = id_deporte;
    }

    public String getImagen_miniatura() {
        return imagen_miniatura;
    }

    public void setImagen_miniatura(String imagen_miniatura) {
        this.imagen_miniatura = imagen_miniatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
