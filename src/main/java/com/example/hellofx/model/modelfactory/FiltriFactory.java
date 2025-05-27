package com.example.hellofx.model.modelfactory;

import com.example.hellofx.model.Filtri;

public class FiltriFactory {
    private static FiltriFactory instance = null;
    public static FiltriFactory getInstance() {
        if (instance == null) {
            instance = new FiltriFactory();
        }
        return instance;
    }
    private FiltriFactory() {}

    public Filtri createFiltri(String titolo, String autore, String genere, String biblioteca, String raggio, String isbn, String cap) {
        return new Filtri(titolo, autore, genere, biblioteca, raggio, isbn, cap);
    }
}
