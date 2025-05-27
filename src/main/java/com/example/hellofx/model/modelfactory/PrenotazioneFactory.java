package com.example.hellofx.model.modelfactory;


import com.example.hellofx.model.Prenotazione;

public class PrenotazioneFactory {
    private static PrenotazioneFactory instance = null;
    public static PrenotazioneFactory getInstance() {
        if (instance == null) {
            instance = new PrenotazioneFactory();
        }
        return instance;
    }
    private PrenotazioneFactory() {}

    public Prenotazione createPrenotazione() { return new Prenotazione(); }
}
