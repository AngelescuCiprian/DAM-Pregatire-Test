package com.example.filmeapp;   // pune pachetul proiectului tău

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adaptor PERSONALIZAT pentru RecyclerView = 3 metode obligatorii + ViewHolder.
 * Partea 1 ("doar view"): fără listener.  Partea 2 (bonus): interfața OnFilmListener pentru click-uri.
 */
public class FilmRecyclerAdapter extends RecyclerView.Adapter<FilmRecyclerAdapter.FilmViewHolder> {

    // ===== BONUS: click-urile sunt "trimise" activității printr-o interfață =====
    public interface OnFilmListener {
        void onFilmClick(int position);
        void onFilmLongClick(int position);
    }

    private final List<Film> filme;
    private final OnFilmListener listener;   // poate fi null dacă vrei "doar view"

    public FilmRecyclerAdapter(List<Film> filme, OnFilmListener listener) {
        this.filme = filme;
        this.listener = listener;
    }

    // 1. CREEAZĂ un rând nou (se apelează de puține ori — doar cât încap pe ecran + câteva)
    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(view);
    }

    // 2. UMPLE un rând (refolosit) cu datele de pe poziția dată (se apelează la fiecare scroll)
    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        Film f = filme.get(position);
        holder.tvTitlu.setText(f.getTitlu());
        holder.tvDetalii.setText(f.getRegizor() + " · " + f.getAn());

        // BONUS: click-uri. Poziția curentă se ia din holder (după ștergeri, "position" poate fi învechit)
        if (listener != null) {
            holder.itemView.setOnClickListener(v ->
                    listener.onFilmClick(holder.getBindingAdapterPosition()));
            holder.itemView.setOnLongClickListener(v -> {
                listener.onFilmLongClick(holder.getBindingAdapterPosition());
                return true;
            });
        }
    }

    // 3. CÂTE rânduri sunt
    @Override
    public int getItemCount() {
        return filme.size();
    }

    // ViewHolder = "cutia" care ține referințele la view-urile unui rând (findViewById o singură dată)
    static class FilmViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitlu;
        final TextView tvDetalii;

        FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitlu = itemView.findViewById(R.id.tvTitlu);
            tvDetalii = itemView.findViewById(R.id.tvDetalii);
        }
    }
}
