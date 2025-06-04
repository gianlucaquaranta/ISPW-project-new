package com.example.hellofx.controller;

import com.example.hellofx.bean.RegistrazioneBibliotecaBean;
import com.example.hellofx.bean.RegistrazioneUtenteBean;
import com.example.hellofx.dao.registrazionedao.RegistrazioneDao;

import java.util.List;

public class RegistrazioneController {

    public boolean registraBiblioteca(RegistrazioneBibliotecaBean rbb){
        return checkCredenziali(rbb.getNome(), "bib");
    }

    public boolean registraUtente(RegistrazioneUtenteBean rub){
        return checkCredenziali(rub.getUsername(), "utente");
    }

    private boolean checkCredenziali(String username, String type){
        RegistrazioneDao rd = new RegistrazioneDao();
        List<String> usernames = rd.loadAll(type);

        for(String u : usernames) {
            if (u.equals(username)) {
                return false;
            }
        }

        rd.store(type, username);
        return true;

    }

}
