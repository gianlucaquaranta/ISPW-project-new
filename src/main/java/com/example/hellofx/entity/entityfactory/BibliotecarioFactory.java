package com.example.hellofx.entity.entityfactory;

import com.example.hellofx.entity.Bibliotecario;

public class BibliotecarioFactory {
    private static BibliotecarioFactory instance = null;
    public static BibliotecarioFactory getInstance() {
        if (instance == null) {
            instance = new BibliotecarioFactory();
        }
        return instance;
    }
    private BibliotecarioFactory() {}

    public Bibliotecario createBibliotecario() { return new Bibliotecario(); }
}
