package com.example.hellofx.bean;

public class FiltriBean {
    private String titolo;
    private String autore;
    private String genere;
    private String isbn;
    private String biblioteca;
    private String cap;

    public FiltriBean() {}
    public FiltriBean(String titolo, String autore, String genere, String isbn, String biblioteca, String cap) {
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        String cleanedIsbn;
        cleanedIsbn = validaIsbn(isbn);
        this.isbn = cleanedIsbn;
        this.biblioteca = biblioteca;
        if(!validaCap(cap)) throw new IllegalArgumentException("Formato CAP non valido");
        this.cap = cap;
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

    public String getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(String biblioteca) {
        this.biblioteca = biblioteca;
    }

    public String validaIsbn(String isbn) {
        if (isbn.isBlank()) {
            return isbn;
        }

        // Rimuove spazi e trattini
        String cleanedIsbn = isbn.replaceAll("[\\s-]", "");

        if(!cleanedIsbn.matches("\\d{13}")){
            throw new IllegalArgumentException("Formato ISBN non valido");
        }

        return cleanedIsbn;
    }

    public boolean validaCap(String cap) {
        return(cap.matches("\\d{5}") || cap.isBlank());
    }
}
