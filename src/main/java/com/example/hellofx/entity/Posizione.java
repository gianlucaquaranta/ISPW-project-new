package com.example.hellofx.entity;

public class Posizione {
    String cap;
    String indirizzo;
    String numeroCivico;
    String citta;
    String provincia;
    float[] coordinate;

    public Posizione (String cap, String indirizzo, String numeroCivico, String citta, String provincia) {
        this.cap = cap;
        this.indirizzo = indirizzo;
        this.numeroCivico = numeroCivico;
        this.citta = citta;
        this.provincia = provincia;
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

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public float[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(float[] coordinate) {
        this.coordinate = coordinate;
    }

}
