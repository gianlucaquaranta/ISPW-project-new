package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.TrovaPrezziController;

public class TrovaPrezziControllerFactory {
    private static TrovaPrezziControllerFactory instance = null;


    public static TrovaPrezziControllerFactory getInstance() {
        if(instance == null) {
            instance = new TrovaPrezziControllerFactory();
        }
        return instance;
    }

    public TrovaPrezziController createTrovaPrezziController(){
        return new TrovaPrezziController();
    }
}
