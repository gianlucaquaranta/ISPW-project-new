package com.example.hellofx.session;

import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Bibliotecario;
import com.example.hellofx.entity.Utente;

public class BibliotecarioSession extends Session {
    private Bibliotecario bibliotecario;
    private Biblioteca biblioteca;
    private static BibliotecarioSession instance = null;

    public static BibliotecarioSession getInstance() {
        if (instance == null) {
            instance = new BibliotecarioSession();
        }
        return instance;
    }

    private BibliotecarioSession() { }

    public Bibliotecario getBibliotecario() { return bibliotecario; }
    public void setBibliotecario(Bibliotecario bibliotecario) { this.bibliotecario = bibliotecario; }

    public Biblioteca getBiblioteca() { return biblioteca; }

    @Override
    public void setUtente(Utente utente) {
        //no override needed
    }

    @Override
    public Utente getUtente() {
        return null;
    }

    public void setBiblioteca(Biblioteca biblioteca) { this.biblioteca = biblioteca; }

    @Override
    public void close(){
        biblioteca = null;
        bibliotecario = null;
    }

}
