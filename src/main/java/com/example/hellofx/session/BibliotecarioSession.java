package com.example.hellofx.session;

import com.example.hellofx.model.Biblioteca;

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

    public void close(){ biblioteca = null; }

}
