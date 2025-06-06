package com.example.hellofx.model;


public class Posizione {

    private String cap;
    private String indirizzo;
    private String numeroCivico;
    private String citta;
    private String provincia;

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

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
