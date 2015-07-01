package com.boticap.boticapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ActionBar actionBar;
    long id_remedio_recibido;
    long id_farmacia_recibida;
    ArrayList<FilaListView> filas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_en_farmacia);
        contexto = this;
        actionBar = getSupportActionBar();
        actionBar.setTitle("BoticApp");
        actionBar.setDisplayHomeAsUpEnabled(true);

        bd = new DatabaseHelper(getApplicationContext());

        nombreMedicamento = (TextView)findViewById(R.id.nombreMedicamento);
        descripcionMedicamento = (TextView)findViewById(R.id.comentarioMedicamento);
        comentariosUsuarios = (ListView)findViewById(R.id.listViewComentarios);
        agregarComentario = (Button)findViewById(R.id.botonAgregarComentario);

        //recepcion del id del remedio enviado
        Intent miIntent = getIntent();
        id_remedio_recibido = miIntent.getLongExtra("id_remedio", 0);
        Remedio remedio = bd.getRemedio(id_remedio_recibido);

        //recepcion del id de la farmacia enviado
        id_farmacia_recibida = miIntent.getIntExtra("id_farmacia_seleccionada", 0);

        //obtiene id tabla farmacia_remedio
        //Problema: no esta encontrando resultados
        final long id_farmacia_remedio = bd.getIdFarmaciaRemedio(id_remedio_recibido, id_farmacia_recibida);

        if(savedInstanceState == null){
            listaComentarios = bd.getAllComentariosFarmaciaRemedio(id_farmacia_remedio);
            filas = new ArrayList<>();
            for (int i = 0; i < listaComentarios.size(); i++) {
                //Log.i("hola", "COMENTARIO Y PRECIO SON: " + listaComentarios.get(i).getComentario() + " " + listaComentarios.get(i).getPrecio());
                filas.add(new FilaListView(listaComentarios.get(i).getComentario(),
                        String.valueOf(listaComentarios.get(i).getPrecio()), listaComentarios.get(i).getFechaHora()));
            }
        }
        else{
            filas = savedInstanceState.getParcelableArrayList("filas");
        }

        mAdapter = new MyAdapter(this, filas);
        comentariosUsuarios.setAdapter(mAdapter);

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

                    public void onClick(DialogInterface dialog, int which) {
                        Editable editablePrecio = textPrecio.getText();
                        Editable editableComentario = textComentario.getText();
                        if (editablePrecio.toString().isEmpty() || editableComentario.toString().isEmpty()) {
                            Toast.makeText(MedicamentoEnFarmacia.this, "Comentario no agregado. El precio y el comentario son obligatorios.",
                                    Toast.LENGTH_SHORT)
                                    .show();

                        } else {
                            //descripcionComentarios.clear();
                            //precioComentario.clear();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String fecha = sdf.format(new Date());
                            comentario = new Comentario(textComentario.getText().toString(), fecha, Integer.parseInt(textPrecio.getText().toString()));
                            bd.createComentario(comentario, id_farmacia_remedio);
                            FilaListView nuevaFila = new FilaListView(comentario.getComentario(), String.valueOf(comentario.getPrecio()), comentario.getFechaHora());
                            mAdapter.add(nuevaFila);
                            mAdapter.notifyDataSetChanged();
                            textComentario.getText().clear();
                            textPrecio.getText().clear();
                            dialog.dismiss();
                        }
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
        //listaComentarios = bd.getAllComentariosFarmaciaRemedio(id_farmacia_remedio);
        //if(!listaComentarios.isEmpty()) {
            //for (int i = 0; i < listaComentarios.size(); i++) {
                //Log.i("hola", "COMENTARIO Y PRECIO SON: " + listaComentarios.get(i).getComentario() + " " + listaComentarios.get(i).getPrecio());
                //descripcionComentarios.add(listaComentarios.get(i).getComentario());
              //  precioComentario.add(listaComentarios.get(i).getPrecio());
            //}
        //}

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicamento_en_farmacia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(contexto, MapsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id_remedio_seleccionado", id_remedio_recibido);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("filas", filas);
    }

    private class MyAdapter extends ArrayAdapter<FilaListView> {

        private final Activity context;
        private ArrayList<FilaListView> filas;

        public MyAdapter(Activity context, ArrayList<FilaListView> filas) {
            super(context, R.layout.casilla, filas);
            this.context = context;
            this.filas = filas;
        }
        public View getView(int position,View view,ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.casilla, null,true);

            TextView txtPrecio = (TextView) rowView.findViewById(R.id.precio);
            TextView txtFecha = (TextView) rowView.findViewById(R.id.fecha_comentario);
            TextView txtComentario = (TextView) rowView.findViewById(R.id.comentario);

            txtPrecio.setText("$" + filas.get(position).getPrecio());
            txtFecha.setText(filas.get(position).getFecha());
            txtComentario.setText(filas.get(position).getItemName());
            return rowView;

        }
    }

    public class FilaListView implements Parcelable{

        private String itemName;
        private String precio;
        private String fecha;

        public FilaListView(String itemName, String precio, String fecha){
            this.itemName = itemName;
            this.precio = precio;
            this.fecha = fecha;
        }

        public String getItemName() {
            return itemName;
        }

        public String getPrecio() {
            return precio;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public void setPrecio(String precio) {
            this.precio = precio;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}
