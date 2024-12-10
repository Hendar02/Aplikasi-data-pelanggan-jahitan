package com.abrorrahmad.perpustakaanapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.abrorrahmad.perpustakaanapp.adapter.CustomCursorAdapter;
import com.abrorrahmad.perpustakaanapp.adapter.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, DialogChoise.DialogChoiceListener {

    ListView Is;
    DBHelper dbHelper;
    Context context;
    int listData;
    SharedPreferences viewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        Is = (ListView)findViewById(R.id.list_pelanggan);
        Is.setOnItemClickListener(this);

        viewData = getSharedPreferences("currentListView", 0);
        listData = viewData.getInt("currentListView", 0);

        toolbar.setTitle("Antrian");

        setSupportActionBar(toolbar);
        setupListView();
    }

    //untuk menampilkan urutan data
    private void setupListView() {
        if (listData == 0){
            allData();
        }else if (listData == 1){
            dataDiproses();
        }else if(listData == 2){
            dataSelesai();
        }
    }

    //menampilkan data dengan listview yang sudah di tentukan
    public void allData(){
        Cursor cursor = dbHelper.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

    //menampilkan data yang sedang dipinjam dari database ke dalam listview
    public void dataDiproses(){
        Cursor cursor = dbHelper.dataDiproses();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);

    }

    //menampilkan data yang dikembalikan dari database ke dalam listview
    public void dataSelesai(){
        Cursor cursor = dbHelper.dataSelesai();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this, cursor, 1);
        Is.setAdapter(customCursorAdapter);
    }

    //menampilkan menu tindakan di ActionBar atau Toolbar saat aktivitas dijalankan.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //untuk menanggapi aksi yang diambil oleh pengguna ketika
    // memilih salah satu item dari action menu pada ActionBar atau Toolbar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.sort){
            DialogFragment dialogFragment = new DialogChoise();
            dialogFragment.setCancelable(false);
            dialogFragment.show(getSupportFragmentManager(),"Choice Dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long I) {

        TextView getID = (TextView)view.findViewById(R.id.listAntrian);
        final long id = Long.parseLong(getID.getText().toString());
        Cursor cur = dbHelper.oneData(id);
        cur.moveToFirst();

        Intent idproses = new Intent(MainActivity.this,AddActivity.class);
        idproses.putExtra(DBHelper.row_id,id);
        startActivity(idproses);

    }

    @Override
    //memastikan bahwa tampilan yang ditampilkan telah diperbarui
    protected void onResume() {
        super.onResume();
        setupListView();
    }

    @Override
    public void onPositiveButtonClicked(String[] list, int position) {
        SharedPreferences.Editor editor = viewData.edit();

        if (position == 0){
            editor.putInt("currentListView", 0);
            editor.apply();

            allData();
        }else if (position == 1){
            editor.putInt("currentListView", 1);
            editor.apply();

            dataDiproses();
        }else if (position == 2){
            editor.putInt("currentListView",2);
            editor.apply();

            dataSelesai();

        }

    }

    @Override
    public void onNegativeButtonClicked() {
    }


}

