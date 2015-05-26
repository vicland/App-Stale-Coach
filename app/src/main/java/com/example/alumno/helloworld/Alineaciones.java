package com.example.alumno.helloworld;

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

import com.example.alumno.helloworld.Modelo.Alineacion;
import com.example.alumno.helloworld.Modelo.CategoriaEntrenamiento;
import com.example.alumno.helloworld.Modelo.CoordenadasAlineacion;
import com.example.alumno.helloworld.Modelo.CoordenadasEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.Vista.AlineacionAdapter;
import com.example.alumno.helloworld.Vista.EntrenamientoAdapter;
import com.example.alumno.helloworld.Vista.myDialogMostrarDetalles;
import com.example.alumno.helloworld.bd.GestorBD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Alineaciones extends ActionBarActivity {
    private GestorBD gb=new GestorBD(this);
    private GridView gridView;
    private Deporte deporte;
    private List<Alineacion> alineaciones;
    private Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        c=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);

        Intent intent = getIntent();
        deporte=(Deporte)intent.getExtras().getSerializable("deporte");

        alineaciones= gb.getAlineaciones(deporte);

        if(alineaciones.size()<=0){
            this.finish();
            Toast.makeText(getApplicationContext(), R.string.NoAlineaciones, Toast.LENGTH_LONG).show();
        }
        else {
            gridView = (GridView) findViewById(R.id.gridView1);

            gridView.setAdapter(new AlineacionAdapter(this, alineaciones));
            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int position, long arg3) {
                    myDialogMostrarDetalles a=new myDialogMostrarDetalles();
                    a.setmContext(c);
                    a.setDescripcion(alineaciones.get(position).getDescripcion());
                    a.show(getFragmentManager(),"Dialog");
                    return true;
                }
            });
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Alineacion alineacion = alineaciones.get(position);

                    List<CoordenadasAlineacion> cordenadas = gb.getCoordenadasAlineacion(alineacion);

                    Intent intent = new Intent(c, EditarAlineacion.class);
                    intent.putExtra("deporte", deporte);
                    intent.putParcelableArrayListExtra("coordenadas", (ArrayList<CoordenadasAlineacion>) cordenadas);
                    intent.putExtra("alineacion", alineacion);
                    //mBundle = new Bundle();
                    startActivity(intent);


                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alineaciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
