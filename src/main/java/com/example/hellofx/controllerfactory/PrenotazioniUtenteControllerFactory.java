package com.example.hellofx.controllerfactory;


import com.example.hellofx.controller.PrenotazioniUtenteController;

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
