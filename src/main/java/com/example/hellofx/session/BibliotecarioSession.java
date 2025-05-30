package com.example.hellofx.session;

import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Bibliotecario;
import com.example.hellofx.model.Utente;

public class BibliotecarioSession extends Session {
    private Biblioteca biblioteca;
    private static BibliotecarioSession instance = null;

    public static BibliotecarioSession getInstance() {
        if (instance == null) {
            instance = new BibliotecarioSession();
        }
        return instance;
    }

    private BibliotecarioSession() { }
    public Biblioteca getBiblioteca() { return biblioteca; }

    public void setBiblioteca(Biblioteca biblioteca) { this.biblioteca = biblioteca; }

    @Override
    public void setUtente(Utente utente) {
        //no override needed
    }

    @Override
    public Utente getUtente() {
        return null;
    }

    @Override
    public void close(){
        biblioteca = null;
    }

}
