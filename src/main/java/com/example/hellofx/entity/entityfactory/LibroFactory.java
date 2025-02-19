package com.example.hellofx.entity.entityfactory;


import com.example.hellofx.entity.Libro;

public class LibroFactory {
    private static LibroFactory instance = new LibroFactory();
    public static LibroFactory getInstance() {
        if (instance == null) {
            instance = new LibroFactory();
        }
        return instance;
    }
    private LibroFactory() {}

    public Libro createLibro() { return new Libro(); }
}
