package com.example.hellofx.entity.entityfactory;


import com.example.hellofx.entity.Biblioteca;

public class BibliotecaFactory {
    private static BibliotecaFactory instance = null;
    public static BibliotecaFactory getInstance() {
        if (instance == null) {
            instance = new BibliotecaFactory();
        }
        return instance;
    }
    private BibliotecaFactory() {}

    public Biblioteca createBiblioteca() { return new Biblioteca(); }
}
