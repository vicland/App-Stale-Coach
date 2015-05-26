package com.example.alumno.helloworld.Vista;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alumno.helloworld.EntrenamientoLibre;
import com.example.alumno.helloworld.R;

public class ListaObjetos extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private static final String IMAGEVIEW_TAG="Prueba";
    private RelativeLayout.LayoutParams layoutParams;
    private ImageView ima;
    public ListaObjetos(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.lateral, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.lateral, null, true);
        //TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ima = (ImageView) rowView.findViewById(R.id.img);
        //txtTitle.setText(web[position]);

        ima.setTag(IMAGEVIEW_TAG);
        ima.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View objeto, MotionEvent evento) {
                if(!web[position].equals("guardar") && !web[position].equals("papelera") ) {
                    if (evento.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData datos;

                        int parentId = ((View) objeto.getParent()).getId();
                        if(parentId==R.id.campoFutbol){
                            datos = ClipData.newPlainText(web[position]+"/campo/"+objeto.getX()+"/"+objeto.getY(),web[position]+"/campo/"+objeto.getX()+"/"+objeto.getY());
                        }
                        else
                            datos=ClipData.newPlainText(web[position], web[position]);

                        View.DragShadowBuilder objeto_sombra = new View.DragShadowBuilder(objeto);
                        objeto.startDrag(datos, objeto_sombra, objeto, 0);
                        objeto.setVisibility(View.INVISIBLE);

                        return true;
                    } else {
                        return false;
                    }
                }

                return false;
            }
        });

        ima.setImageResource(imageId[position]);
        return rowView;
    }
}
