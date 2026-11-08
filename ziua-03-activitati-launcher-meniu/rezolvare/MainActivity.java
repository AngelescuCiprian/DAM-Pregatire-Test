package com.example.cartiapp;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_CARTI = "carti";

    // final: adaptorul ține referința la ACEST obiect listă → restaurezi cu addAll, nu cu =
    private final ArrayList<Carte> carti = new ArrayList<>();
    private ArrayAdapter<Carte> adapter;
    private ActivityResultLauncher<Intent> addLauncher;

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

        // restaurare după rotire — ÎNAINTE de adaptor
        if (savedInstanceState != null) {
            @SuppressWarnings("unchecked")
            ArrayList<Carte> salvate = (ArrayList<Carte>) savedInstanceState.getSerializable(KEY_CARTI);
            if (salvate != null) {
                carti.addAll(salvate);
            }
        }

        ListView listView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);

        // adaptor generic: afișează toString()
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carti);
        listView.setAdapter(adapter);

        // 1. ÎNREGISTRARE (în onCreate) — callback-ul rulează mai târziu, după finish()
        addLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {   // OK
                        Carte c = (Carte) result.getData().getSerializableExtra("carte");  // scoate
                        if (c != null) {
                            carti.add(c);                                                  // adaugă
                            adapter.notifyDataSetChanged();                                // notify
                            Log.d(TAG, "Carte primita: " + c);
                        }
                    }
                });

        // 2. LANSARE — aștept rezultat → launch()
        fab.setOnClickListener(v ->
                addLauncher.launch(new Intent(this, AddCarteActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_count) {
            Toast.makeText(this, getString(R.string.count_format, carti.size()),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.nav_about) {
            // nu aștept nimic înapoi → startActivity()
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CARTI, carti);
    }
}
