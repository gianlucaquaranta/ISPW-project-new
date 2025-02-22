package com.example.hellofx.bean;

import com.example.hellofx.converter.Converter;

import java.sql.Timestamp;

public class PrenotazioneBean {
    private Timestamp dataInizio;
    private Timestamp dataScadenza;
    private String[] datiUtente; //username, email
    private String idBiblioteca;
    private String isbn;
    private String[] idPrenotazione = {this.datiUtente[0], this.idBiblioteca, this.isbn}; //username, idBiblioteca, isbn

    public PrenotazioneBean(){}

    public String[] getIdPrenotazione() {
        return this.idPrenotazione;
    }

    public void setIdPrenotazione() {
        this.idPrenotazione[0] = this.datiUtente[0];
        this.idPrenotazione[1] = this.idBiblioteca;
        this.idPrenotazione[2] = this.isbn;
    }

    public String getDataInizio() { return Converter.timestampToString(this.dataInizio); }
    public void setDataInizio(String date) { this.dataInizio = Converter.stringToTimestamp(date); } //date = "gg/MM/yyyy"
    public String getDataScadenza() { return Converter.timestampToString(this.dataScadenza); }
    public void setDataScadenza() {
        this.dataScadenza = Timestamp.valueOf(this.dataInizio.toLocalDateTime().plusDays(15));
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
