package com.example.hellofx.entity;

public class Filtri {
    private final String titolo; //filtri per libri
    private final String autore;
    private final String genere;
    private final String isbn;
    private final String biblioteca; //filtri per biblioteche
    private final String cap;
    private final String raggio;

    public Filtri(String titolo, String autore, String genere, String biblioteca, String raggio, String isbn, String cap) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.biblioteca = biblioteca;
        this.raggio = raggio;
        this.isbn = isbn;
        this.cap = cap;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public String getAutore() {
        return this.autore;
    }

    public String getGenere() {
        return this.genere;
    }

    public String getCap() {
        return this.cap;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getRaggio() { return raggio; }

    public String getBiblioteca() {
        return biblioteca;
    }
}
