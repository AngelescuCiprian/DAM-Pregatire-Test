package com.example.produse;   // pune pachetul proiectului tău

import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // 1. CÂMPURI
    private final ArrayList<Produs> produse = new ArrayList<>();
    private ArrayAdapter<Produs> adapter;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 0. blocul generat — codul meu vine DUPĂ el
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView listView = findViewById(R.id.listView);
        Button btnAdauga = findViewById(R.id.btnAdauga);

        // 2. ADAPTOR — CU lista
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produse);
        listView.setAdapter(adapter);

        // 3. ÎNREGISTRARE: OK → scoate → adaugă → notify (cheia "produs" = cea din AddProdusActivity)
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Produs p = (Produs) result.getData().getSerializableExtra("produs");
                        if (p != null) {
                            produse.add(p);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        // 4. LANSARE — la click, nu direct în onCreate
        btnAdauga.setOnClickListener(v ->
                launcher.launch(new Intent(this, AddProdusActivity.class)));

        // detalii → startActivity (nu aștept nimic înapoi)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, DetaliiProdusActivity.class);
            i.putExtra("produs", produse.get(position));
            startActivity(i);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_numar) {
            Toast.makeText(this, "Ai " + produse.size() + " produse", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.nav_total) {
            double total = 0;
            for (Produs p : produse) {
                total += p.getPret();
            }
            Toast.makeText(this, "Total: " + String.format(Locale.US, "%.2f", total) + " lei",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
