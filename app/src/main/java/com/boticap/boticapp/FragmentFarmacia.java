package com.boticap.boticapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentFarmacia extends Fragment {

    View rootView;
    ListView lista;
    ArrayList<Marcador> nombre = new ArrayList<Marcador>();
    private DataSource data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_farmacia, container, false);

        data = new DataSource(getActivity());
        try {
            data.abrir();
        }
        catch (Exception e){
            Log.i("hola", "hola");
        }
        nombre.addAll(data.obtenerMarcadores());
        lista = (ListView) rootView.findViewById(R.id.lista_prueba);
        lista.setAdapter(new ListViewAdapter(getActivity(), nombre));
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //funcion
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("posicion", position);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
