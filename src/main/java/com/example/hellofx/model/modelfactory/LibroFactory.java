package com.example.hellofx.model.modelfactory;


import com.example.hellofx.model.Libro;

public class LibroFactory {
    private static LibroFactory instance = null;
    public static LibroFactory getInstance() {
        if (instance == null) {
            instance = new LibroFactory();
        }
        return instance;
    }
    private LibroFactory() {}

    public Libro createLibro() { return new Libro(); }
}
