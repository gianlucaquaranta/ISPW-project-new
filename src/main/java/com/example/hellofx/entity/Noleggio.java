package com.example.hellofx.entity;

import com.example.hellofx.converter.Converter;

import java.sql.Timestamp;

public class Noleggio {
    private String isbn;
    private Timestamp dataInizio;
    private Timestamp dataScadenza;
    private String[] datiUtente; //username, email
    private String idBiblioteca;
    private String[] idNoleggio = {this.datiUtente[0], this.idBiblioteca, this.isbn}; //username, idBiblioteca, isbn

    public Noleggio() {
        //factory
    }

    public Noleggio(Timestamp dataInizio, Timestamp dataScadenza, String[] datiUtente, String idBiblioteca, String isbn){
        this.dataInizio = dataInizio;
        this.dataScadenza = dataScadenza;
        this.datiUtente = datiUtente;
        this.idBiblioteca = idBiblioteca;
        this.isbn = isbn;
    }

    public String[] getIdNoleggio() {return idNoleggio;}

    public String getDataInizio() { return Converter.timestampToString(this.dataInizio); }
    public void setDataInizio(String date) { this.dataInizio = Converter.stringToTimestamp(date); } //date = "gg/MM/yyyy"
    public String getDataScadenza() { return Converter.timestampToString(this.dataScadenza); }
    public void setDataScadenza(String date){ this.dataScadenza = Converter.stringToTimestamp(date); }

    public String[] getDatiUtente() {return datiUtente;}

    public String getIdBiblioteca() {return idBiblioteca;}

    public void setIdBiblioteca(String idBiblioteca) {this.idBiblioteca = idBiblioteca;}

    public String getIsbn() {return this.isbn;}

    public void setIsbn(String isbn) {this.isbn = isbn;}

    public void setDatiUtente(String[] datiUtente) {
        this.datiUtente = datiUtente;
    }

    public void setIdNoleggio() {
        this.idNoleggio[0] = this.datiUtente[0];
        this.idNoleggio[1] = this.idBiblioteca;
        this.idNoleggio[2] = this.isbn;
    }
}