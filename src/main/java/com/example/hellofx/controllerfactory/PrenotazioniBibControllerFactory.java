package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.PrenotazioniBibController;


public class PrenotazioniBibControllerFactory {
    private static PrenotazioniBibControllerFactory instance = null;


    public static PrenotazioniBibControllerFactory getInstance() {
        if(instance == null) {
            instance = new PrenotazioniBibControllerFactory();
        }
        return instance;
    }

    public PrenotazioniBibController createGestPrenotazioniController(){
        return new PrenotazioniBibController();
    }
}
