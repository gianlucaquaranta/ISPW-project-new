package com.example.hellofx.bean;

import java.time.Year;

public class LibroBean {
    private String titolo;
    private String autore;
    private String editore;
    private GenereBean genere;
    private String isbn;
    private int annoPubblicazione;
    private Integer[] numCopie; //copie totali, copie non prenotate

    public LibroBean() {
        //factory
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
        isbn = isbn.replaceAll("[\\s,]+", "");
        if((isbn.matches("\\d{13}"))) {
            this.isbn = isbn;
        } else throw new IllegalArgumentException("ISBN non valido");
    }

    public Integer getCopie() {
        return numCopie[0];
    }

    public Integer getCopieDisponibili() {
        return numCopie[1];
    }

    public String getGenereString() {
        return this.genere.getNome();
    }

    public void validate() throws IllegalArgumentException {
        StringBuilder missingFields = new StringBuilder();

        if (titolo == null || titolo.isBlank())
            missingFields.append("- Titolo\n");

        if (autore == null || autore.isBlank())
            missingFields.append("- Autore\n");

        if (isbn == null || isbn.isBlank())
            missingFields.append("- ISBN\n");

        if (editore == null || editore.isBlank())
            missingFields.append("- Editore\n");

        if (genere == null)
            missingFields.append("- Genere\n");

        if (annoPubblicazione <= 0 || annoPubblicazione >= Year.now().getValue())
            missingFields.append("- Anno pubblicazione\n");

        if (numCopie == null || numCopie.length < 2 || numCopie[0] == null || numCopie[0] < 0)
            missingFields.append("- Numero di copie\n");

        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException("I seguenti campi sono mancanti o invalidi:\n" + missingFields);
        }
    }


}