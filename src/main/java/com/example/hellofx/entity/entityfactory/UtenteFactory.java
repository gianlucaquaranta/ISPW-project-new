package com.example.hellofx.entity.entityfactory;

import com.example.hellofx.entity.Utente;

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
