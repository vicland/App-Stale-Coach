package com.example.alumno.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.alumno.helloworld.Modelo.CategoriaEntrenamiento;
import com.example.alumno.helloworld.Modelo.CoordenadasEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.Vista.CategoriaAdapter;
import com.example.alumno.helloworld.Vista.EntrenamientoAdapter;
import com.example.alumno.helloworld.Vista.myDialogMostrarDetalles;
import com.example.alumno.helloworld.bd.GestorBD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EntrenamientosDeCategorias extends Activity {
    private GestorBD gb=new GestorBD(this);
    private GridView gridView;
    private Deporte deporte;
    private CategoriaEntrenamiento categoria;
    private List<Entrenamiento> entrenamientos;
    private Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        c=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);

        Intent intent = getIntent();
        deporte=(Deporte)intent.getExtras().getSerializable("deporte");
        categoria = (CategoriaEntrenamiento)intent.getExtras().getSerializable("categoria");

        entrenamientos= gb.getEntrenamientos(categoria, deporte);
        if(entrenamientos.size()<=0) {
            Toast.makeText(getApplicationContext(), "No hay entrenamientos.",
                    Toast.LENGTH_LONG).show();
            this.finish();
        }
        else {
            gridView = (GridView) findViewById(R.id.gridView1);

            gridView.setAdapter(new EntrenamientoAdapter(this, entrenamientos));
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Entrenamiento entrenamiento = entrenamientos.get(position);

                    List<CoordenadasEntrenamiento> cordenadas = gb.getCoordenadasEntrenamiento(entrenamiento);

                    Intent intent = new Intent(c, EditarEntrenamiento.class);
                    intent.putExtra("deporte", deporte);
                    intent.putExtra("entrenamiento", (Serializable) entrenamiento);
                    intent.putParcelableArrayListExtra("coordenadas", (ArrayList<CoordenadasEntrenamiento>) cordenadas);
                    //mBundle = new Bundle();
                    startActivity(intent);


                }
            });
            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
                    myDialogMostrarDetalles a=new myDialogMostrarDetalles();
                    a.setmContext(c);
                    a.setDescripcion(entrenamientos.get(position).getDescripcion());
                    a.show(getFragmentManager(),"Dialog");
                    return true;
                }
            });
        }

    }


}
