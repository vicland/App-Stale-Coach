package com.example.alumno.helloworld.Modelo;

import java.io.Serializable;

/**
 * Created by damonfor on 05/03/2015.
 */
public class ObjetoEntrenamiento implements Serializable{
    private int id_obj;
    private String nombre;
    private String descripcion;
    private String imagen;

    public ObjetoEntrenamiento(int id_obj, String nombre, String descripcion, String imagen) {
        this.id_obj = id_obj;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getId_obj() {
        return id_obj;
    }

    public void setId_obj(int id_obj) {
        this.id_obj = id_obj;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
