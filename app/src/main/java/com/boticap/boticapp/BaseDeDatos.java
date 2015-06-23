package com.boticap.boticapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {

    public static final String TITULO_TABLA = "posiciones";

    public static final String ID_COL = "id_col";
    public static final String TITULO = "titulo_lugar";
    public static final String FRAGMENTO = "fragmento_lugar";
    public static final String COORDENADAS = "coordenadas_lugar";

    private static final int DB_VERSION = 1;
    private static final String DB_NOMBRE = "boticapp.db";
    private static final String DB_CREATE =
            "create table " + TITULO_TABLA + "("
                    + ID_COL + " integer primary key autoincrement, "
                    + TITULO + " text, "
                    + FRAGMENTO + " text, "
                    + COORDENADAS + " text);";
    private static final String DATOS_1 =
            "insert into " + TITULO_TABLA + " (" + TITULO + "," + FRAGMENTO + "," + COORDENADAS + ") values ('Farmacia Cruz Verde','Estacion Central','-33.452228 -70.682610')";
    private static final String DATOS_2 =
            "insert into " + TITULO_TABLA + " (" + TITULO + "," + FRAGMENTO + "," + COORDENADAS + ") values ('Farmacia Salcobrand', 'Estación Central','-33.453660 -70.688318')";
    private static final String DATOS_3 =
            "insert into " + TITULO_TABLA + " (" + TITULO + "," + FRAGMENTO + "," + COORDENADAS + ") values ('Farmacia Hanneman','Estación Central','-33.450814 -70.679435')";


    public BaseDeDatos(Context context){
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DB_CREATE);
        db.execSQL(DATOS_1);
        db.execSQL(DATOS_2);
        db.execSQL(DATOS_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TITULO_TABLA);
        onCreate(db);
    }
}