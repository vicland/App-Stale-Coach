package com.example.alumno.helloworld.Modelo;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alumno.helloworld.Alineaciones;
import com.example.alumno.helloworld.DeporteFutbol;
import com.example.alumno.helloworld.EditarAlineacion;
import com.example.alumno.helloworld.EditarEntrenamiento;
import com.example.alumno.helloworld.EntrenamientoLibre;
import com.example.alumno.helloworld.EntrenamientosDeCategorias;
import com.example.alumno.helloworld.MisAlineaciones;
import com.example.alumno.helloworld.R;
import com.example.alumno.helloworld.bd.GestorBD;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alumno on 05/05/2015.
 */
public class myDialogFrag extends DialogFragment {

    private Deporte deporte;
    private Context c;
    private EditText inputNombre;
    private EditText inputDescripcion;
    private Spinner s;
    private GestorBD gb;
    private EntrenamientoLibre entrenamientoLibre;
    private EditarEntrenamiento editarEntrenamiento;
    private List<CoordenadasEntrenamiento> coordenadasEntrenamientos;
    private List<CoordenadasAlineacion>coordenadasAlineacion;
    private Entrenamiento entrenamiento;
    private MisAlineaciones alineaciones;
    private EditarAlineacion editarAlineacion;
    private Alineacion alineacion;

    public List<CoordenadasAlineacion> getCoordenadasAlineacion() {
        return coordenadasAlineacion;
    }

    public void setCoordenadasAlineacion(List<CoordenadasAlineacion> coordenadasAlineacion) {
        this.coordenadasAlineacion = coordenadasAlineacion;
    }

    public MisAlineaciones getAlineaciones() {
        return alineaciones;
    }

    public void setAlineaciones(MisAlineaciones alineaciones) {
        this.alineaciones = alineaciones;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    public EditarEntrenamiento getEditarEntrenamiento() {
        return editarEntrenamiento;
    }

    public void setEditarEntrenamiento(EditarEntrenamiento editarEntrenamiento) {
        this.editarEntrenamiento = editarEntrenamiento;
    }

    public EntrenamientoLibre getEntrenamientoLibre() {
        return entrenamientoLibre;
    }

    public void setEntrenamientoLibre(EntrenamientoLibre entrenamientoLibre) {
        this.entrenamientoLibre = entrenamientoLibre;
    }

    public void setContext(Context c){
        this.c=c;
    }
    public void setDeporte(Deporte deporte2){
        this.deporte=deporte2;
    }
    public Deporte getDeporte(){
        return this.deporte;
    }

    public List<CoordenadasEntrenamiento> getCoordenadasEntrenamientos() {
        return coordenadasEntrenamientos;
    }

    public void setCoordenadasEntrenamientos(List<CoordenadasEntrenamiento> coordenadasEntrenamientos) {
        this.coordenadasEntrenamientos = coordenadasEntrenamientos;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        getDialog().setTitle(R.string.DatosEntrenamiento);
        inputNombre = new EditText(getActivity());
        inputDescripcion = new EditText(getActivity());
        if(entrenamiento!=null) {
            inputNombre.setText(entrenamiento.getNombre());
            inputDescripcion.setText(entrenamiento.getDescripcion());
            getDialog().setTitle(R.string.DatosEntrenamiento);
        }
        else if(alineacion!=null){
            inputNombre.setText(alineacion.getNombre());
            inputDescripcion.setText(alineacion.getDescripcion());
            getDialog().setTitle(R.string.DatosAlineacion);
        }


        TableLayout linLayout=new TableLayout(getActivity());
        gb = new GestorBD(getActivity());
        List<Deporte> ld = gb.obtenerDeportes();
        List<CategoriaEntrenamiento> cat = gb.obtenerCategorias(this.deporte);

        ListView lv1 = new ListView(getActivity());


        String[]deportes=new String[cat.size()];
        for(int i=0;i<cat.size();i++){
            deportes[i]=cat.get(i).getNombre();
        }

        LinearLayout nombre=new LinearLayout(getActivity());
        nombre.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtNombre=new TextView(getActivity());
        txtNombre.setText(c.getString(R.string.Nombre)+"         ");
        nombre.addView(txtNombre);
        inputNombre.setLayoutParams(new LinearLayout.LayoutParams(350,65));
        nombre.addView(inputNombre);

        linLayout.addView(nombre);

        LinearLayout categoria=new LinearLayout(getActivity());
        categoria.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtCategoria=new TextView(getActivity());
        txtCategoria.setText(c.getString(R.string.Categoria)+"     ");
        categoria.addView(txtCategoria);

        if(alineaciones==null && alineacion==null) {
            s = new Spinner(getActivity());
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, deportes);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(spinnerAdapter);
            categoria.addView(s);

            linLayout.addView(categoria);
        }


        LinearLayout descripcion=new LinearLayout(getActivity());
        descripcion.setOrientation(LinearLayout.HORIZONTAL);
        TextView txtDescripcion=new TextView(getActivity());
        txtDescripcion.setText(c.getString(R.string.Descripcion)+"  ");
        descripcion.addView(txtDescripcion);

        inputDescripcion.setLayoutParams(new LinearLayout.LayoutParams(350, 100));
        descripcion.addView(inputDescripcion);

        linLayout.addView(descripcion);


        Button b=new Button(getActivity());
        b.setText(R.string.Guardar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (coordenadasAlineacion != null) {
                    if (alineacion == null) {
                        Alineacion a = new Alineacion(0, inputNombre.getText().toString(), deporte, "", inputDescripcion.getText().toString());
                        a.setId_alin(gb.insertarAlineacion(a));
                        for (CoordenadasAlineacion c : coordenadasAlineacion) {
                            c.setId_alin(a);
                            gb.insertarCoordenadasAlineacion(c);
                        }

                        Toast.makeText(c, R.string.Guardado, Toast.LENGTH_SHORT).show();
                    } else {
                        alineacion = new Alineacion(alineacion.getId_alin(), inputNombre.getText().toString(), deporte, "", inputDescripcion.getText().toString());
                        gb.modificarAlineacion(alineacion);
                        for (int i = 0; i < coordenadasAlineacion.size(); i++) {
                            coordenadasAlineacion.get(i).setId_alin(alineacion);
                        }
                        gb.modificarCoordenadasAlineacion(coordenadasAlineacion, alineacion);
                        Toast.makeText(c, R.string.Modificado, Toast.LENGTH_SHORT).show();
                    }
                } else if (entrenamiento == null) {
                    Entrenamiento e = new Entrenamiento(0, inputNombre.getText().toString(), deporte, "", inputDescripcion.getText().toString(), gb.getCategoriaEntrenamiento((String) s.getSelectedItem(), deporte));
                    e.setId_entre(gb.insertarEntrenamiento(e));

                    for (CoordenadasEntrenamiento c : coordenadasEntrenamientos) {
                        c.setId_entr(e);
                        gb.insertarCoordenadasEntrenamiento(c);
                    }

                    Toast.makeText(c, R.string.Guardado, Toast.LENGTH_SHORT).show();

                } else {
                    entrenamiento = new Entrenamiento(entrenamiento.getId_entre(), inputNombre.getText().toString(), deporte, "", inputDescripcion.getText().toString(), gb.getCategoriaEntrenamiento((String) s.getSelectedItem(), deporte));
                    gb.modificarEntrenamiento(entrenamiento);
                    for (int i = 0; i < coordenadasEntrenamientos.size(); i++) {
                        coordenadasEntrenamientos.get(i).setId_entr(entrenamiento);
                    }
                    gb.modificarCoordenadasEntrenamiento(coordenadasEntrenamientos, entrenamiento);
                    Toast.makeText(c, R.string.Modificado, Toast.LENGTH_SHORT).show();

                }
                Intent intent = new Intent(c, DeporteFutbol.class);
                intent.putExtra("deporte", deporte);
                if (entrenamientoLibre != null)
                    entrenamientoLibre.finish();
                if (editarEntrenamiento != null) {
                    editarEntrenamiento.finish();
                    intent = new Intent(c, EntrenamientosDeCategorias.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ;
                    intent.putExtra("deporte", deporte);
                    intent.putExtra("categoria", entrenamiento.getCategoria_entrenamiento());
                    //mBundle = new Bundle();
                    startActivity(intent);
                }
                if (alineaciones != null)
                    alineaciones.finish();
                if (editarAlineacion != null) {
                    editarAlineacion.finish();
                    intent = new Intent(editarAlineacion, Alineaciones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("deporte", deporte);
                    //mBundle = new Bundle();
                    startActivity(intent);
                }

            }

        });

        linLayout.addView(b);

        return linLayout;
    }

    protected void saveImage(Bitmap bmScreen2) {
        // TODO Auto-generated method stub

        // String fname = "Upload.png";

        File storagePath = new File(Environment.getExternalStorageDirectory()+ "/StaleCoach");
        storagePath.mkdirs();


        myDialogFrag dialog = new myDialogFrag();
        dialog.setDeporte(deporte);



        File saved_image_file = new File( storagePath +"/imagen1.png");

        dialog.show(getFragmentManager(),"Diag");
        if (saved_image_file.exists())
            saved_image_file.delete();
        try {
            FileOutputStream out = new FileOutputStream(saved_image_file);
            bmScreen2.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setEditarAlineacion(EditarAlineacion editarAlineacion) {
        this.editarAlineacion = editarAlineacion;
    }

    public EditarAlineacion getEditarAlineacion() {
        return editarAlineacion;
    }

    public void setAlineacion(Alineacion alineacion) {
        this.alineacion = alineacion;
    }
}