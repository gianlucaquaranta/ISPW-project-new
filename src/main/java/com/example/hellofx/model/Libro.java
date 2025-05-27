package com.example.hellofx.model;

public class Libro {
    private String titolo;
    private String autore;
    private String editore;
    private String isbn;
    private String genere;
    private Integer annoPubblicazione;

    public Libro(String titolo, String autore, String editore, String isbn, String genere, Integer annoPubblicazione){
        this.titolo = titolo;
        this.autore = autore;
        this.editore = editore;
        this.isbn = isbn;
        this.genere = genere;
        this.annoPubblicazione = annoPubblicazione;
    }

    public Libro(){
        //Factory
    }

    public String getTitolo() {
        return this.titolo;
    }

    public String getAutore() {
        return this.autore;
    }

    public String getEditore() {
        return this.editore;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getGenere() {
        return this.genere;
    }

    public Integer getAnnoPubblicazione() {
        return this.annoPubblicazione;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public void setAnnoPubblicazione(Integer annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }
}
