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

        Handler handler = new Handler();
        contexto = this.getApplicationContext();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                //Farmacias
                if (!db.existen()) {

                    Farmacia far1 = new Farmacia("-33.453114 -70.682136", "Obpo Javier Vasquez Valencia 3455", "Cruz Verde");
                    Farmacia far2 = new Farmacia("-33.454059 -70.692502", "Av Libertador Bernardo O'Higgins 4131, Estacion", "Farmacia Doctor Simi");
                    Farmacia far3 = new Farmacia("-33.456762 -70.701965", "Avenida Liber Bernardo O'higgins 4850", "Farmacias Salcobrand");
                    Farmacia far4 = new Farmacia("-33.440958 -70.630218", "Seminario 92", "Cruz Verde");
                    Farmacia far5 = new Farmacia("-33.439096 -70.624681", "Salvador 648-698", "Farmacias Carmen");
                    Farmacia far6 = new Farmacia("-33.447869 -70.634638", "Santa Isabel 201-299", "Farmacias Ahumada");
                    Farmacia far7 = new Farmacia("-33.479391 -70.731684", "Simon Bolívar 4435", "Farmacia Cerrillos");
                    Farmacia far8 = new Farmacia("-33.509060 -70.758120", "Calle Chacabuco 50 Z", "Farmacia Principal");
                    Farmacia far9 = new Farmacia("-33.443264 -70.649919", "Avda Liber Bernardo O`Higgins 985", "Farmacias Salcobrand");
                    Farmacia far10 = new Farmacia("-33.441321 -70.639287", "Avda Portugal 44", "Santa Gemita");
                    Farmacia far11 = new Farmacia("-33.440294 -70.647999", "Calle San Antonio 2280", "Farmacias Mapuche");


                    //guardar farmacias BD
                    long far1_id = db.createFarmacia(far1);
                    long far2_id = db.createFarmacia(far2);
                    long far3_id = db.createFarmacia(far3);
                    long far4_id = db.createFarmacia(far4);
                    long far5_id = db.createFarmacia(far5);
                    long far6_id = db.createFarmacia(far6);
                    long far7_id = db.createFarmacia(far7);
                    long far8_id = db.createFarmacia(far8);
                    long far9_id = db.createFarmacia(far9);
                    long far10_id = db.createFarmacia(far10);
                    long far11_id = db.createFarmacia(far11);



                    //Remedios
                    Remedio re1 = new Remedio("viadil", "Gotas");
                    Remedio re2 = new Remedio("paracetamol", "500mg");
                    Remedio re3 = new Remedio("nastizol", "300mg");
                    Remedio re4 = new Remedio("adiro100", "100mg");
                    Remedio re5 = new Remedio("cardil", "10mg");
                    Remedio re6 = new Remedio("flumil", "25mg");
                    Remedio re7 = new Remedio("tapsin noche", "polvo");
                    Remedio re8 = new Remedio("tapsin dia", "polvo");

                    //guardar remdios
                    long re1_id = db.createRemedio(re1);
                    long re2_id = db.createRemedio(re2);
                    long re3_id = db.createRemedio(re3);
                    long re4_id = db.createRemedio(re4);
                    long re5_id = db.createRemedio(re5);
                    long re6_id = db.createRemedio(re6);
                    long re7_id = db.createRemedio(re7);
                    long re8_id = db.createRemedio(re8);


                    //crear farmaciaRemedio
                    long fr1 = db.createFarmaciaRemedio(far1_id, re1_id);
                    long fr2 = db.createFarmaciaRemedio(far1_id, re2_id);
                    long fr3 = db.createFarmaciaRemedio(far1_id, re3_id);
                    long fr14 = db.createFarmaciaRemedio(far1_id, re4_id);
                    long fr15= db.createFarmaciaRemedio(far1_id, re5_id);
                    long fr16= db.createFarmaciaRemedio(far1_id, re6_id);

                    long fr21 = db.createFarmaciaRemedio(far2_id, re1_id);
                    long fr22 = db.createFarmaciaRemedio(far2_id, re2_id);
                    long fr23 = db.createFarmaciaRemedio(far2_id, re3_id);
                    long fr24 = db.createFarmaciaRemedio(far2_id, re4_id);
                    long fr25= db.createFarmaciaRemedio(far2_id, re5_id);
                    long fr26= db.createFarmaciaRemedio(far2_id, re6_id);
                    long fr27= db.createFarmaciaRemedio(far2_id, re7_id);

                    long fr6 = db.createFarmaciaRemedio(far3_id, re1_id);
                    long fr7 = db.createFarmaciaRemedio(far3_id, re2_id);
                    long fr8 = db.createFarmaciaRemedio(far3_id, re8_id);

                    long fr41 = db.createFarmaciaRemedio(far4_id, re1_id);
                    long fr42 = db.createFarmaciaRemedio(far4_id, re2_id);
                    long fr43 = db.createFarmaciaRemedio(far4_id, re3_id);
                    long fr47 = db.createFarmaciaRemedio(far4_id, re7_id);
                    long fr48 = db.createFarmaciaRemedio(far4_id, re8_id);
                    long fr44= db.createFarmaciaRemedio(far4_id, re4_id);

                    long fr53= db.createFarmaciaRemedio(far5_id, re1_id);
                    long fr52= db.createFarmaciaRemedio(far5_id, re2_id);
                    long fr54= db.createFarmaciaRemedio(far5_id, re3_id);
                    long fr51= db.createFarmaciaRemedio(far5_id, re4_id);
                    long fr55= db.createFarmaciaRemedio(far5_id, re5_id);
                    long fr57= db.createFarmaciaRemedio(far5_id, re6_id);
                    long fr58= db.createFarmaciaRemedio(far5_id, re7_id);

                    long fr61= db.createFarmaciaRemedio(far6_id, re1_id);
                    long fr62= db.createFarmaciaRemedio(far6_id, re2_id);
                    long fr63= db.createFarmaciaRemedio(far6_id, re3_id);
                    long fr5664= db.createFarmaciaRemedio(far6_id, re7_id);
                    long fr56= db.createFarmaciaRemedio(far6_id, re8_id);

                    long fr78= db.createFarmaciaRemedio(far7_id, re1_id);
                    long fr71= db.createFarmaciaRemedio(far7_id, re7_id);
                    long fr72= db.createFarmaciaRemedio(far7_id, re8_id);

                    long fr80= db.createFarmaciaRemedio(far8_id, re2_id);
                    long fr81= db.createFarmaciaRemedio(far8_id, re7_id);
                    long fr82= db.createFarmaciaRemedio(far8_id, re8_id);

                    long fr90= db.createFarmaciaRemedio(far9_id, re3_id);
                    long fr91= db.createFarmaciaRemedio(far9_id, re6_id);
                    long fr92= db.createFarmaciaRemedio(far9_id, re8_id);

                    long fr0= db.createFarmaciaRemedio(far10_id, re3_id);
                    long fr10= db.createFarmaciaRemedio(far10_id, re6_id);
                    long fr100= db.createFarmaciaRemedio(far10_id, re8_id);

                    long fr11= db.createFarmaciaRemedio(far10_id, re4_id);
                    // Don't forget to close database connection
                    db.closeDB();
                }
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
