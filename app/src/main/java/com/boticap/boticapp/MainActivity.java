package com.boticap.boticapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import BD.helper.DatabaseHelper;
import BD.model.Farmacia;
import BD.model.Remedio;


public class MainActivity extends Activity {


    // Database Helper
    DatabaseHelper db;

    Context contexto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());

        //Farmacias
        Farmacia far1=new Farmacia("-33.453114 -70.682136","Obpo Javier Vásquez Valencia 3455","Cruz Verde");
        Farmacia far2=new Farmacia("-33.454059 -70.692502","Av Libertador Bernardo O'Higgins 4131, Estación","Farmacia Doctor Simi");
        Farmacia far3=new Farmacia("-33.456762 -70.701965","Avenida Liber Bernardo O'higgins 4850","Farmacias Salcobrand");

        //guardar farmacias BD
        long  far1_id=db.createFarmacia(far1);
        long  far2_id=db.createFarmacia(far2);
        long  far3_id=db.createFarmacia(far3);

        //Remedios
        Remedio re1=new Remedio("viadil","gotas");
        Remedio re2=new Remedio("Paracetamol","500mg");
        Remedio re3=new Remedio("nastizol","300mg");

        //guardar remdios
        long re1_id=db.createRemedio(re1);
        long re2_id=db.createRemedio(re2);
        long re3_id=db.createRemedio(re3);

        //crear farmaciaRemedio
        long fr1=db.createFarmaciaRemedio(far1_id,re1_id);
        long fr2=db.createFarmaciaRemedio(far1_id,re2_id);
        long fr3=db.createFarmaciaRemedio(far1_id,re3_id);

        long fr4=db.createFarmaciaRemedio(far2_id,re1_id);
        long fr5=db.createFarmaciaRemedio(far2_id,re3_id);

        long fr6=db.createFarmaciaRemedio(far3_id,re1_id);
        long fr7=db.createFarmaciaRemedio(far3_id,re2_id);
        long fr8=db.createFarmaciaRemedio(far3_id,re3_id);

        // Don't forget to close database connection
        db.closeDB();



        Handler handler = new Handler();
        contexto = this.getApplicationContext();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                Intent intent = new Intent(contexto,TipoDeBusqueda.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
