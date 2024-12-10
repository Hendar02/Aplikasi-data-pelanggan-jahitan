package com.abrorrahmad.perpustakaanapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_jahitan.db";
    public static final String tabel_name = "Data_Jahitan";

    public static final String row_id = "_id";
    public static final String row_nama = "Nama";
    public static final String row_lebarPundak = "LebarPundak";
    public static final String row_lingkarBadan = "LingkarBadan";
    public static final String row_panjangTangan = "PanjangTangan";
    public static final String row_lingkarPinggang = "LingkarPinggang";
    public static final String row_lingkarPinggul = "LingkarPinggul";
    public static final String row_panjangBaju = "PanjangBaju";
    public static final String row_modelBaju = "ModelBaju";
    public static final String row_tglSelesai = "Tglselesai";
    public static final String row_tglMasuk = "Tglmasuk";
    public static final String row_status = "Status";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE "+ tabel_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nama + " TEXT , " + row_modelBaju + " TEXT, " + row_lebarPundak + " TEXT, " + row_panjangBaju + " TEXT, " + row_lingkarPinggul + " TEXT, " + row_lingkarPinggang + " TEXT, " + row_panjangTangan+ " TEXT, " + row_lingkarBadan + " TEXT, " + row_tglMasuk + " TEXT, " + row_tglSelesai + " TEXT, " + row_status + " TEXT) ";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tabel_name);

    }

    //Get All SQL data
    public Cursor allData(){
        Cursor cur = db.rawQuery(" SELECT * FROM " + tabel_name + " ORDER BY " + row_id + " DESC", null);
        return cur;
    }


    //GET DATA Diproses
    public Cursor dataDiproses(){
        Cursor cur = db.rawQuery(" SELECT * FROM " + tabel_name + " WHERE " + row_status + "=" + "'Diproses'", null);
        return cur;
    }

    //GET DATA Selesai
    public Cursor dataSelesai(){
        Cursor cur = db.rawQuery(" SELECT * FROM " + tabel_name + " WHERE " + row_status + "=" + "'Selesai'", null);
        return cur;
    }


    //GET 1 data  By Id
    public Cursor oneData(long id){
        Cursor cur = db.rawQuery(" SELECT * FROM " + tabel_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data

    public void insertData (ContentValues values){
        db.insert(tabel_name, null, values);
    }

    //update Data
    public void updateData (ContentValues values, long id){
        db.update(tabel_name, values, row_id + "=" + id, null);
    }

    //Delete data
    public void deleteData (long id){
        db.delete(tabel_name, row_id + "=" + id, null);
    }
    public int jumlahDataDiproses(){
        int count = 0;
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + tabel_name + " WHERE " + row_status + "=?", new String[]{"Diproses"});

        if (cur.moveToFirst()) {
            count = cur.getInt(0);
        }

        cur.close();
        return count;
    }
    public int jumlahDataSelesai(){
        int count = 0;
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + tabel_name + " WHERE " + row_status + "=?", new String[]{"Selesai"});

        if (cur.moveToFirst()) {
            count = cur.getInt(0);
        }

        cur.close();
        return count;
    }
    public int getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Data_Jahitan", null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

}
