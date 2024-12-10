package com.abrorrahmad.perpustakaanapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;


public class DataSelesaiActivity extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_selesai);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Mengatur teks pada ActionBar (Toolbar)
            actionBar.setTitle("Teks Baru pada Toolbar");
        }
    }
}

