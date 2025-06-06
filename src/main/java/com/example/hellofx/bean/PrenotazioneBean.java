package com.example.hellofx.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrenotazioneBean {

    private String id;
    private String dataInizio;
    private String dataScadenza;
    private UtenteBean utente;
    private BibliotecaBean biblioteca;
    private LibroBean libro;


    public PrenotazioneBean(String id, LocalDateTime dataInizioL, LocalDateTime dataScadenzaL, UtenteBean utente, BibliotecaBean biblioteca, LibroBean libro) {
        this.id = id;
        this.dataInizio = formatInputDate(dataInizioL);
        this.dataScadenza = formatInputDate(dataScadenzaL);
        this.utente = utente;
        this.biblioteca = biblioteca;
        this.libro = libro;
    }

    public PrenotazioneBean(String id, Timestamp dataInizioT, Timestamp dataScadenzaT, UtenteBean utente, BibliotecaBean biblioteca, LibroBean libro) {
        this.id = id;
        this.dataInizio = formatInputDate(dataInizioT.toLocalDateTime());
        this.dataScadenza = formatInputDate(dataScadenzaT.toLocalDateTime());
        this.utente = utente;
        this.biblioteca = biblioteca;
        this.libro = libro;
    }

    public PrenotazioneBean() {
        //converter
    }

    public String getDataInizio() {
        return formatDateString(dataInizio);
    }

    public String getDataScadenza() {
        return formatDateString(dataScadenza);
    }

    private String formatDateString(String dateTimeString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime date = LocalDateTime.parse(dateTimeString, inputFormatter);
        return date.format(outputFormatter);
    }

    private String formatInputDate(LocalDateTime date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(inputFormatter);
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
