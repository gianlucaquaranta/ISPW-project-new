package com.example.hellofx.controller;

import com.example.hellofx.bean.RegistrazioneBibliotecaBean;
import com.example.hellofx.bean.RegistrazioneUtenteBean;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.registrazionedao.RegistrazioneDao;
import com.example.hellofx.dao.registrazionedao.RegistrazioneDaoFile;
import com.example.hellofx.dao.registrazionedao.RegistrazioneDaoMemory;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Posizione;
import com.example.hellofx.model.Utente;
import com.example.hellofx.model.modelfactory.BibliotecaFactory;
import com.example.hellofx.model.modelfactory.UtenteFactory;
import com.example.hellofx.session.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegistrazioneController {
    private static final String MEMORY = "memory";
    private static final String FILE = "file";
    BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();
    UtenteDao utenteDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoUtente();

    public boolean registraBiblioteca(RegistrazioneBibliotecaBean rbb) {
         boolean result = checkCredenziali(rbb.getBiblioteca().getNome(), "bib");
         if(!result){
             return false;
         }
         Biblioteca b = BibliotecaFactory.getInstance().createBiblioteca();
         b.setNome(rbb.getBiblioteca().getNome());
         b.setCatalogo(new ArrayList<>());
         b.setPosizione(new Posizione(rbb.getBiblioteca().getCap(), rbb.getBiblioteca().getIndirizzo(), rbb.getBiblioteca().getNumeroCivico(), rbb.getBiblioteca().getCitta(), rbb.getBiblioteca().getProvincia()));
         b.setPrenotazioniAttive(new ArrayList<>());
         b.setCopie(new HashMap<>());
         b.setNoleggiAttivi(new ArrayList<>());
         b.setId(rbb.getBiblioteca().getNome());

         bibliotecaDaoMemory.update(b);
         if(Session.isFull()){
            BibliotecaDao bibliotecaDaoFile = FactoryProducer.getFactory(FILE).createDaoBiblioteca();
            bibliotecaDaoFile.update(b);
         }

         return true;
    }

    public boolean registraUtente(RegistrazioneUtenteBean rub) {
        boolean result = checkCredenziali(rub.getUsername(), "utente");

        if(!result){
            return false;
        }
        Utente u = UtenteFactory.getInstance().createUtente();
        u.setUsername(rub.getUsername());
        u.setEmail(rub.getEmail());
        u.setNoleggiAttivi(new ArrayList<>());
        u.setPrenotazioniAttive(new ArrayList<>());
        u.setRicercheSalvate(new ArrayList<>());

        utenteDaoMemory.storeUtente(u);
        if(Session.isFull()){

            UtenteDao utenteDaoDb = FactoryProducer.getFactory("db").createDaoUtente();
            utenteDaoDb.storeUtente(u);

        }

        return true;
    }

    private boolean checkCredenziali(String username, String type) { //true se non trova credenziali già salvate
        RegistrazioneDao rdm = RegistrazioneDaoMemory.getInstance();

        if(found(rdm.loadAll(type), username)) {
            return false;
        }

        if (Session.isFull()) {
            RegistrazioneDaoFile rdf = new RegistrazioneDaoFile();
            if(found(rdf.loadAll(type), username)) {
                return false;
            }
            rdf.store(type, username);
        }

        rdm.store(type, username);
        return true;

    }

    boolean found(List<String> list, String username) {
        if(list == null) return false;
        for (String u : list) {
            if (u.equals(username)) {
                return true;
            }
        }
        return false;
    }

}