package com.example.filmeapp;   // pune pachetul proiectului tău

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// BONUS: implements OnFilmListener → Alt+Enter → Implement methods
public class RecyclerActivity extends AppCompatActivity implements FilmRecyclerAdapter.OnFilmListener {

    private final ArrayList<Film> filme = new ArrayList<>();
    private FilmRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // lista vine din MainActivity
        @SuppressWarnings("unchecked")
        ArrayList<Film> primite = (ArrayList<Film>) getIntent().getSerializableExtra("filme");
        if (primite != null) {
            filme.addAll(primite);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));   // FĂRĂ el → ecran gol
        adapter = new FilmRecyclerAdapter(filme, this);                 // "doar view": (filme, null)
        recyclerView.setAdapter(adapter);
    }

    // ===== BONUS: evenimentele venite din adaptor =====
    @Override
    public void onFilmClick(int position) {
        Toast.makeText(this, filme.get(position).getTitlu(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFilmLongClick(int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dlg_titlu)
                .setMessage(filme.get(position).getTitlu())
                .setPositiveButton(R.string.dlg_da, (dialog, which) -> {
                    filme.remove(position);
                    adapter.notifyItemRemoved(position);   // RecyclerView: notificare precisă (cu animație)
                })
                .setNegativeButton(R.string.dlg_nu, null)
                .show();
    }
}
