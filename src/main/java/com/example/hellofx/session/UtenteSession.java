package com.example.hellofx.session;

import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Bibliotecario;
import com.example.hellofx.model.Utente;

public class UtenteSession extends Session {
    private Utente utente;

    private static UtenteSession instance = null;

    public static UtenteSession getInstance() {
        if (instance == null) {
            instance = new UtenteSession();
        }
        return instance;
    }

    private UtenteSession() { }

    public Utente getUtente() { return utente; }

    @Override
    public Biblioteca getBiblioteca() {
        return null;
    }

    public void setUtente(Utente utente) { this.utente = utente; }

    @Override
    public void close(){
        utente = null;
    }

}
