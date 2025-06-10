package com.example.hellofx.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca{
    private String id;
    private String nome;
    private List<Libro> catalogo;
    private Map<String, Integer[]> copie = new HashMap<>(); //Totali, disponibili
    private Posizione posizione;
    private List<Prenotazione> prenotazioniAttive;
    private List<Noleggio> noleggiAttivi;

    public Biblioteca(String nome, List<Libro> catalogo, Posizione posizione) {
        this.nome = nome;
        this.catalogo = catalogo;
        this.posizione = posizione;
        prenotazioniAttive = new ArrayList<>();
        noleggiAttivi = new ArrayList<>();
        this.id = this.nome;
    }

    public Biblioteca() {
        //factory
    }

    public Libro getLibroByIsbn(String isbn) {
        for(Libro l: this.catalogo) {
            if(l.getIsbn().equals(isbn)) {
                return l;
            }
        }
        return null;
    }

    public Map<String, Integer[]> getCopie() {
        return copie;
    }

    public void setCopie(Map<String, Integer[]> copie) {
        this.copie = copie;
    }

    public String getId() {
        return this.id;
    }

    public List<Noleggio> getNoleggiAttivi() {
        return noleggiAttivi;
    }

    public void setNoleggiAttivi(List<Noleggio> noleggiAttivi) {
        this.noleggiAttivi = noleggiAttivi;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Libro> getCatalogo() {
        return this.catalogo;
    }

    public void setCatalogo(List<Libro> catalogo) {this.catalogo = catalogo;}

    public Posizione getPosizione() {
        return this.posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }

    public List<Prenotazione> getPrenotazioniAttive() {
        return this.prenotazioniAttive;
    }

    public void setPrenotazioniAttive(List<Prenotazione> prenotazioniAttive) {
        this.prenotazioniAttive = prenotazioniAttive;
    }

    public void setId(String s){
        this.id = s;
    }

}
