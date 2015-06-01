package com.boticap.boticapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataSource {
    BaseDeDatos bdSQLHelper;
    SQLiteDatabase db;
    String[] cols = {BaseDeDatos.TITULO, BaseDeDatos.FRAGMENTO, BaseDeDatos.COORDENADAS};

    public DataSource(Context contexto){
        bdSQLHelper = new BaseDeDatos(contexto);
    }

    public void abrir() throws SQLException{
        db = bdSQLHelper.getWritableDatabase();
    }

    public void cerrar(){
        db.close();
    }

    public void agregarMarcadores(Marcador m){
        ContentValues v = new ContentValues();

        v.put(BaseDeDatos.TITULO, m.getTitulo());
        v.put(BaseDeDatos.FRAGMENTO, m.getFragmento());
        v.put(BaseDeDatos.COORDENADAS,m.getCoordenadas());

        db.insert(BaseDeDatos.TITULO_TABLA, null, v);
    }

    public List<Marcador> obtenerMarcadores(){
        List<Marcador> m = new ArrayList<Marcador>();
        Cursor cursor = db.query(BaseDeDatos.TITULO_TABLA, cols, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Marcador marcador = cursorAMarcador(cursor);
            m.add(marcador);
            cursor.moveToNext();
        }
        return m;
    }

    private Marcador cursorAMarcador(Cursor cursor) {
        Marcador m = new Marcador();
        m.setTitulo(cursor.getString(0));
        m.setFragmento(cursor.getString(1));
        m.setCoordenadas(cursor.getString(2));
        return m;
    }
}