package com.boticap.boticapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {
    private static ArrayList<Marcador> nombre = new ArrayList<Marcador>();
    private LayoutInflater inflater;

    public ListViewAdapter(Context contexto, ArrayList<Marcador> listadoNombres){
        nombre = listadoNombres;
        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return nombre.size();
    }

    @Override
    public Object getItem(int position) {
        return nombre.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.casilla, null);
            text = (TextView) convertView.findViewById(R.id.casilla);
            convertView.setTag(text);
        }
        else {
            text = (TextView) convertView.getTag();
        }
        text.setText(nombre.get(position).getTitulo());

        return convertView;
    }
}
