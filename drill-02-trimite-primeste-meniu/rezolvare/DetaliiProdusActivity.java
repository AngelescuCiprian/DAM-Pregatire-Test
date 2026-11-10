package com.example.produse;   // pune pachetul proiectului tău

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DetaliiProdusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_produs);

        Produs p = (Produs) getIntent().getSerializableExtra("produs");
        if (p == null) { finish(); return; }

        TextView tvNume = findViewById(R.id.tvNume);
        TextView tvPret = findViewById(R.id.tvPret);
        tvNume.setText(p.getNume());
        tvPret.setText(String.format(Locale.US, "%.2f lei", p.getPret()));
    }
}
