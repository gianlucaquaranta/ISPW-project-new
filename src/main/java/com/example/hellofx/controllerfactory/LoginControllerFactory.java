package com.example.hellofx.controllerfactory;

import com.example.hellofx.controller.LoginController;

public class LoginControllerFactory{

    public static LoginControllerFactory instance = null;


    public static LoginControllerFactory getInstance() {
        if(instance == null) {
            instance = new LoginControllerFactory();
        }
        return instance;
    }

    public LoginController createLoginController(){
        return new LoginController();
    }

}
