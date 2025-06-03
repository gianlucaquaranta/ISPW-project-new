package com.example.hellofx.bean;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrenotazioneBean {

    private String id;
    private String dataInizio;
    private String dataScadenza;
    private UtenteBean utente;
    private BibliotecaBean biblioteca;
    private LibroBean libro;


    public PrenotazioneBean(String id, LocalDate dataInizioL, LocalDate dataScadenzaL, UtenteBean utente, BibliotecaBean biblioteca, LibroBean libro) {
        this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataInizio = dataInizioL.format(formatter);
        this.dataScadenza = dataScadenzaL.format(formatter);
        this.utente = utente;
        this.biblioteca = biblioteca;
        this.libro = libro;
    }

    public PrenotazioneBean(String id, Timestamp dataInizioT, Timestamp dataScadenzaT, UtenteBean utente, BibliotecaBean biblioteca, LibroBean libro) {
        this.id = id;
        this.dataInizio = dataInizioT.toString();
        this.dataScadenza = dataScadenzaT.toString();
        this.utente = utente;
        this.biblioteca = biblioteca;
        this.libro = libro;
    }

    public PrenotazioneBean() {
        //converter
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public Timestamp getDataInizioT() {
        return Timestamp.valueOf(dataInizio);
    }

    public Timestamp getDataScadenzaT() {
        return Timestamp.valueOf(dataScadenza);
    }

    public UtenteBean getUtente() {
        return utente;
    }

    public void setUtente(UtenteBean utente) {
        this.utente = utente;
    }

    public BibliotecaBean getBibliotecaB() {
        return biblioteca;
    }

    public void setBiblioteca(BibliotecaBean biblioteca) {
        this.biblioteca = biblioteca;
    }

    public LibroBean getLibro() {
        return libro;
    }

    public void setLibro(LibroBean libro) {
        this.libro = libro;
    }

    public String getTitolo() { return libro.getTitolo(); }

    public String getAutore() { return libro.getAutore(); }

    public String getEditore() { return libro.getEditore(); }

    public String getIsbn() { return libro.getIsbn(); }

    public String getBiblioteca() { return biblioteca.getNome(); }

    public String getIndirizzo() { return biblioteca.getIndirizzoCompleto(); }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}


}
