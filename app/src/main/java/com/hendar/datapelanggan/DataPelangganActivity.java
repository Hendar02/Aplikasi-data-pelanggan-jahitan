package com.abrorrahmad.perpustakaanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;

import com.abrorrahmad.perpustakaanapp.adapter.CustomCursorAdapter;
import com.abrorrahmad.perpustakaanapp.adapter.DBHelper;

public class DataPelangganActivity extends AppCompatActivity {


    String[] daftar;
    ListView listView;
    Menu menu;
    protected Cursor cursor;
    DBHelper dbHelper;
    public static DataPelangganActivity ma;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pelanggan);
        ma = this;
        dbHelper = new DBHelper(this);

        Refreshlist();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Data Pelanggan");
        }
    }


    private void Refreshlist() {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM Data_Jahitan", null);
            daftar = new String[cursor.getCount()];
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++){
                cursor.moveToPosition(i);
                daftar[i] = cursor.getString(1).toString();
            }
            int lisview;
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
            listView.setSelected(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3){
                    final String selection = daftar[arg2];
                    final CharSequence[] dialogitem = {"Lihat data","Ubah data", "Hapus data"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(DataPelangganActivity.this);
                    builder.setTitle("Pilihan");
                    builder.setItems(dialogitem, new  DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            switch (item){
                                case 0:
                                    Intent i = new Intent(getApplicationContext(), LihatdataActivity.class);
                                    i.putExtra("nama", selection);
                                    startActivity(i);
                                    break;

                                case 1:
                                    Intent in = new Intent(getApplicationContext(), EditActivity.class);
                                    in.putExtra("nama", selection);
                                    startActivity(in);
                                    break;

                                case 2:
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    db.execSQL("delete from Data_Jahitan where nama ='" + selection + "'");
                                    Refreshlist();
                                    break;

                            }
                        }

                    });
                    builder.create().show();

                }


            });


        }
    }