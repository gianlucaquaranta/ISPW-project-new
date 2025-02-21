package com.example.hellofx.controllerfactory;


import com.example.hellofx.controller.GestNoleggiController;

public class GestNoleggiControllerFactory {

    private static GestNoleggiControllerFactory instance = null;

    public static GestNoleggiControllerFactory getInstance() {
        if(instance == null) {
            instance = new GestNoleggiControllerFactory();
        }
        return instance;
    }

    public GestNoleggiController createGestNoleggiController(){
        return new GestNoleggiController();
    }

}
