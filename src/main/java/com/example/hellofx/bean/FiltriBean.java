package com.example.hellofx.bean;

public class FiltriBean {
    private String titolo;
    private String autore;
    private String genere;
    private String isbn;
    private String biblioteca;
    private String cap;
    private String raggio;

    public FiltriBean(String titolo, String autore, String genere, String isbn, String biblioteca, String cap, String raggio) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.isbn = isbn;
        this.biblioteca = biblioteca;
        this.cap = cap;
        this.raggio = raggio;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getRaggio() {
        return raggio;
    }

    public void setRaggio(String raggio) {
        this.raggio = raggio;
    }

    public String getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(String biblioteca) {
        this.biblioteca = biblioteca;
    }
}
