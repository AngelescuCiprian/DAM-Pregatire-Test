package com.example.filmeapp;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<Film> filme = new ArrayList<>();
    private FilmAdapter adapter;                 // pas 1 (generic): ArrayAdapter<Film>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = findViewById(R.id.listView);
        Button btnRecycler = findViewById(R.id.btnRecycler);

        // PAS 1 — adaptor GENERIC (afișează toString()):
        // ArrayAdapter<Film> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filme);

        // PAS 2 — adaptor PERSONALIZAT (layout propriu item_film.xml):
        adapter = new FilmAdapter(this, filme);
        listView.setAdapter(adapter);
        populeaza();

        // evenimentele rămân în ACTIVITATE — adaptorul e "doar view"
        listView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, filme.get(position).getTitlu(), Toast.LENGTH_SHORT).show());

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.dlg_titlu)
                    .setMessage(filme.get(position).getTitlu())
                    .setPositiveButton(R.string.dlg_da, (dialog, which) -> {
                        filme.remove(position);
                        adapter.notifyDataSetChanged();   // ListView: mereu notifyDataSetChanged
                    })
                    .setNegativeButton(R.string.dlg_nu, null)
                    .show();
            return true;
        });

        // aceleași date, afișate în RecyclerView → altă activitate (startActivity + lista în Intent)
        btnRecycler.setOnClickListener(v -> {
            Intent i = new Intent(this, RecyclerActivity.class);
            i.putExtra("filme", filme);          // ArrayList<Film> e Serializable
            startActivity(i);
        });
    }

    private void populeaza() {
        filme.add(new Film("Inception", "Christopher Nolan", 2010));
        filme.add(new Film("Interstellar", "Christopher Nolan", 2014));
        filme.add(new Film("Dune", "Denis Villeneuve", 2021));
        filme.add(new Film("Parasite", "Bong Joon-ho", 2019));
        filme.add(new Film("Whiplash", "Damien Chazelle", 2014));
        filme.add(new Film("The Matrix", "Wachowski", 1999));
        filme.add(new Film("Arrival", "Denis Villeneuve", 2016));
        filme.add(new Film("Joker", "Todd Phillips", 2019));
        adapter.notifyDataSetChanged();
    }
}
