package com.example.hellofx.entity;

import java.util.ArrayList;
import java.util.List;

public class Utente {
    private String username;
    private String password;
    private String email;
    private List<Noleggio> noleggiAttivi;
    private List<Prenotazione> prenotazioniAttive;
    private List<Filtri> ricercheSalvate;

    public Utente(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.prenotazioniAttive = new ArrayList<>();
        this.ricercheSalvate = new ArrayList<>();
    }

    public Utente() {
        this.username = null;
        this.password = null;
        this.email = null;
        this.prenotazioniAttive = new ArrayList<>();
        this.ricercheSalvate = new ArrayList<>();
        this.noleggiAttivi = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Noleggio> getNoleggiAttivi() {
        return noleggiAttivi;
    }

    public void setNoleggiAttivi(List<Noleggio> noleggiAttivi) {
        this.noleggiAttivi = noleggiAttivi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Prenotazione> getPrenotazioniAttive() {
        return this.prenotazioniAttive;
    }

    public void setPrenotazioniAttive(List<Prenotazione> prenotazioniAttive) {
        this.prenotazioniAttive = prenotazioniAttive;
    }

    public List<Filtri> getRicercheSalvate() {
        return this.ricercheSalvate;
    }

    public void setRicercheSalvate(List<Filtri> ricercheSalvate) {
        this.ricercheSalvate = ricercheSalvate;
    }

}
