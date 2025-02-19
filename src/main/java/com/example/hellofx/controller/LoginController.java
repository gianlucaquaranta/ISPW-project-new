package com.example.hellofx.controller;

import com.example.hellofx.bean.LoginBean;

public class LoginController {

    public LoginBean authenticate(LoginBean loginBean) {
        if(true) { //autenticazione
            loginBean.setType("utente");
            return loginBean;
        } else return null;

        //tira dentro la lista degli utenti
        //se l'username è tra questi inizializza una sessione utente
        //Costruisci tramite service l'entity utente necessaria e emttila nella sessione

        //Se non è così tira dentro i bibliotecari
        // se l'username esiste inizializza la sessione utente con i dati del bibliotecario
        //ottieni la biblioteca tramite service e mettila nella sessione


    }

}
