package com.example.hellofx.bean;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrenotazioneBean {
    private String dataInizio;
    private String dataScadenza;
    private UtenteBean utente;
    private BibliotecaBean biblioteca;
    private LibroBean libro;

    public PrenotazioneBean(LocalDate dataInizioL, LocalDate dataScadenzaL, UtenteBean utente, BibliotecaBean biblioteca, LibroBean libro) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dataInizio = dataInizioL.format(formatter);;
        this.dataScadenza = dataScadenzaL.format(formatter);
        this.utente = utente;
        this.biblioteca = biblioteca;
        this.libro = libro;
    }

    public PrenotazioneBean(Timestamp dataInizioT, Timestamp dataScadenzaT, UtenteBean utente, BibliotecaBean biblioteca, LibroBean libro) {
        this.dataInizio = dataInizioT.toString();
        this.dataScadenza = dataScadenzaT.toString();
        this.utente = utente;
        this.biblioteca = biblioteca;
        this.libro = libro;
    }

    public PrenotazioneBean() {
        //converter
    }

    public String getDataInizioS() {
        return dataInizio;
    }

    public String getDataScadenzaS() {
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

    public BibliotecaBean getBiblioteca() {
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
}
