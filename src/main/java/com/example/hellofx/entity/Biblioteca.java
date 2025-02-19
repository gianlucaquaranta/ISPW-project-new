package com.example.hellofx.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Biblioteca {
    private String id;
    private String nome;
    private List<Libro> catalogo;
    private Map<String, Integer[]> copie = new HashMap<>();
    private Posizione posizione;
    private String url;
    private List<Prenotazione> prenotazioniAttive;
    private List<Noleggio> noleggiAttivi;
    private List<Bibliotecario> bibliotecari;

    public Biblioteca(String nome, List<Libro> catalogo, Posizione posizione, String url) {
        this.nome = nome;
        this.catalogo = catalogo;
        this.posizione = posizione;
        this.url = url;
        prenotazioniAttive = new ArrayList<>();
        noleggiAttivi = new ArrayList<>();
        bibliotecari = new ArrayList<>();
        this.id = this.nome.replace(" ", "")+this.posizione.getIndirizzo().replace(" ", "")+ this.posizione.getCap();
    }

    public Biblioteca() {
        //factory
    }

    public boolean isLibroInCatalogo(String isbn) {
        for(Libro l: this.catalogo) {
            if(l.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, Integer[]> getCopie() {
        return copie;
    }

    public void setCopie(Map<String, Integer[]> copie) {
        this.copie = copie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<Bibliotecario> getBibliotecari() {
        return bibliotecari;
    }

    public void setBibliotecari(List<Bibliotecario> bibliotecari) {
        this.bibliotecari = bibliotecari;
    }

    public void setId(){
        this.id = this.nome.replace(" ", "")+this.posizione.getIndirizzo().replace(" ", "")+ this.posizione.getCap();
    }

    public void setId(String s){
        this.id = s;
    }
}
