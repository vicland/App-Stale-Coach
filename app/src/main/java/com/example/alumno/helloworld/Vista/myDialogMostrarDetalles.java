package com.example.alumno.helloworld.Vista;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.alumno.helloworld.Alineaciones;
import com.example.alumno.helloworld.EditarAlineacion;
import com.example.alumno.helloworld.EditarEntrenamiento;
import com.example.alumno.helloworld.EntrenamientosDeCategorias;
import com.example.alumno.helloworld.Modelo.Alineacion;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.R;
import com.example.alumno.helloworld.bd.GestorBD;

public class myDialogMostrarDetalles extends DialogFragment{
    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    private String Descripcion;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;
    public myDialogMostrarDetalles() {
        mContext = getActivity();
    }
    private Intent intent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(R.string.Descripcion);
        alertDialogBuilder.setMessage(this.Descripcion);



        return alertDialogBuilder.create();
    }


}