package com.example.hellofx.bean;

import com.example.hellofx.converter.Converter;

import java.sql.Timestamp;

public class NoleggioBean {
    private String[] datiUtente; //username, email
    private String idBiblioteca;
    private String isbn;
    private String[] idNoleggio;
    private Timestamp dataInizio;
    private Timestamp dataScadenza;

    public NoleggioBean() {
        //no need to set up attributes
    }

    public String getIdBiblioteca() { return idBiblioteca; }
    public void setIdBiblioteca(String idBiblioteca) { this.idBiblioteca = idBiblioteca; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String[] getIdNoleggio() { return idNoleggio; }
    public void setIdNoleggio(String[] id) { this.idNoleggio = id; }
    public String getDataInizio() { return Converter.timestampToString(this.dataInizio); }
    public void setDataInizio(String date) { this.dataInizio = Converter.stringToTimestamp(date); } //date = "gg/MM/yyyy"
    public String getDataScadenza() { return Converter.timestampToString(this.dataScadenza); }
    public void setDataScadenza(String date){ this.dataScadenza = Converter.stringToTimestamp(date); } //date = "gg/MM/yyy"
    public String[] getDatiUtente() { return datiUtente; }
    public void setDatiUtente(String[] datiUtente) { this.datiUtente = datiUtente; }

}
