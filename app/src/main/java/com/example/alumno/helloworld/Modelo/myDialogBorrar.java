package com.example.alumno.helloworld.Modelo;
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
import com.example.alumno.helloworld.R;
import com.example.alumno.helloworld.bd.GestorBD;

public class myDialogBorrar extends DialogFragment{
    private Entrenamiento entrenamiento;
    private GestorBD gb;
    private EditarEntrenamiento editarEntrenamiento;
    private Deporte deporte;
    private Alineacion alineacion;
    private EditarAlineacion editarAlineacion;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;
    public myDialogBorrar() {
        mContext = getActivity();
    }
    private Intent intent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(R.string.eliminar);
        alertDialogBuilder.setMessage(R.string.eliminarEntrenamiento);
        //null should be your on click listener
        alertDialogBuilder.setPositiveButton(R.string.Si, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                gb=new GestorBD(mContext);

                //dialog.dismiss();
                if(entrenamiento!=null) {
                    gb.eliminarEntrenamiento(entrenamiento);
                    editarEntrenamiento.finish();

                    intent = new Intent(mContext, EntrenamientosDeCategorias.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("deporte", deporte);
                    intent.putExtra("categoria", entrenamiento.getCategoria_entrenamiento());
                    //mBundle = new Bundle();
                    startActivity(intent);
                }
                else if(alineacion!=null){
                    gb.eliminarAlineacion(alineacion);
                    editarAlineacion.finish();

                    intent = new Intent(mContext, Alineaciones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("deporte", deporte);

                    //mBundle = new Bundle();
                    startActivity(intent);
                }
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        return alertDialogBuilder.create();
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEditarEntrenamiento(EditarEntrenamiento editarEntrenamiento) {
        this.editarEntrenamiento = editarEntrenamiento;
    }

    public EditarEntrenamiento getEditarEntrenamiento() {
        return editarEntrenamiento;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setAlineacion(Alineacion alineacion) {
        this.alineacion = alineacion;
    }

    public Alineacion getAlineacion() {
        return alineacion;
    }

    public void setEditarAlineacion(EditarAlineacion editarAlineacion) {
        this.editarAlineacion = editarAlineacion;
    }

    public EditarAlineacion getEditarAlineacion() {
        return editarAlineacion;
    }
}