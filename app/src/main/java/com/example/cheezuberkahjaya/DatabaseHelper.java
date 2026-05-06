package com.example.cheezuberkahjaya;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "berkahjaya.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_BARANG = "barang";
    public static final String COL_ID = "id";
    public static final String COL_NAMA = "nama_barang";
    public static final String COL_JUMLAH = "jumlah";
    public static final String COL_JENIS = "jenis";
    public static final String COL_TANGGAL = "tanggal";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_BARANG + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAMA + " TEXT, " +
                COL_JUMLAH + " INTEGER, " +
                COL_JENIS + " TEXT, " +
                COL_TANGGAL + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARANG);
        onCreate(db);
    }
}