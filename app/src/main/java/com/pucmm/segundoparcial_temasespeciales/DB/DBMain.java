package com.pucmm.segundoparcial_temasespeciales.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBMain {

    private DBSec dbSec;
    private Context context;
    protected SQLiteDatabase db;

    public DBMain(Context context) {
        this.context = context;
        dbSec = DBSec.getInstance(context);
        open();
    }

    public void open() throws SQLException {
        if (dbSec == null) {
            dbSec = DBSec.getInstance(context);
        }
        db = dbSec.getWritableDatabase();
    }

    public void close() {
        dbSec.close();
    }

    public void createProduct(String nombre, String precio, String categoria) {
        open();
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("categoria", categoria);
        cv.put("precio", precio);
        db.insert("Productos", null, cv);
    }

    public void createCategory(String nombre) {
        open();
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        db.insert("Categorias", null, cv);
    }

    public Cursor getProducts() {
        open();
        String[] columns = new String[] { "_id", "nombre", "categoria", "precio" };
        Cursor cursor = db.query("Productos", columns, null, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        return cursor;
    }

    public List<String> getCategories(){
        open();
        List<String> list = new ArrayList<String>();

        String selectQuery = "SELECT * FROM Categorias";

        dbSec.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public int updateProducts(long _id, String nombre, String precio, String categoria) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("categoria", categoria);
        cv.put("precio", precio);
        return db.update("Productos", cv, "_id = " + _id, null);
    }

    public void removeProduct(long _id) {
        db.delete("Productos",  "_id= " + _id, null);
    }
}
