package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.RegistrazioneController;

public class RegistrazioneControllerFactory {
    private static RegistrazioneControllerFactory instance = null;


    public static RegistrazioneControllerFactory getInstance() {
        if(instance == null) {
            instance = new RegistrazioneControllerFactory();
        }
        return instance;
    }

    public RegistrazioneController creteRegistrazioneController() {
        return new RegistrazioneController();
    }
}
