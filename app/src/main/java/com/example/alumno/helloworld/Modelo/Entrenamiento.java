package com.example.alumno.helloworld.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by damonfor on 05/03/2015.
 */
public class Entrenamiento implements Parcelable,Serializable{
    private int id_entre;
    private String nombre;
    private Deporte deporte;
    private String imagen_miniatura;
    private String descripcion;
    private CategoriaEntrenamiento categoria_entrenamiento;

    public Entrenamiento(Parcel p){
        id_entre=p.readInt();
        nombre=p.readString();
        deporte=(Deporte)p.readSerializable();
        imagen_miniatura=p.readString();
        descripcion=p.readString();
        categoria_entrenamiento=(CategoriaEntrenamiento)p.readSerializable();
    }

    public Entrenamiento(int id_entre, String nombre, Deporte deporte, String imagen_miniatura, String descripcion, CategoriaEntrenamiento categoria_entrenamiento) {
        this.id_entre = id_entre;
        this.nombre = nombre;
        this.deporte = deporte;
        this.imagen_miniatura = imagen_miniatura;
        this.descripcion = descripcion;
        this.categoria_entrenamiento = categoria_entrenamiento;
    }

    public int getId_entre() {
        return id_entre;
    }

    public void setId_entre(int id_entre) {
        this.id_entre = id_entre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
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

    public CategoriaEntrenamiento getCategoria_entrenamiento() {
        return categoria_entrenamiento;
    }

    public void setCategoria_entrenamiento(CategoriaEntrenamiento categoria_entrenamiento) {
        this.categoria_entrenamiento = categoria_entrenamiento;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.getId_entre());
        dest.writeString(this.nombre);
        dest.writeSerializable(this.deporte);
        dest.writeString(imagen_miniatura);
        dest.writeString(descripcion);
        dest.writeSerializable(categoria_entrenamiento);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Entrenamiento createFromParcel(Parcel in) {
            return new Entrenamiento(in);
        }

        public Entrenamiento[] newArray(int size) {
            return new Entrenamiento[size];
        }
    };
}
