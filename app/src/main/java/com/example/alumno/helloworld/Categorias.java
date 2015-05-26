package com.example.alumno.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.alumno.helloworld.Modelo.CategoriaEntrenamiento;
import com.example.alumno.helloworld.Modelo.CoordenadasEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.Vista.CategoriaAdapter;
import com.example.alumno.helloworld.bd.GestorBD;
import com.example.alumno.helloworld.util.SystemUiHider;

import java.util.ArrayList;
import java.util.List;


public class Categorias extends Activity {
    private GestorBD gb=new GestorBD(this);
    private GridView gridView;
    private Deporte deporte;
    private List<CategoriaEntrenamiento> categorias;
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        c=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);
        Intent intent = getIntent();
        deporte=(Deporte)intent.getExtras().getSerializable("deporte");
        categorias=gb.obtenerCategorias(deporte);

        gridView = (GridView) findViewById(R.id.gridView1);

        gridView.setAdapter(new CategoriaAdapter(this, categorias));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                CategoriaEntrenamiento categoria=categorias.get(position);

                if(gb.getEntrenamientos(categoria, deporte).size()>0) {

                    Intent intent = new Intent(c, EntrenamientosDeCategorias.class);
                    intent.putExtra("deporte", deporte);
                    intent.putExtra("categoria", categoria);
                    //mBundle = new Bundle();
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(), R.string.NoEntrenamiento, Toast.LENGTH_LONG).show();


            }
        });

    }





}
