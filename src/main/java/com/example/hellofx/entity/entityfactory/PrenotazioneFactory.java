package com.example.hellofx.entity.entityfactory;


import com.example.hellofx.entity.Prenotazione;

public class PrenotazioneFactory {
    private static PrenotazioneFactory instance = new PrenotazioneFactory();
    public static PrenotazioneFactory getInstance() {
        if (instance == null) {
            instance = new PrenotazioneFactory();
        }
        return instance;
    }
    private PrenotazioneFactory() {}

    public Prenotazione createPrenotazione() { return new Prenotazione(); }
}
