package com.example.hellofx.model.modelfactory;

import com.example.hellofx.model.Utente;

public class UtenteFactory {
    private static UtenteFactory instance = null;
    public static UtenteFactory getInstance() {
        if (instance == null) {
            instance = new UtenteFactory();
        }
        return instance;
    }
    private UtenteFactory() {}

    public Utente createUtente() { return new Utente(); }
}
