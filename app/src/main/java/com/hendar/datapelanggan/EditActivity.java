package com.abrorrahmad.perpustakaanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abrorrahmad.perpustakaanapp.adapter.DBHelper;

public class EditActivity extends AppCompatActivity {

    protected Cursor cursor;
    DBHelper dbHelper;
    Button btnEdit;
    EditText nama, tgl_masuk, tgl_selesai, lebarPundak, lingkarBadan, panjangTangan, lingkarPinggang, lingkarPinggul, panjangBaju, modelBaju;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dbHelper = new DBHelper(this);
        nama = findViewById(R.id.txNama);
        tgl_masuk = findViewById(R.id.txTglmasuk);
        tgl_selesai = findViewById(R.id.txTglselesai);
        lebarPundak = findViewById(R.id.txLebarPundak);
        lingkarBadan = findViewById(R.id.txlingkarBadan);
        panjangTangan = findViewById(R.id.txPanjangTangan);
        lingkarPinggang = findViewById(R.id.txLingkarPinggang);
        lingkarPinggul = findViewById(R.id.txLingkarPinggul);
        panjangBaju = findViewById(R.id.txPanjangBaju);
        modelBaju = findViewById(R.id.txModelBaju);
        btnEdit = findViewById(R.id.btnEdit);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM Data_Jahitan WHERE nama = '" +
                getIntent().getStringExtra("nama")+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() >0)
        {
            cursor.moveToPosition(0);
            nama.setText(cursor.getString(1).toString());
            tgl_masuk.setText(cursor.getString(10).toString());
            tgl_selesai.setText(cursor.getString(9).toString());
            lebarPundak.setText(cursor.getString(4).toString());
            lingkarBadan.setText(cursor.getString(5).toString());
            panjangTangan.setText(cursor.getString(6).toString());
            lingkarPinggang.setText(cursor.getString(7).toString());
            lingkarPinggul.setText(cursor.getString(8).toString());
            panjangBaju.setText(cursor.getString(3).toString());
            modelBaju.setText(cursor.getString(2).toString());

        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update Data_Jahitan set row_nama ='" +
                        nama.getText().toString() +"', tgl_masuk='" +
                        tgl_masuk.getText().toString() +"', tgl_selesai='" +
                        tgl_selesai.getText().toString() +"', lebarPundak='" +
                        lebarPundak.getText().toString() +"', lingkarBadan='" +
                        lingkarBadan.getText().toString() +"', panjangTangan='" +
                        panjangTangan.getText().toString() +"', lingkarPinggang='" +
                        lingkarPinggang.getText().toString() +"', lingkarPinggul='" +
                        lingkarPinggul.getText().toString() +"', panjangBaju='" +
                        panjangBaju.getText().toString() +"', row_modelBaju'" +
                        modelBaju.getText().toString() +"' where row_nama ='" +
                        getIntent().getStringExtra("nama") +"'");
                Toast.makeText(EditActivity.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                DashboardActivity.ma.Refreshlist();
                finish();

            }
        });

    }
}