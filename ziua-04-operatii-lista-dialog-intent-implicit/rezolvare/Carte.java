package com.example.bibliotecaapp;   // pune pachetul proiectului tău

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Carte implements Serializable {

    private final String titlu;
    private final String gen;
    private final int an;

    // Alt+Insert → Constructor
    public Carte(String titlu, String gen, int an) {
        this.titlu = titlu;
        this.gen = gen;
        this.an = an;
    }

    // Alt+Insert → Getter
    public String getTitlu() {
        return titlu;
    }

    public String getGen() {
        return gen;
    }

    public int getAn() {
        return an;
    }

    // Alt+Insert → toString()  — asta afișează ArrayAdapter-ul generic
    @NonNull
    @Override
    public String toString() {
        return titlu + " · " + gen + ", " + an;
    }
}
