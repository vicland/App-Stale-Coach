package com.example.alumno.helloworld.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by damonfor on 05/03/2015.
 */
public class CoordenadasAlineacion implements Parcelable {
    private ObjetoEntrenamiento id_obj;
    private Alineacion id_alin;
    private double coordx;
    private double coordy;

    public CoordenadasAlineacion(Parcel p){
        id_obj=(ObjetoEntrenamiento)p.readSerializable();
        id_alin=(Alineacion)p.readSerializable();
        coordx=p.readDouble();
        coordy=p.readDouble();
    }

    public CoordenadasAlineacion(ObjetoEntrenamiento id_obj, Alineacion id_alin, double coordx, double coordy) {
        this.id_obj = id_obj;
        this.id_alin = id_alin;
        this.coordx = coordx;
        this.coordy = coordy;
    }

    public ObjetoEntrenamiento getId_obj() {
        return id_obj;
    }

    public void setId_obj(ObjetoEntrenamiento id_obj) {
        this.id_obj = id_obj;
    }

    public Alineacion getId_alin() {
        return id_alin;
    }

    public void setId_alin(Alineacion id_alin) {
        this.id_alin = id_alin;
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
        dest.writeSerializable(id_alin);
        dest.writeDouble(coordx);
        dest.writeDouble(coordy);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CoordenadasAlineacion createFromParcel(Parcel in) {
            return new CoordenadasAlineacion(in);
        }

        public CoordenadasAlineacion[] newArray(int size) {
            return new CoordenadasAlineacion[size];
        }
    };
}
