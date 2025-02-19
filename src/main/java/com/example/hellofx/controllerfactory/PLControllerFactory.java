package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.PLController;

public class PLControllerFactory {
    private static PLControllerFactory instance = null;


    public static PLControllerFactory getInstance() {
        if(instance == null) {
            instance = new PLControllerFactory();
        }
        return instance;
    }

    public PLController createPLController(){
        return new PLController();
    }
}
