package com.example.drill;   // pune pachetul proiectului tău

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetaliiFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_film);

        // datele au venit prin Intent-ul cu care a fost PORNITĂ activitatea
        Film f = (Film) getIntent().getSerializableExtra("film");

        TextView tvDetalii = findViewById(R.id.tvDetalii);
        if (f != null) {
            tvDetalii.setText(f.toString());
        }
    }
}
