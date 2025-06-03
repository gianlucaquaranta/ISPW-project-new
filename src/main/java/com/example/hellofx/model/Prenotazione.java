package com.example.hellofx.model;

import com.example.hellofx.converter.Converter;

import java.sql.Timestamp;

public class Prenotazione {

    private Timestamp dataInizio;
    private Timestamp dataScadenza;
    private String[] datiUtente; //username, email
    private String idBiblioteca;
    private String isbn;
    private String[] idPrenotazione; //username, idBiblioteca, isbn

    public Prenotazione(Timestamp dataInizio, Timestamp dataScadenza, String[] datiUtente, String idBiblioteca, String isbn){
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
        this.datiUtente = datiUtente;
        this.idBiblioteca = idBiblioteca;
        this.isbn = isbn;
    }

    public Prenotazione(){}

    public String[] getIdPrenotazione() {
        return this.idPrenotazione;
    }

    public void setIdPrenotazione() {
        this.idPrenotazione[0] = this.datiUtente[0];
        this.idPrenotazione[1] = this.idBiblioteca;
        this.idPrenotazione[2] = this.isbn;
    }

    public void setIdPrenotazione(String[] idPrenotazione) { this.idPrenotazione = idPrenotazione; }


    public Timestamp getDataInizio() { return this.dataInizio; }
    public void setDataInizio(Timestamp date) { this.dataInizio = date;}
    public Timestamp getDataScadenza() { return this.dataScadenza; }
    public void setDataScadenza(Timestamp date) { this.dataScadenza = date; }

    public String getIdBiblioteca() {
        return this.idBiblioteca;
    }

    public void setIdBiblioteca(String idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String[] getDatiUtente() {
        return datiUtente;
    }

    public void setDatiUtente(String[] datiUtente) {
        this.datiUtente = datiUtente;
    }
}
