package com.boticap.boticapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import BD.helper.DatabaseHelper;
import BD.model.Remedio;


public class MedicamentoEnFarmacia extends ActionBarActivity {
    Context contexto;
    TextView nombreMedicamento;
    TextView descripcionMedicamento;
    DatabaseHelper bd;
    ListView comentariosUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_en_farmacia);
        contexto = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("BoticApp");
        bd = new DatabaseHelper(getApplicationContext());

        nombreMedicamento = (TextView)findViewById(R.id.nombreMedicamento);
        descripcionMedicamento = (TextView)findViewById(R.id.comentarioMedicamento);
        comentariosUsuarios = (ListView)findViewById(R.id.listViewComentarios);

        Intent miIntent = getIntent();
        long id_remedio_recibido = miIntent.getLongExtra("id_remedio", 0);
        Remedio remedio = bd.getRemedio(id_remedio_recibido);

        nombreMedicamento.setText("Medicamento: " + remedio.getNombre());
        descripcionMedicamento.setText("Descripcion: " + remedio.getComentario());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicamento_en_farmacia, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
