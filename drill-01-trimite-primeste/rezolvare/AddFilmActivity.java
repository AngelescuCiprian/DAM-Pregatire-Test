package com.example.drill;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        EditText etTitlu = findViewById(R.id.etTitlu);
        EditText etAn = findViewById(R.id.etAn);
        Button btnTrimite = findViewById(R.id.btnTrimite);

        btnTrimite.setOnClickListener(v -> {
            // fără validări (doar drill) — la test: gol → parse → interval
            Film film = new Film(
                    etTitlu.getText().toString(),
                    Integer.parseInt(etAn.getText().toString()));

            Intent data = new Intent();
            data.putExtra("film", film);
            setResult(RESULT_OK, data);
            finish();
        });
    }
}
