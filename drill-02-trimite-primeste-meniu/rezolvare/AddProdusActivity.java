package com.example.produse;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddProdusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produs);

        EditText etNume = findViewById(R.id.etNume);
        EditText etPret = findViewById(R.id.etPret);
        Button btnSalveaza = findViewById(R.id.btnSalveaza);

        btnSalveaza.setOnClickListener(v -> {
            // drill: fără validări
            String nume = etNume.getText().toString().trim();
            double pret = Double.parseDouble(etPret.getText().toString().trim());
            Produs p = new Produs(nume, pret);

            Intent data = new Intent();
            data.putExtra("produs", p);
            setResult(RESULT_OK, data);
            finish();
        });
    }
}
