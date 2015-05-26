package com.example.alumno.helloworld.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by damonfor on 05/03/2015.
 */
public class CoordenadasEntrenamiento implements Parcelable {
    private ObjetoEntrenamiento id_obj;
    private Entrenamiento id_entr;
    private double coordx;
    private double coordy;
    private int contador;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public CoordenadasEntrenamiento(Parcel p){
        id_obj=(ObjetoEntrenamiento)p.readSerializable();
        id_entr=(Entrenamiento)p.readSerializable();
        coordx=p.readDouble();
        coordy=p.readDouble();
        contador=0;
    }

    public CoordenadasEntrenamiento(ObjetoEntrenamiento id_obj, Entrenamiento id_entr, double coordx, double coordy) {
        this.id_obj = id_obj;
        this.id_entr = id_entr;
        this.coordx = coordx;
        this.coordy = coordy;
    }

    public ObjetoEntrenamiento getId_obj() {
        return id_obj;
    }

    public void setId_obj(ObjetoEntrenamiento id_obj) {
        this.id_obj = id_obj;
    }

    public Entrenamiento getId_entr() {
        return id_entr;
    }

    public void setId_entr(Entrenamiento id_entr) {
        this.id_entr = id_entr;
    }

    public double getCoordx() {
        return coordx;
    }

    public void setCoordx(double coordx) {
        this.coordx = coordx;
    }

    public double getCoordy() {
        return coordy;
    }

    public void setCoordy(double coordy) {
        this.coordy = coordy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(id_obj);
        dest.writeSerializable(id_entr);
        dest.writeDouble(coordx);
        dest.writeDouble(coordy);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CoordenadasEntrenamiento createFromParcel(Parcel in) {
            return new CoordenadasEntrenamiento(in);
        }

        public CoordenadasEntrenamiento[] newArray(int size) {
            return new CoordenadasEntrenamiento[size];
        }
    };
}
