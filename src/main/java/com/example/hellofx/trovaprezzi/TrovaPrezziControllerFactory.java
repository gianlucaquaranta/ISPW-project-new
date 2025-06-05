package com.example.hellofx.trovaprezzi;

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
