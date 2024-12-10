package com.abrorrahmad.perpustakaanapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.abrorrahmad.perpustakaanapp.adapter.DBHelper;

public class DashboardActivity extends AppCompatActivity {


    DBHelper dbHelper;

    public static DashboardActivity ma;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        TextView totalPelanggan = findViewById(R.id.txTotalPelanggan);
        dbHelper = new DBHelper(this);

        ma = this;

        int rowCount = dbHelper.getRowCount();
        String rowCountText = "" + rowCount;
        totalPelanggan.setText(rowCountText);

        updateJumlahDataDiproses();
        updateJumlahDataSelesai();

        CardView exitCardView = findViewById(R.id.exitCardView);
        exitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmationDialog();
            }

            private void showExitConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Apakah Anda yakin ingin keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Jika pengguna memilih 'Ya', keluar dari aplikasi
                                exitOnClick();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Jika pengguna memilih 'Tidak', tutup dialog dan lanjutkan aplikasi
                                dialog.dismiss();
                            }
                        });
                // Membuat dan menampilkan dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            private void exitOnClick() {
                // Logika keluar dari aplikasi
                finish(); // atau System.exit(0);
            }
        });
    }

    public void exitOnClick(View view) {
        // Menutup activity
        finish();
    }



    private void updateJumlahDataDiproses() {
        // Menggunakan fungsi jumlahDataDiproses() yang telah dijelaskan sebelumnya
        int jumlahDiproses = dbHelper.jumlahDataDiproses();

        // Mendapatkan referensi TextView dari layout
        TextView JumlahDataDiproses = findViewById(R.id.txJumlahDiproses);

        // Menetapkan teks TextView dengan jumlah data yang diproses
        JumlahDataDiproses.setText("" + jumlahDiproses);
    }

    private void updateJumlahDataSelesai() {
        // Menggunakan fungsi jumlahDataDiproses() yang telah dijelaskan sebelumnya
        int jumlahSelesai = dbHelper.jumlahDataSelesai();

        // Mendapatkan referensi TextView dari layout
        TextView JumlahDataSelesai = findViewById(R.id.txJumlaSelesai);

        // Menetapkan teks TextView dengan jumlah data yang diproses
        JumlahDataSelesai.setText("" + jumlahSelesai);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    public void DataAksi2(View view) {
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void DataAksi(View view) {
        Intent intent = new Intent(DashboardActivity.this, AddActivity.class);
        startActivity(intent);
    }

    public void DataAksi1(View view) {
        Intent intent = new Intent(DashboardActivity.this, DataPelangganActivity.class);
        startActivity(intent);
    }

    public void Refreshlist() {
    }
}