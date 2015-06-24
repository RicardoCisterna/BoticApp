package BD.helper;

/**
 * Created by Ricardo on 23-06-2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import BD.model.Farmacia;
import BD.model.Remedio;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BoticappBd";

    // Table Names
    private static final String TABLE_COMENTARIO = "comentario";
    private static final String TABLE_FARMACIA = "farmacia";
    private static final String TABLE_REMEDIO = "remedio";
    private static final String TABLE_FARMACIA_REMEDIO = "farmacia_remedio";

    // Comunes column names
    private static final String KEY_ID = "id";
    private static final String KEY_COMENTARIO = "comentario";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_PRECIO = "precio";

    // Farmacia Table - column names
    private static final String KEY_DIRECCION = "direccion";
    private static final String KEY_POSICION = "posicion";


    // Remedio Table - column names


    //Comentarios
    private static final String KEY_FECHA = "fecha";
    private static final String KEY_FARMACIA_REMEDIO_ID = "farmacia_remedio_id";



    // Farmacia-remedio Table - column names
    private static final String KEY_FARMACIA_ID = "farmacia_id";
    private static final String KEY_REMEDIO_ID = "remedio_id";


    // Table Create Statements
    // Farmacia table create statement
    private static final String CREATE_TABLE_FARMACIA = "CREATE TABLE "
            + TABLE_FARMACIA + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOMBRE
            + " TEXT," + KEY_DIRECCION + " TEXT," + KEY_POSICION
            + " TEXT" + ")";

    // Remedio table create statement
    private static final String CREATE_TABLE_REMEDIO = "CREATE TABLE " + TABLE_REMEDIO
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOMBRE + " TEXT,"
            + KEY_COMENTARIO + " TEXT" + ")";

    // Comentario table create statement
    private static final String CREATE_TABLE_COMENTARIO = "CREATE TABLE " + TABLE_COMENTARIO
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FARMACIA_REMEDIO_ID + " INTEGER," + KEY_COMENTARIO + " TEXT,"
            + KEY_FECHA + " DATETIME" + KEY_PRECIO + " INTEGER"+ ")";

    // FarmaciaRemdio table create statement
    private static final String CREATE_TABLE_FARMACIA_REMEDIO = "CREATE TABLE "
            + TABLE_FARMACIA_REMEDIO + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_FARMACIA_ID + " INTEGER," + KEY_REMEDIO_ID + " INTEGER,"
            + KEY_PRECIO + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_FARMACIA);
        db.execSQL(CREATE_TABLE_COMENTARIO);
        db.execSQL(CREATE_TABLE_REMEDIO);
        db.execSQL(CREATE_TABLE_FARMACIA_REMEDIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMACIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMEDIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMENTARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMACIA_REMEDIO);

        // create new tables
        onCreate(db);

    }
    //CREATE
    /*
 * Creating a Farmacia
 */
    public long createFarmacia(Farmacia farmacia) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, farmacia.getNombre());
        values.put(KEY_DIRECCION, farmacia.getDireccion());
        values.put(KEY_POSICION, farmacia.getPosicion());

        // insert row
        long farmacia_id = db.insert(TABLE_FARMACIA, null, values);


        return farmacia_id;
    }

    /*
 * Creating Remedio
 */
    public long createRemedio(Remedio remedio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, remedio.getNombre());
        values.put(KEY_COMENTARIO, remedio.getComentario());

        // insert row
        long Remedio_id = db.insert(TABLE_REMEDIO, null, values);

        return Remedio_id;
    }

    /*
    Obtener 1
     */
    //Farmacia
    public Farmacia getFarmacia(long farmacia_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_FARMACIA + " WHERE "
                + KEY_ID + " = " + farmacia_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Farmacia td = new Farmacia();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));
        td.setDireccion(c.getString(c.getColumnIndex(KEY_DIRECCION)));
        td.setPosicion(c.getString(c.getColumnIndex(KEY_POSICION)));

        return td;
    }


    //Remedio
    public Remedio getRemedio(long remedio_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_REMEDIO + " WHERE "
                + KEY_ID + " = " + remedio_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Remedio td = new Remedio();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));
        td.setComentario(c.getString(c.getColumnIndex(KEY_COMENTARIO)));

        return td;
    }



    /*
 * Obtener Todos
 * */
    //
    public List<Farmacia> getAllFarmacias() {
        List<Farmacia> farmacias = new ArrayList<Farmacia>();
        String selectQuery = "SELECT  * FROM " + TABLE_FARMACIA;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Farmacia td = new Farmacia();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));
                td.setDireccion(c.getString(c.getColumnIndex(KEY_DIRECCION)));
                td.setPosicion(c.getString(c.getColumnIndex(KEY_POSICION)));

                // adding to farmacias list
                farmacias.add(td);
            } while (c.moveToNext());
        }

        return farmacias;
    }
    /*
 * Obtener Todos
 * */
    //
    public List<Remedio> getAllRemedios() {
        List<Remedio> remedios = new ArrayList<Remedio>();
        String selectQuery = "SELECT  * FROM " + TABLE_REMEDIO;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Remedio td = new Remedio();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));
                td.setComentario(c.getString(c.getColumnIndex(KEY_COMENTARIO)));

                // adding to farmacias list
                remedios.add(td);
            } while (c.moveToNext());
        }

        return remedios;
    }


    /*
 * todos los remedios de una farmacia
 * */
    public List<Remedio> getAllRemediosFarmacia(Farmacia farmacia) {
        List<Remedio> RemediosFarmacias = new ArrayList<Remedio>();

        String selectQuery = "SELECT  * FROM " + TABLE_REMEDIO + " re, "
                + TABLE_FARMACIA + " fa, " + TABLE_FARMACIA_REMEDIO + " fr WHERE fa.id"
                + " = '" + String.valueOf(farmacia.getId()) + "'" + " AND re." + KEY_ID
                + " = " + "fr." + KEY_REMEDIO_ID + " AND fa." + KEY_ID + " = "
                + "fr." + KEY_FARMACIA_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Remedio re = new Remedio();
                re.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                re.setNombre((c.getString(c.getColumnIndex(KEY_NOMBRE))));
                re.setComentario(c.getString(c.getColumnIndex(KEY_COMENTARIO)));

                // adding to farmacia
                RemediosFarmacias.add(re);
            } while (c.moveToNext());
        }

        return RemediosFarmacias;
    }

    /*
 * Updating a Farmacia
 */
    public int updateFarmacia(Farmacia farmacia) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, farmacia.getNombre());
        values.put(KEY_POSICION, farmacia.getPosicion());
        values.put(KEY_DIRECCION, farmacia.getDireccion());

        // updating row
        return db.update(TABLE_FARMACIA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(farmacia.getId()) });
    }

    /*
* Updating a Remedio
*/
    public int updateRemedio(Remedio remedio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, remedio.getNombre());
        values.put(KEY_COMENTARIO, remedio.getComentario());


        // updating row
        return db.update(TABLE_FARMACIA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(remedio.getId()) });
    }

    /*
 * Borrar Farmacia
 */
    public void deleteFarmacia(long farmacia_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FARMACIA, KEY_ID + " = ?",
                new String[] { String.valueOf(farmacia_id) });
    }

    /*
 * Borrar Remedio
 */
    public void deleteRemedio(long remedio_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMEDIO, KEY_ID + " = ?",
                new String[] { String.valueOf(remedio_id) });
    }
    /*
     * Creating farmacia_remedio
     */
    public long createFarmaciaRemedio(long farmacia_id, long remedio_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMEDIO_ID, remedio_id);
        values.put(KEY_FARMACIA_ID, farmacia_id);

        long id = db.insert(TABLE_FARMACIA_REMEDIO, null, values);

        return id;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
