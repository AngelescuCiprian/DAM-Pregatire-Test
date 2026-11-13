package com.example.filmeapp;   // pune pachetul proiectului tău

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Film implements Serializable {

    private final String titlu;
    private final String regizor;
    private final int an;

    public Film(String titlu, String regizor, int an) {
        this.titlu = titlu;
        this.regizor = regizor;
        this.an = an;
    }

    public String getTitlu() { return titlu; }
    public String getRegizor() { return regizor; }
    public int getAn() { return an; }

    // folosit DOAR de adaptorul generic (simple_list_item_1)
    @NonNull
    @Override
    public String toString() {
        return titlu + " (" + an + ")";
    }
}
