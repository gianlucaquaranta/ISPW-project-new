package com.example.hellofx.entity;

import java.sql.Timestamp;

public class Prenotazione {

    private Timestamp dataInizio;
    private Timestamp dataScadenza;
    private String[] datiUtente; //username, email
    private String idBiblioteca;
    private String isbn;
    private String[] idPrenotazione = {this.datiUtente[0], this.idBiblioteca, this.isbn}; //username, idBiblioteca, isbn

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

    public Timestamp getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Timestamp dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Timestamp getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Timestamp dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

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
