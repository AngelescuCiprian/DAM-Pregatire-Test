package com.example.bibliotecaapp;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final ArrayList<Carte> toateCartile = new ArrayList<>();   // sursa completă
    private final ArrayList<Carte> carti = new ArrayList<>();          // ce se afișează (în adapter)
    private ArrayAdapter<Carte> adapter;
    private ActivityResultLauncher<Intent> launcher;

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

        // 1. view-uri
        ListView listView = findViewById(R.id.listView);
        Button btnAdauga = findViewById(R.id.btnAdauga);

        // 2. adaptor CU lista + populare
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carti);
        listView.setAdapter(adapter);
        populeaza();

        // 3. un singur launcher: pos == -1 → adăugare, altfel → editare
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Carte c = (Carte) result.getData().getSerializableExtra("carte");
                        int pos = result.getData().getIntExtra("pos", -1);
                        if (c != null) {
                            if (pos == -1) {
                                carti.add(c);
                                toateCartile.add(c);
                            } else {
                                // întâi lista completă (are nevoie de obiectul vechi), apoi cea afișată
                                toateCartile.set(toateCartile.indexOf(carti.get(pos)), c);
                                carti.set(pos, c);
                            }
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "Carte primita: " + c + ", pos=" + pos);
                        }
                    }
                });

        // 4. adăugare → formular gol
        btnAdauga.setOnClickListener(v ->
                launcher.launch(new Intent(this, AddCarteActivity.class)));

        // click = vezi → startActivity (nu aștept nimic înapoi)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, DetaliiCarteActivity.class);
            intent.putExtra("carte", carti.get(position));
            startActivity(intent);
        });

        // long click = modifici → AlertDialog
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Carte c = carti.get(position);
            new AlertDialog.Builder(this)
                    .setTitle(c.getTitlu())
                    .setMessage(R.string.dlg_mesaj)
                    .setPositiveButton(R.string.dlg_sterge, (dialog, which) -> {
                        Carte stearsa = carti.remove(position);
                        toateCartile.remove(stearsa);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, R.string.msg_stearsa, Toast.LENGTH_SHORT).show();
                    })
                    .setNeutralButton(R.string.dlg_editeaza, (dialog, which) -> {
                        Intent intent = new Intent(this, AddCarteActivity.class);
                        intent.putExtra("carte", c);
                        intent.putExtra("pos", position);
                        launcher.launch(intent);
                    })
                    .setNegativeButton(R.string.dlg_anuleaza, null)
                    .show();
            return true;   // consumat → nu se mai declanșează și click-ul simplu
        });
    }

    private void populeaza() {
        // anii în intervalul de validare (1990–2026), altfel editarea nu se poate salva
        toateCartile.add(new Carte("Dune", "SF", 2021));
        toateCartile.add(new Carte("Neuromancer", "SF", 1994));
        toateCartile.add(new Carte("Ion", "Roman", 1999));
        toateCartile.add(new Carte("Luceafarul", "Poezie", 2005));
        toateCartile.add(new Carte("O istorie a lumii", "Istorie", 2012));
        carti.addAll(toateCartile);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_sort_titlu) {
            carti.sort(Comparator.comparing(Carte::getTitlu));
            adapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.nav_sort_an) {
            carti.sort(Comparator.comparingInt(Carte::getAn));
            adapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.nav_filtru_sf) {
            carti.clear();
            for (Carte c : toateCartile) {
                if (c.getGen().equals("SF")) carti.add(c);
            }
            adapter.notifyDataSetChanged();
            return true;
        }
        if (id == R.id.nav_toate) {
            carti.clear();
            carti.addAll(toateCartile);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // exemplificare stare
    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "MainActivity: onPause", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause");
    }
}
