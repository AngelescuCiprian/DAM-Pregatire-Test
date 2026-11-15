package ro.ase.simularetestdam_1;

import java.io.Serializable;

public class Laptop implements Serializable
{
    private String model;
    private double pret;
    private String tip;
    private boolean garantieExtinsa;

    public Laptop(String model, double pret, String tip, boolean garantieExtinsa) {
        this.model = model;
        this.tip = tip;
        this.pret = pret;
        this.garantieExtinsa = garantieExtinsa;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isGarantieExtinsa() {
        return garantieExtinsa;
    }

    public void setGarantieExtinsa(boolean garantieExtinsa) {
        this.garantieExtinsa = garantieExtinsa;
    }

    @Override
    public String toString() {
        return "Laptop " +
                "model '" + model + '\'' +
                ", pret " + pret +
                ", tip '" + tip + '\'' +
                ", garantieExtinsa " + garantieExtinsa +
                '}';
    }
}
