package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.NoleggiUtenteController;

public class NoleggiUtenteControllerFactory {

    private static NoleggiUtenteControllerFactory instance = null;


    public static NoleggiUtenteControllerFactory getInstance() {
        if(instance == null) {
            instance = new NoleggiUtenteControllerFactory();
        }
        return instance;
    }

    public NoleggiUtenteController createNoleggiUtenteController(){
        return new NoleggiUtenteController();
    }

}
