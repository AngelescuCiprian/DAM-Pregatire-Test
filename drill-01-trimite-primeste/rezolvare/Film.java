package com.example.drill;   // pune pachetul proiectului tău

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Film implements Serializable {

    private final String titlu;
    private final int an;

    public Film(String titlu, int an) {
        this.titlu = titlu;
        this.an = an;
    }

    @NonNull
    @Override
    public String toString() {
        return titlu + " (" + an + ")";
    }
}
