package com.example.hellofx.entity;

public class Posizione {
    String cap;
    String indirizzo;
    int numeroCivico;
    float[] coordinate;


    private Posizione() {}

    public Posizione (String cap, String indirizzo, int numeroCivico, float[] coordinate) {
        this.cap = cap;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.coordinate = coordinate; //se il numero civico e l'indirizzo non ci sono: cercare le coordinate del cap? prendendo il centro del paese con quel cap
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(int numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public float[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(float[] coordinate) {
        this.coordinate = coordinate;
    }

}
