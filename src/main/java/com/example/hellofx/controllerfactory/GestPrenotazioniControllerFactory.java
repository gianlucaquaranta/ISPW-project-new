package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.GestPrenotazioniController;


public class GestPrenotazioniControllerFactory {
    private static GestPrenotazioniControllerFactory instance = null;


    public static GestPrenotazioniControllerFactory getInstance() {
        if(instance == null) {
            instance = new GestPrenotazioniControllerFactory();
        }
        return instance;
    }

    public GestPrenotazioniController createGestPrenotazioniController(){
        return new GestPrenotazioniController();
    }
}
