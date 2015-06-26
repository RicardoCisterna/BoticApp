package com.boticap.boticapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import BD.helper.DatabaseHelper;
import BD.model.Remedio;

public class FragmentMedicamento extends Fragment {
    EditText textoBuscador;
    Button botonBuscar;
    ListView listadoMedicamentos;
    DatabaseHelper bd;
    View rootView;
    MyAdapter mAdapter;
    ArrayList<String> nombreRemedios = new ArrayList<String>();
    List<Remedio> remedios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_medicamento, container, false);

        textoBuscador = (EditText)rootView.findViewById(R.id.textoBuscador);
        botonBuscar = (Button)rootView.findViewById(R.id.botonBuscar);
        listadoMedicamentos = (ListView)rootView.findViewById(R.id.listView);
        bd = new DatabaseHelper(getActivity());

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (savedInstanceState != null) {
            nombreRemedios = savedInstanceState.getStringArrayList("remedios");
        }

        mAdapter = new MyAdapter(getActivity(),android.R.layout.simple_list_item_1, nombreRemedios);
        listadoMedicamentos.setAdapter(mAdapter);

        listadoMedicamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent miIntent = new Intent(getActivity(), MapsActivity.class);
                miIntent.putExtra("id_remedio_seleccionado", remedios.get(position).getId());
                startActivity(miIntent);
            }
        });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.clear();
                nombreRemedios.clear();
                Editable nombreMedicamentoBuscado = textoBuscador.getText();
                if (nombreMedicamentoBuscado.toString().isEmpty()){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Error")
                            .setMessage("Debe ingresar un Medicamento")
                            .setPositiveButton("Ok", null)
                            .show();
                }
                else {
                    // Esconde el teclado
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textoBuscador.getWindowToken(), 0);

                    remedios = bd.getAllRemediosByName(textoBuscador.getText().toString());
                    for (int i = 0; i < remedios.size(); i++) {
                        nombreRemedios.add(remedios.get(i).getNombre());
                    }
                    mAdapter.addAll(nombreRemedios);
                    mAdapter.notifyDataSetChanged();
                    textoBuscador.getText().clear();
                }
            }
        });

        return rootView;
    }

    private class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("remedios", nombreRemedios);
    }

}
