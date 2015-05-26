package com.example.alumno.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alumno.helloworld.Modelo.CategoriaEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.bd.GestorBD;

import static com.example.alumno.helloworld.R.*;

/**
 * Created by Alumno on 25/02/2015.
 */
public class DeporteFutbol extends ActionBarActivity {
    Button button;
    Deporte deporte;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.deportefutbol);
        intent = getIntent();
        deporte=(Deporte)intent.getExtras().getSerializable("deporte");
    }
    public void pulsarBoton(View view) {
        Bundle mBundle=null;
        switch(view.getId()) {
            case R.id.btnEntrenamientoLibre:
                intent = new Intent(this, EntrenamientoLibre.class);
                intent.putExtra("deporte",deporte);
                mBundle = new Bundle();
                startActivity(intent);

                break;
            case R.id.btnMisEntrenamientos:
                intent = new Intent(this, Categorias.class);
                intent.putExtra("deporte",deporte);
                mBundle = new Bundle();
                startActivity(intent);
                break;
            case R.id.btnAlineacion:
                intent = new Intent(this, MisAlineaciones.class);
                intent.putExtra("deporte",deporte);
                mBundle = new Bundle();
                startActivity(intent);
                break;
            case R.id.btnMisAlineaciones:
                intent = new Intent(this, Alineaciones.class);
                intent.putExtra("deporte",deporte);
                mBundle = new Bundle();
                startActivity(intent);
                break;
        }
    }
}
