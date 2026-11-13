package com.example.filmeapp;   // pune pachetul proiectului tău

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Adaptor PERSONALIZAT pentru ListView: layout propriu pe rând (item_film.xml).
 * "Doar view": construiește rândul și atât — click-urile rămân în activitate.
 */
public class FilmAdapter extends ArrayAdapter<Film> {

    public FilmAdapter(@NonNull Context context, @NonNull List<Film> filme) {
        super(context, 0, filme);          // 0 = nu folosim layout-ul standard, îl facem noi în getView
    }

    // Ctrl+O → getView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // 1. dacă nu am un rând de refolosit, îl creez din XML (inflate)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_film, parent, false);
        }

        // 2. obiectul de pe poziția asta
        Film f = getItem(position);

        // 3. leg datele de view-urile rândului
        TextView tvTitlu = convertView.findViewById(R.id.tvTitlu);
        TextView tvDetalii = convertView.findViewById(R.id.tvDetalii);
        if (f != null) {
            tvTitlu.setText(f.getTitlu());
            tvDetalii.setText(f.getRegizor() + " · " + f.getAn());
        }

        // 4. returnez rândul gata completat
        return convertView;
    }
}
