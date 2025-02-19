package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.RegistrazioneController;

public class RegistrazioneControllerFactory {

    public static RegistrazioneControllerFactory instance = null;

    public static RegistrazioneControllerFactory getInstance() {
        if(instance == null) {
            instance = new RegistrazioneControllerFactory();
        }
        return instance;
    }

    public RegistrazioneController createRegistrazioneController() {
        return new RegistrazioneController();
    }

}
