package com.example.alumno.helloworld.Vista;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alumno.helloworld.Modelo.CategoriaEntrenamiento;
import com.example.alumno.helloworld.Modelo.Entrenamiento;
import com.example.alumno.helloworld.R;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Created by damonfor on 26/03/2015.
 */
public class EntrenamientoAdapter extends BaseAdapter {
    private Context context;
    private List<Entrenamiento> entrenamientos;

    public EntrenamientoAdapter(Context context, List<Entrenamiento> entrenamientos) {
        this.context = context;
        this.entrenamientos = entrenamientos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.categoria, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(entrenamientos.get(position).getNombre());

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);

            int drawableID = context.getResources().getIdentifier(entrenamientos.get(position).getImagen_miniatura(), "drawable", context.getPackageName());


            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                AssetManager assets = context.getResources().getAssets();
                InputStream buf = new BufferedInputStream((assets.open(entrenamientos.get(position).getImagen_miniatura())));
                Bitmap myBitmap = BitmapFactory.decodeStream(buf);
                imageView.setImageBitmap(myBitmap);
            } catch (IOException i) {

            }



        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return entrenamientos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
