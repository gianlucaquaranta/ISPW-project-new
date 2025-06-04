package com.example.hellofx.controller;

import com.example.hellofx.bean.RegistrazioneBibliotecaBean;
import com.example.hellofx.bean.RegistrazioneUtenteBean;

import java.util.List;

public class RegistrazioneController {

    public Boolean registraBiblioteca(RegistrazioneBibliotecaBean rbb){
        return checkCredenziali(rbb.getNome(), "bib");
    }

    public Boolean registraUtente(RegistrazioneUtenteBean rub){
        return checkCredenziali(rub.getUsername(), "utente");
    }

    private Boolean checkCredenziali(String username, String type){
        RegistrazioneDao rd = new RegistrazioneDao();
        List<String> usernames = rd.loadAll(type);

        for(String u : usernames) {
            if (u.equals(username)) {
                return false;
            }
        }

        rd.store(username);
        return true;

    }

}
