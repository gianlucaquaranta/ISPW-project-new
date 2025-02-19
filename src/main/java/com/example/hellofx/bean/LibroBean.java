package com.example.hellofx.bean;

public class LibroBean {
    private String titolo;
    private String autore;
    private String editore;
    private GenereBean genere;
    private String isbn;
    private int annoPubblicazione;
    private Integer[] numCopie; //copie totali, copie non prenotate
    private String imageUrl;

    public LibroBean() {}

    public LibroBean(String titolo, String autore, String editore, GenereBean genere, String isbn, Integer[] numCopie, String imageUrl) {
        this.titolo = titolo;
        this.autore = autore;
        this.editore = editore;
        this.genere = genere;
        this.isbn = isbn;
        this.numCopie = numCopie;
        this.imageUrl = imageUrl;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
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

    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public GenereBean getGenere() {
        return genere;
    }

    public void setGenere(GenereBean genere) {
        this.genere = genere;
    }

    public Integer[] getNumCopie() {
        return numCopie;
    }

    public void setNumCopie(Integer[] numCopie) {
        this.numCopie = numCopie;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
