package com.boticap.boticapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class OpcionesMedicamento extends ActionBarActivity {

    Button boton;
    Context contexto;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_medicamento);
        contexto = this;
        boton = (Button)findViewById(R.id.boton_disponibilidad_en_farmacias);

        actionBar = getSupportActionBar();
        actionBar.setTitle("BoticApp");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //recepcion del medicamento seleccionado
        Intent intentRecepcion = getIntent();
        final long id_remedio = intentRecepcion.getLongExtra("id_remedio_seleccionado", 0);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent = new Intent(contexto, MapsActivity.class);
                //se envía el medicamento seleccionado
                miIntent.putExtra("id_remedio_seleccionado", id_remedio);
                startActivity(miIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opciones_medicamento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, TipoDeBusqueda.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
