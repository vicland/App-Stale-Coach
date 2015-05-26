package com.example.alumno.helloworld;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alumno.helloworld.Modelo.Alineacion;
import com.example.alumno.helloworld.Modelo.CoordenadasAlineacion;
import com.example.alumno.helloworld.Modelo.CoordenadasEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.Modelo.ObjetoEntrenamiento;
import com.example.alumno.helloworld.Modelo.myDialogBorrar;
import com.example.alumno.helloworld.Modelo.myDialogFrag;
import com.example.alumno.helloworld.Vista.ListaObjetos;
import com.example.alumno.helloworld.bd.GestorBD;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditarAlineacion extends ActionBarActivity {
    Deporte deporte;
    List<ObjetoEntrenamiento> objs = new ArrayList<ObjetoEntrenamiento>();
    View screen;
    Bitmap bmScreen;
    ImageView bmImage;
    ListView lista;
    Integer[] imageId = {
            R.drawable.ficharoja,
            R.drawable.fichaazul,
            R.drawable.balon,
            R.drawable.papelera,
            R.drawable.guardar
    };
    ListaObjetos adapter;
    ListView list;
    EditarAlineacion e;
    String[] web = {
            "ficharoja",
            "fichaazul",
            "balon",
            "papelera",
            "guardar"
    } ;
    GestorBD gb;
    RelativeLayout a;
    Context c;
    Button save;
    List<CoordenadasAlineacion> coordenadasAlineaciones;
    List<CoordenadasAlineacion> coordenadasEntrenamientosAntiguas;
    Alineacion alineacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        coordenadasAlineaciones =new ArrayList<CoordenadasAlineacion>();
        gb=new GestorBD(this);
        c=this;
        e=this;
        Intent intent = getIntent();
        deporte=(Deporte)intent.getExtras().getSerializable("deporte");
        coordenadasEntrenamientosAntiguas = intent.getParcelableArrayListExtra("coordenadas");
        alineacion=(Alineacion)intent.getExtras().getSerializable("alineacion");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_libre);
        lista=(ListView)findViewById(R.id.list);
        screen = (View) findViewById(R.id.campoFutbol);

        a=(RelativeLayout)findViewById(R.id.campoFutbol);
        e=this;
        adapter = new ListaObjetos(this, web, imageId);
        list=(ListView)findViewById(R.id.list);

        list.setAdapter(adapter);

        for (final CoordenadasAlineacion coordenada:coordenadasEntrenamientosAntiguas){
            RelativeLayout contenedor_nuevo2 = (RelativeLayout) findViewById(R.id.campoFutbol);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(45,45);
            params.leftMargin =(int)coordenada.getCoordx();
            params.topMargin = (int)coordenada.getCoordy();

            ImageView imageView1 = new ImageView(this);

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                AssetManager assets = e.getResources().getAssets();
                InputStream buf = new BufferedInputStream((assets.open(coordenada.getId_obj().getImagen())));
                Bitmap myBitmap = BitmapFactory.decodeStream(buf);
                imageView1.setImageBitmap(myBitmap);
            } catch (IOException i) {

            }

            //imageView1.ontou
            imageView1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View objeto, MotionEvent evento) {
                    if (evento.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData datos = ClipData.newPlainText(coordenada.getId_obj().getNombre()+"/campo/"+coordenada.getCoordx()+"/"+coordenada.getCoordy(), coordenada.getId_obj().getNombre()+"/campo/"+coordenada.getCoordx()+"/"+coordenada.getCoordy());
                        View.DragShadowBuilder objeto_sombra = new View.DragShadowBuilder(objeto);
                        objeto.startDrag(datos, objeto_sombra, objeto, 0);
                        objeto.setVisibility(View.INVISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            contenedor_nuevo2.addView(imageView1,params);

        }



        a.setOnDragListener(new View.OnDragListener(){
            @Override
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
                        contenedor_nuevo2.addView(objeto, params);

                        adapter = new ListaObjetos(e, web, imageId);
                        list=(ListView)findViewById(R.id.list);
                        list.setAdapter(adapter);

                        objeto.setVisibility(View.VISIBLE);
                        ObjetoEntrenamiento objetoEn=gb.getObjetoEntrenamient(parametros[0]);
                        if(!nombreObjeto.contains("/")){
                            coordenadasAlineaciones.add(new CoordenadasAlineacion(objetoEn,null,event.getX(),event.getY()));
                        }
                        else {
                            double x=Double.parseDouble(parametros[2]);
                            double y=Double.parseDouble(parametros[3]);
                            CoordenadasAlineacion eliminar=null;
                            for(CoordenadasAlineacion c:coordenadasEntrenamientosAntiguas){
                                if(c.getCoordx()==x && y==c.getCoordy() && parametros[0].equals(c.getId_obj().getNombre())){
                                    eliminar=c;
                                }
                            }
                            if(eliminar!=null){
                                coordenadasEntrenamientosAntiguas.remove(eliminar);
                                coordenadasAlineaciones.add(new CoordenadasAlineacion(objetoEn,null,event.getX(),event.getY()));
                            }
                        }
                        break;
                }
                return true;
            }
        });
        lista.setOnDragListener(new View.OnDragListener(){
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
        ListaObjetos adapter = new ListaObjetos(this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if(web[position].equals("guardar")) {
                    myDialogFrag dialog = new myDialogFrag();
                    dialog.setDeporte(deporte);
                    dialog.setContext(c);
                    List<CoordenadasAlineacion> totales = new ArrayList<CoordenadasAlineacion>(coordenadasAlineaciones);
                    totales.addAll(coordenadasEntrenamientosAntiguas);
                    dialog.setCoordenadasAlineacion(totales);
                    dialog.setAlineacion(alineacion);
                    dialog.setEditarAlineacion(e);
                    dialog.show(getFragmentManager(), "Diag");
                }
                else {
                    myDialogBorrar a=new myDialogBorrar();
                    a.setmContext(c);
                    a.setAlineacion(alineacion);
                    a.setEditarAlineacion(e);
                    a.setDeporte(deporte);
                    a.show(getFragmentManager(), "MyDialog");

                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entrenamiento_libre, menu);
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
