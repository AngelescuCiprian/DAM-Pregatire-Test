package com.example.bibliotecaapp;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddCarteActivity extends AppCompatActivity {

    private EditText etTitlu, etAn;
    private Spinner spGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_carte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTitlu = findViewById(R.id.etTitlu);
        etAn = findViewById(R.id.etAn);
        spGen = findViewById(R.id.spGen);
        Button btnSalveaza = findViewById(R.id.btnSalveaza);

        // precompletare — DOAR la editare (la adăugare nu primim nicio carte → null)
        Carte existenta = (Carte) getIntent().getSerializableExtra("carte");
        if (existenta != null) {
            etTitlu.setText(existenta.getTitlu());
            etAn.setText(String.valueOf(existenta.getAn()));   // setText(int) → crash
            String[] genuri = getResources().getStringArray(R.array.genuri);
            for (int i = 0; i < genuri.length; i++) {
                if (genuri[i].equals(existenta.getGen())) spGen.setSelection(i);
            }
        }

        btnSalveaza.setOnClickListener(v -> salveaza());
    }

    private void salveaza() {
        String titlu = etTitlu.getText().toString().trim();
        String anStr = etAn.getText().toString().trim();

        if (titlu.isEmpty() || anStr.isEmpty()) {
            Toast.makeText(this, R.string.err_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        int an = Integer.parseInt(anStr);
        if (an < 1990 || an > 2026) {
            Toast.makeText(this, R.string.err_an, Toast.LENGTH_SHORT).show();
            return;
        }

        Carte carte = new Carte(titlu, spGen.getSelectedItem().toString(), an);

        Intent data = new Intent();
        data.putExtra("carte", carte);
        data.putExtra("pos", getIntent().getIntExtra("pos", -1));   // -1 la adăugare
        setResult(RESULT_OK, data);
        finish();
    }
}
