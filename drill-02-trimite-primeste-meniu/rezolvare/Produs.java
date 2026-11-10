package com.example.produse;   // pune pachetul proiectului tău

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

public class Produs implements Serializable {

    private final String nume;
    private final double pret;

    public Produs(String nume, double pret) {
        this.nume = nume;
        this.pret = pret;
    }

    public String getNume() { return nume; }
    public double getPret() { return pret; }

    @NonNull
    @Override
    public String toString() {
        return nume + " - " + String.format(Locale.US, "%.2f", pret) + " lei";
    }
}
