package com.example.alumno.helloworld;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alumno.helloworld.Modelo.CoordenadasEntrenamiento;
import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.Modelo.ObjetoEntrenamiento;
import com.example.alumno.helloworld.Modelo.myDialogFrag;
import com.example.alumno.helloworld.Vista.ListaObjetos;
import com.example.alumno.helloworld.bd.GestorBD;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class EntrenamientoLibre extends ActionBarActivity {
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
            R.drawable.cono,
            R.drawable.valla,
            R.drawable.numeros,
            R.drawable.guardar
    };
    ListaObjetos adapter;
    ListView list;
    EntrenamientoLibre e;
    String[] web = {
            "ficharoja",
            "fichaazul",
            "balon",
            "cono",
            "valla",
            "numeros",
            "guardar"
    } ;
    GestorBD gb;
    int cont=1;
    RelativeLayout a;
    Context c;
    Button save;
    List<CoordenadasEntrenamiento> coordenadasEntrenamientos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        coordenadasEntrenamientos=new ArrayList<CoordenadasEntrenamiento>();
        gb=new GestorBD(this);
        c=this;

        Intent intent = getIntent();
        deporte=(Deporte)intent.getExtras().getSerializable("deporte");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_libre);
        lista=(ListView)findViewById(R.id.list);
        screen = (View) findViewById(R.id.campoFutbol);

        a=(RelativeLayout)findViewById(R.id.campoFutbol);
        e=this;
        adapter = new ListaObjetos(this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        e=this;

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

                        TextView tv = new TextView(getApplicationContext());
                        ImageView iv = null;
                        if(nombreObjeto.equals("numeros")){

                            tv.setText(String.valueOf(cont));
                            tv.setTextSize(47);
                            cont++;
                            Bitmap testB;

                            testB = Bitmap.createBitmap(80, 100, Bitmap.Config.ARGB_8888);
                            Canvas c = new Canvas(testB);
                            tv.layout(0, 0, 80, 100);
                            tv.draw(c);

                            iv = new ImageView(getApplicationContext());
                            iv.setImageBitmap(testB);
                            //iv.setColorFilter(Color.BLACK);

                            objeto=iv;

                        }

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
                            coordenadasEntrenamientos.add(new CoordenadasEntrenamiento(objetoEn,null,event.getX(),event.getY()));
                        }
                        else {
                            double x=Double.parseDouble(parametros[2]);
                            double y=Double.parseDouble(parametros[3]);
                            CoordenadasEntrenamiento eliminar=null;
                            for(CoordenadasEntrenamiento c:coordenadasEntrenamientos){
                                if(c.getCoordx()==x && y==c.getCoordy() && parametros[0].equals(c.getId_obj().getNombre())){
                                    eliminar=c;
                                }
                            }
                            if(eliminar!=null){
                                coordenadasEntrenamientos.remove(eliminar);
                                coordenadasEntrenamientos.add(new CoordenadasEntrenamiento(objetoEn,null,event.getX(),event.getY()));
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

                myDialogFrag dialog=new myDialogFrag();
                dialog.setDeporte(deporte);
                dialog.setContext(c);
                dialog.setEntrenamientoLibre(e);
                dialog.setCoordenadasEntrenamientos(coordenadasEntrenamientos);
                dialog.show(getFragmentManager(),"Diag");

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
