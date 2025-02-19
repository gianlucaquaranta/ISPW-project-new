package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.NoleggiBibController;

public class NoleggiBibControllerFactory {
    private static NoleggiBibControllerFactory instance = null;


    public static NoleggiBibControllerFactory getInstance() {
        if(instance == null) {
            instance = new NoleggiBibControllerFactory();
        }
        return instance;
    }

    public NoleggiBibController createGestNoleggiController(){
        return new NoleggiBibController();
    }
}
