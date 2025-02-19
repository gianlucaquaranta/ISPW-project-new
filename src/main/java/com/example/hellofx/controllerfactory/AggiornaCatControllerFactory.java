package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.AggiornaCatController;

public class AggiornaCatControllerFactory {
    private static AggiornaCatControllerFactory instance = null;


    public static AggiornaCatControllerFactory getInstance() {
        if(instance == null) {
            instance = new AggiornaCatControllerFactory();
        }
        return instance;
    }

    public AggiornaCatController createAggiornaCatController(){
        return new AggiornaCatController();
    }
}
