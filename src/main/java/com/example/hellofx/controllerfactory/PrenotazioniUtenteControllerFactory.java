package com.example.hellofx.controllerfactory;


public class PrenotazioniUtenteControllerFactory {

    private static PrenotazioniUtenteControllerFactory instance = null;

    public static PrenotazioniUtenteControllerFactory getInstance() {
        if(instance == null) {
            instance = new PrenotazioniUtenteControllerFactory();
        }
        return instance;
    }

    public PrenotazioniUtenteController createPrenotazioniUtenteController(){
        return new PrenotazioniUtenteController();
    }

}
