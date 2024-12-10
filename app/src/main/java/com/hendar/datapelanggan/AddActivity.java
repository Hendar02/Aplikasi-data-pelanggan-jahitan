package com.abrorrahmad.perpustakaanapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abrorrahmad.perpustakaanapp.adapter.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView TvStatus;
    Button BtnProses;
    EditText TxID, TxNama, TxlebarPundak, TxlingkarBadan , TxPanjangTangan, TxLingkarPinggang, TxLingkarPinggul, TxPanjangBaju, TxModelBaju, TxTglMasuk, TxSelesai, TxStatus;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxID = (EditText)findViewById(R.id.txID);
        TxNama = (EditText)findViewById(R.id.txNama);
        TxlingkarBadan = (EditText)findViewById(R.id.txlingkarBadan);
        TxlebarPundak = (EditText)findViewById(R.id.txLebarPundak);
        TxPanjangTangan = (EditText)findViewById(R.id.txPanjangTangan);
        TxLingkarPinggang = (EditText)findViewById(R.id.txLingkarPinggang);
        TxLingkarPinggul = (EditText)findViewById(R.id.txLingkarPinggul);
        TxPanjangBaju = (EditText)findViewById(R.id.txPanjangBaju);
        TxModelBaju = (EditText)findViewById(R.id.txModelBaju);
        TxTglMasuk = (EditText)findViewById(R.id.txTglmasuk);
        TxSelesai = (EditText)findViewById(R.id.txTglselesai);

        TvStatus = (TextView)findViewById(R.id.tvStatus);
        TxStatus = (EditText)findViewById(R.id.txStatus);
        BtnProses = (Button)findViewById(R.id.btnProses);

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));

        getData();

        TxSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        BtnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesSelesai();
            }
        });

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
    }

    private void prosesSelesai() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setMessage("Proses sudah selesai ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idproses = TxID.getText().toString().trim();
                String Selesai = "Selesai";

                ContentValues values = new ContentValues();

                values.put(DBHelper.row_status, Selesai);
                dbHelper.updateData(values, id);
                Toast.makeText(AddActivity.this, "Proses Sudah Selesai", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog (this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                TxSelesai.setText(dateFormatter.format(newDate.getTime()));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData() {
        Calendar cl = Calendar.getInstance();
        SimpleDateFormat sdfl = new SimpleDateFormat("dd MMMM yyyy",new Locale("id", "ID"));
        String tglMasuk = sdfl.format(cl.getTime());
        TxTglMasuk.setText(tglMasuk);

        Cursor cur = dbHelper.oneData(id);
        if (cur.moveToFirst()){
            String idantrian = cur.getString(cur.getColumnIndex(DBHelper.row_id));
            String nama = cur.getString(cur.getColumnIndex(DBHelper.row_nama));
            String LebarPundak = cur.getString(cur.getColumnIndex(DBHelper.row_lebarPundak));
            String LingkarBadan = cur.getString(cur.getColumnIndex(DBHelper.row_lingkarBadan));
            String PanjangTangan = cur.getString(cur.getColumnIndex(DBHelper.row_panjangTangan));
            String LingkarPinggang = cur.getString(cur.getColumnIndex(DBHelper.row_lingkarPinggang));
            String LingkarPinggul = cur.getString(cur.getColumnIndex(DBHelper.row_lingkarPinggul));
            String PanjangBaju = cur.getString(cur.getColumnIndex(DBHelper.row_panjangBaju));
            String ModelBaju = cur.getString(cur.getColumnIndex(DBHelper.row_modelBaju));
            String TanggalMasuk = cur.getString(cur.getColumnIndex(DBHelper.row_tglMasuk));
            String selesai = cur.getString(cur.getColumnIndex(DBHelper.row_tglSelesai));
            String status = cur.getString(cur.getColumnIndex(DBHelper.row_status));

            TxID.setText(idantrian);
            TxNama.setText(nama);
            TxlebarPundak.setText(LebarPundak);
            TxlingkarBadan.setText(LingkarBadan);
            TxPanjangTangan.setText(PanjangTangan);
            TxLingkarPinggang.setText(LingkarPinggang);
            TxLingkarPinggul.setText(LingkarPinggul);
            TxPanjangBaju.setText(PanjangBaju);
            TxModelBaju.setText(ModelBaju);
            TxTglMasuk.setText(TanggalMasuk);
            TxSelesai.setText(selesai);
            TxStatus.setText(status);

            if (TxID.equals("")){
                TvStatus.setVisibility(View.GONE);
                TxStatus.setVisibility(View.GONE);
                BtnProses.setVisibility(View.GONE);
            }else {
                TvStatus.setVisibility(View.VISIBLE);
                TxStatus.setVisibility(View.VISIBLE);
                BtnProses.setVisibility(View.VISIBLE);
            }

            if (status.equals("Diproses")){
                BtnProses.setVisibility(View.VISIBLE);
            }else {
                BtnProses.setVisibility(View.GONE);
                TxNama.setEnabled(false);
                TxlebarPundak.setEnabled(false);
                TxlingkarBadan.setEnabled(false);
                TxPanjangTangan.setEnabled(false);
                TxLingkarPinggang.setEnabled(false);
                TxLingkarPinggul.setEnabled(false);
                TxPanjangBaju.setEnabled(false);
                TxModelBaju.setEnabled(false);
                TxSelesai.setEnabled(false);
                TxStatus.setEnabled(false);
            }

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        String idproses = TxID.getText().toString().trim();
        String status = TxStatus.getText().toString().trim();

        MenuItem itemDelete = menu.findItem(R.id.action_delete);
        MenuItem itemClear = menu.findItem(R.id.action_clear);
        MenuItem itemSave = menu.findItem(R.id.action_save);


        if (idproses.equals("")){
            itemDelete.setVisible(false);
            itemClear.setVisible(true);
        }else {
            itemDelete.setVisible(false);
            itemClear.setVisible(false);
            itemSave.setVisible(false);
        }

        if (status.equals("Selesai")){
            itemSave.setVisible(false);
            itemDelete.setVisible(false);
            itemClear.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save:
                insertAndUpdate();
        }
        switch (item.getItemId()){
            case R.id.action_clear:
                TxNama.setText("");
                TxlebarPundak.setText("");
                TxSelesai.setText("");
        }

        switch (item.getItemId()){
            case R.id.action_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setMessage("Data ini akan dihapus");
                builder.setCancelable(true);
                builder.setPositiveButton("hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteData(id);
                        Toast.makeText(AddActivity.this,"Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertAndUpdate(){
        String idAntrian = TxID.getText().toString().trim();
        String nama = TxNama.getText().toString().trim();
        String LebarPundak = TxlebarPundak.getText().toString().trim();
        String tglMasuk = TxTglMasuk.getText().toString().trim();
        String tglSelesai = TxSelesai.getText().toString().trim();
        String lingkarBadan = TxlingkarBadan.getText().toString().trim();
        String panjangTangan = TxPanjangTangan.getText().toString().trim();
        String lingkarPinggang = TxLingkarPinggang.getText().toString().trim();
        String lingkarPinggul = TxLingkarPinggul.getText().toString().trim();
        String panjangBaju = TxPanjangBaju.getText().toString().trim();
        String modelBaju = TxModelBaju.getText().toString().trim();

        String status = "Diproses";

        ContentValues values = new ContentValues();

        values.put(DBHelper.row_nama, nama);
        values.put(DBHelper.row_lingkarBadan, lingkarBadan);
        values.put(DBHelper.row_lebarPundak, LebarPundak);
        values.put(DBHelper.row_modelBaju, modelBaju);
        values.put(DBHelper.row_panjangTangan, panjangTangan);
        values.put(DBHelper.row_lingkarPinggang, lingkarPinggang);
        values.put(DBHelper.row_lingkarPinggul, lingkarPinggul);
        values.put(DBHelper.row_panjangBaju, panjangBaju);
        values.put(DBHelper.row_status, status);
        values.put(DBHelper.row_tglMasuk, tglMasuk);
        values.put(DBHelper.row_tglSelesai, tglSelesai);

        if (nama.equals("") || LebarPundak.equals("") || tglSelesai.equals("")){
            Toast.makeText(AddActivity.this, "Isi data dengan Lengkap", Toast.LENGTH_SHORT).show();
        }else {
            if (idAntrian.equals("")){
                values.put(DBHelper.row_tglMasuk, tglMasuk);
                dbHelper.insertData(values);
            }else {
                dbHelper.updateData(values, id);
            }

            Toast.makeText(AddActivity.this,"Data Tersimpan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}