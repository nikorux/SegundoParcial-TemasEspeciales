package com.pucmm.segundoparcial_temasespeciales.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSec extends SQLiteOpenHelper {

    public static final String DB_NAME = "GEEKPING.DB";


    private static final String CATE_TABLE_NAME = "CREATE TABLE Categorias (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL)";

    private static final String PROD_TABLE_NAME = "CREATE TABLE Productos (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT NOT NULL, " +
            "categoria TEXT NOT NULL, " +
            "precio TEXT NOT NULL)";

    public static final int DB_VERSION = 1;
    public DBSec(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static com.pucmm.segundoparcial_temasespeciales.DB.DBSec instance;

    public static synchronized com.pucmm.segundoparcial_temasespeciales.DB.DBSec getInstance(Context c) {
        if (instance == null) {
            instance = new com.pucmm.segundoparcial_temasespeciales.DB.DBSec(c);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROD_TABLE_NAME);
        db.execSQL(CATE_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Productos");
        db.execSQL("DROP TABLE IF EXISTS Categorias");
        onCreate(db);
    }
}
