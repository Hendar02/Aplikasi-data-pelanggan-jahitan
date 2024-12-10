package com.abrorrahmad.perpustakaanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.abrorrahmad.perpustakaanapp.adapter.DBHelper;

public class LihatdataActivity extends AppCompatActivity {

    protected Cursor cursor;
    DBHelper dbHelper;
    TextView nama, tglMasuk, tglSelesai, LebarPundak, LingkarBadan, PanjangTanggan, LingkarPinggang, LingkarPinggul, PanjangBaju, Model;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihatdata);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();


        dbHelper = new DBHelper(this);
        nama = findViewById(R.id.txNama);
        tglMasuk = findViewById(R.id.txTglmasuk);
        tglSelesai = findViewById(R.id.txTglselesai);
        LebarPundak = findViewById(R.id.txLebarPundak);
        LingkarBadan = findViewById(R.id.txlingkarBadan);
        PanjangTanggan = findViewById(R.id.txPanjangTangan);
        LingkarPinggang = findViewById(R.id.txLingkarPinggang);
        LingkarPinggul = findViewById(R.id.txLingkarPinggul);
        PanjangBaju = findViewById(R.id.txPanjangBaju);
        Model = findViewById(R.id.txModelBaju);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM Data_Jahitan WHERE nama = '" +
                getIntent().getStringExtra("nama") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            nama.setText(cursor.getString(1).toString());
            tglMasuk.setText(cursor.getString(9).toString());
            tglSelesai.setText(cursor.getString(10).toString());
            Model.setText(cursor.getString(2).toString());
            LebarPundak.setText(cursor.getString(3).toString());
            LingkarBadan.setText(cursor.getString(8).toString());
            PanjangTanggan.setText(cursor.getString(7).toString());
            LingkarPinggang.setText(cursor.getString(6).toString());
            LingkarPinggul.setText(cursor.getString(5).toString());
            PanjangBaju.setText(cursor.getString(4).toString());
        }
    }

    private void setSupportActionBar(Toolbar tolbar) {
    }
}