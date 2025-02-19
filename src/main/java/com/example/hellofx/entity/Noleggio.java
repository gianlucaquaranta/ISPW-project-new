package com.example.hellofx.entity;

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

    public Timestamp getDataScadenza() {return dataScadenza;}

    public void setDataScadenza(Timestamp dataScadenza) {this.dataScadenza = dataScadenza;}

    public Timestamp getDataInizio() {return dataInizio;}

    public void setDataInizio(Timestamp dataInizio) {this.dataInizio = dataInizio;}

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