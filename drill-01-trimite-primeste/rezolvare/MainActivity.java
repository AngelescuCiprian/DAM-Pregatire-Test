package com.example.drill;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 1. CÂMPURI
    private final ArrayList<Film> lista = new ArrayList<>();
    private ArrayAdapter<Film> adapter;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdauga = findViewById(R.id.btnAdauga);
        ListView listView = findViewById(R.id.listView);

        // 2. ADAPTOR
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adapter);

        // 3. ÎNREGISTRARE: OK → scoate → adaugă → notify
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Film f = (Film) result.getData().getSerializableExtra("film");
                        lista.add(f);
                        adapter.notifyDataSetChanged();
                    }
                });

        // 4. LANSARE (aștept rezultat)
        btnAdauga.setOnClickListener(v ->
                launcher.launch(new Intent(this, AddFilmActivity.class)));

        // PARTEA 2: startActivity — trimit, NU aștept nimic înapoi
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, DetaliiFilmActivity.class);
            i.putExtra("film", lista.get(position));
            startActivity(i);
        });
    }
}
