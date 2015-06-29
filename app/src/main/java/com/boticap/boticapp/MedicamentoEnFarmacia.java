package com.boticap.boticapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BD.helper.DatabaseHelper;
import BD.model.Comentario;
import BD.model.Remedio;


public class MedicamentoEnFarmacia extends ActionBarActivity {
    Context contexto;
    TextView nombreMedicamento;
    TextView descripcionMedicamento;
    DatabaseHelper bd;
    ListView comentariosUsuarios;
    Button agregarComentario;
    Comentario comentario;
    EditText textPrecio;
    EditText textComentario;
    MyAdapter mAdapter;
    List<Comentario> listaComentarios;
    ArrayList<String> descripcionComentarios = new ArrayList<String>();

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
        agregarComentario = (Button)findViewById(R.id.botonAgregarComentario);

        //recepcion del id del remedio enviado
        Intent miIntent = getIntent();
        long id_remedio_recibido = miIntent.getLongExtra("id_remedio", 0);
        Remedio remedio = bd.getRemedio(id_remedio_recibido);

        //recepcion del id de la farmacia enviado
        long id_farmacia_recibida = miIntent.getIntExtra("id_farmacia_seleccionada", 0);
        Log.i("hola", "ID REMEDIO: " + id_remedio_recibido + " ID FARMACIA: " + id_farmacia_recibida);

        //obtiene id tabla farmacia_remedio
        //Problema: no esta encontrando resultados
        long id_farmacia_remedio = bd.getIdFarmaciaRemedio(id_remedio_recibido, id_farmacia_recibida);

        nombreMedicamento.setText("Medicamento: " + remedio.getNombre());
        descripcionMedicamento.setText("Descripcion: " + remedio.getComentario());

        //pop up que agrega un comentario
        agregarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater linf = LayoutInflater.from(contexto);
                final View inflator = linf.inflate(R.layout.popup_agregar_comentario, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
                builder.setTitle("Agregar Comentario");
                builder.setCancelable(false);
                builder.setView(inflator);
                textPrecio = (EditText)inflator.findViewById(R.id.textPrecio);
                textComentario = (EditText)inflator.findViewById(R.id.textComentario);
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String fecha = sdf.format(new Date());
                        comentario = new Comentario(textComentario.getText().toString(), fecha, Integer.parseInt(textPrecio.getText().toString()));
                        //bd.createComentario(comentario, id_farmacia_remedio);
                        mAdapter.add(comentario.getComentario());
                        mAdapter.notifyDataSetChanged();
                        textComentario.getText().clear();
                        textPrecio.getText().clear();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //listview de los comentarios
        listaComentarios = bd.getAllComentariosFarmaciaRemedio(id_farmacia_remedio);
        for (int i = 0; i < listaComentarios.size(); i++) {
            descripcionComentarios.add(listaComentarios.get(i).getComentario());
        }
        mAdapter = new MyAdapter(this,android.R.layout.simple_list_item_1, descripcionComentarios);
        comentariosUsuarios.setAdapter(mAdapter);

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
        outState.putStringArrayList("comentarios", descripcionComentarios);
    }

    private class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }
    }
}
