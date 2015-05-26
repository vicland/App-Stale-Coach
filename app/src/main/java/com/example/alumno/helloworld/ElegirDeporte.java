package com.example.alumno.helloworld;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.alumno.helloworld.Modelo.Deporte;
import com.example.alumno.helloworld.bd.GestorBD;


public class ElegirDeporte extends ActionBarActivity {

    private GestorBD gb=new GestorBD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.elegirdeporte);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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


    public void pulsarBoton(View view) {
        Intent intent=null;
        Bundle mBundle=null;
        Deporte deporte=new Deporte(1,"futbol","fondo_futbol.jpg","campo_futbol.jpg");
        switch(view.getId()) {
            case R.id.btnFutbol:
                intent = new Intent(this, DeporteFutbol.class);
                intent.putExtra("deporte",deporte);
                mBundle = new Bundle();
                startActivity(intent);
                break;
            case R.id.btnBaloncesto:
                break;
            case R.id.btnVoley:
                break;
        }
        //Toast b=Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT);
        //b.show();


    }
}
