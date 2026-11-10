package com.example.bibliotecaapp;   // pune pachetul proiectului tău

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetaliiCarteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalii_carte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // datele vin prin Intent-ul cu care a fost PORNITĂ activitatea
        Carte c = (Carte) getIntent().getSerializableExtra("carte");
        if (c == null) {
            finish();
            return;
        }

        TextView tvTitlu = findViewById(R.id.tvTitlu);
        TextView tvGen = findViewById(R.id.tvGen);
        TextView tvAn = findViewById(R.id.tvAn);
        Button btnCauta = findViewById(R.id.btnCauta);
        Button btnDistribuie = findViewById(R.id.btnDistribuie);

        tvTitlu.setText(c.getTitlu());
        tvGen.setText(c.getGen());
        tvAn.setText(String.valueOf(c.getAn()));

        // intent implicit: ACTION_VIEW → browser
        btnCauta.setOnClickListener(v -> {
            String url = "https://www.google.com/search?q=" + Uri.encode(c.getTitlu());
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });

        // intent implicit: ACTION_SEND → aplicații de share
        btnDistribuie.setOnClickListener(v -> {
            Intent send = new Intent(Intent.ACTION_SEND);
            send.setType("text/plain");
            send.putExtra(Intent.EXTRA_TEXT, c.toString());
            startActivity(Intent.createChooser(send, getString(R.string.share_title)));
        });
    }
}
