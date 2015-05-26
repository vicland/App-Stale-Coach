package com.example.alumno.helloworld;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.alumno.helloworld.Modelo.CoordenadasAlineacion;
import com.example.alumno.helloworld.Modelo.CoordenadasEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.ObjetoEntrenamiento;
import com.example.alumno.helloworld.Modelo.myDialogFrag;
import com.example.alumno.helloworld.Vista.ListaObjetos;
import com.example.alumno.helloworld.bd.GestorBD;

import java.util.ArrayList;
import java.util.List;


public class MisAlineaciones extends ActionBarActivity {
    GestorBD gb;
    Deporte deporte;
    Integer[] imageId = {
            R.drawable.ficharoja,
            R.drawable.fichaazul,
            R.drawable.balon,
            R.drawable.guardar
    };
    MisAlineaciones e;
    String[] web = {
            "ficharoja",
            "fichaazul",
            "balon",
            "guardar"
    } ;
    ListaObjetos adapter;
    ListView list;
    RelativeLayout a;
    List<CoordenadasAlineacion> coordenadasAlineacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        coordenadasAlineacion=new ArrayList<CoordenadasAlineacion>();
        e=this;
        Intent intent = getIntent();
        deporte=(Deporte)intent.getExtras().getSerializable("deporte");
        gb=new GestorBD(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_alineaciones);

        adapter = new ListaObjetos(this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        a=(RelativeLayout)findViewById(R.id.campoFutbol);
        a.setOnDragListener(new View.OnDragListener(){
            public boolean onDrag(View v,  DragEvent event) {
                switch(event.getAction())
                {
                    case DragEvent.ACTION_DROP:
                        String nombreObjeto= event.getClipDescription().getLabel().toString();
                        String parametros[]=nombreObjeto.split("/");

                        View objeto = (View) event.getLocalState();
                        ViewGroup papa_objeto = (ViewGroup) objeto.getParent();
                        papa_objeto.removeView(objeto);

                        RelativeLayout contenedor_nuevo2 = (RelativeLayout) v;
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(45,45);
                        params.leftMargin =(int)event.getX();
                        params.topMargin = (int)event.getY();
                        contenedor_nuevo2.addView(objeto,params);

                        adapter = new ListaObjetos(e, web, imageId);
                        list=(ListView)findViewById(R.id.list);
                        list.setAdapter(adapter);

                        objeto.setVisibility(View.VISIBLE);
                        ObjetoEntrenamiento objetoEn=gb.getObjetoEntrenamient(parametros[0]);
                        if(!nombreObjeto.contains("/")){
                            coordenadasAlineacion.add(new CoordenadasAlineacion(objetoEn,null,event.getX(),event.getY()));
                        }
                        else {
                            double x=Double.parseDouble(parametros[2]);
                            double y=Double.parseDouble(parametros[3]);
                            CoordenadasAlineacion eliminar=null;
                            for(CoordenadasAlineacion c:coordenadasAlineacion){
                                if(c.getCoordx()==x && y==c.getCoordy() && parametros[0].equals(c.getId_obj().getNombre())){
                                    eliminar=c;
                                }
                            }
                            if(eliminar!=null){
                                coordenadasAlineacion.remove(eliminar);
                                coordenadasAlineacion.add(new CoordenadasAlineacion(objetoEn,null,event.getX(),event.getY()));
                            }
                        }
                        break;
                }
                return true;
            }
        });
        list.setOnDragListener(new View.OnDragListener(){
            @Override
            public boolean onDrag(View v,  DragEvent event) {
                switch(event.getAction())
                {
                    case DragEvent.ACTION_DROP:

                        adapter = new ListaObjetos(e, web, imageId);
                        list=(ListView)findViewById(R.id.list);
                        list.setAdapter(adapter);
                        break;
                }
                return true;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                myDialogFrag dialog=new myDialogFrag();
                dialog.setDeporte(deporte);
                dialog.setContext(e);
                dialog.setAlineaciones(e);
                dialog.setCoordenadasAlineacion(coordenadasAlineacion);
                dialog.show(getFragmentManager(),"Diag");

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mis_alineaciones, menu);
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
