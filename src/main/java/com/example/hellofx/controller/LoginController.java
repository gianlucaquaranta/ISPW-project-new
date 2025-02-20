package com.example.hellofx.controller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDaoMemory;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.entity.Bibliotecario;
import com.example.hellofx.entity.Utente;
import com.example.hellofx.exception.LoginException;
import com.example.hellofx.service.BibliotecaService;
import com.example.hellofx.service.UtenteService;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;

import java.util.List;

public class LoginController {
    private UtenteDao utenteDaoMemory = FactoryProducer.getFactory("memory").createDaoUtente();
    private BibliotecarioDao bibliotecarioDaoMemory = FactoryProducer.getFactory("memory").createDaoBibliotecario();
    private BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory("memory").createDaoBiblioteca();

    public LoginBean authenticate(LoginBean loginBean) throws Exception {
        Utente utente = this.userInList(loginBean, utenteDaoMemory.loadAllUtenti());
        boolean utenteFound = utente != null;

        if(Session.isFull() && !utenteFound) {
            if(Session.isFile()){
                UtenteDao utenteDaoFile = FactoryProducer.getFactory("file").createDaoUtente();
                utente = this.userInList(loginBean, utenteDaoFile.loadAllUtenti());
                utenteFound = utente != null;
            } else{
                UtenteDao utenteDaoDb = FactoryProducer.getFactory("db").createDaoUtente();
                utente =  this.userInList(loginBean, utenteDaoDb.loadAllUtenti());
                utenteFound = utente != null;
            }
        }

        if(utenteFound) {
            UtenteSession utenteSession = UtenteSession.getInstance();
            if(Session.isFull()){
                UtenteService utenteService = new UtenteService(utenteSession);
                utente = utenteService.getUtente(utente.getUsername());
            }
            utenteSession.setUtente(utente);
            loginBean.setType("utente");
            return loginBean;
        }

        Bibliotecario bib = this.bibInList(loginBean, bibliotecarioDaoMemory.loadAll());
        boolean bibFound = bib != null;
        if(Session.isFull() && !bibFound) {
            if(Session.isFile()){
                BibliotecarioDao bibliotecarioDaoFile = FactoryProducer.getFactory("file").createDaoBibliotecario();
                bib = this.bibInList(loginBean, bibliotecarioDaoFile.loadAll());
                bibFound = bib != null;
            } else{
                BibliotecarioDao bibliotecarioDaoDb = FactoryProducer.getFactory("db").createDaoBibliotecario();
                bib = this.bibInList(loginBean, bibliotecarioDaoDb.loadAll());
                bibFound = bib != null;
            }
        }

        if(bibFound) {
            BibliotecarioSession bibliotecarioSession = BibliotecarioSession.getInstance();
            bibliotecarioSession.setBibliotecario(bib);
            bibl
            if(Session.isFull()){
                BibliotecaService bibliotecaService = new BibliotecaService(bibliotecarioSession);
                bib = bibliotecarioSession.getUtente(utente.getUsername());
            } else  {
                bibliotecarioSession.setBiblioteca(bibliotecaDaoMemory.loadOneFromBibliotecario(bib.getUsername()));
            }
            utenteSession.setUtente(utente);
            loginBean.setType("bibliotecario");
            return loginBean;
        }

        if(!utenteFound && !bibFound) throw new LoginException("Credenziali errate.");
        loginBean.setType(null);
        return loginBean;
    }

    private Utente findUtente(Utente utente){

    }


    private Utente userInList(LoginBean loginBean, List<Utente> list) {
        for (Utente utente : list) {
            if (loginBean.getUsername().equals(utente.getUsername()) && loginBean.getPassword().equals(utente.getPassword())) {
                return utente; //utente caricato dal dao
            }
        }
        return null;
    }

    private Bibliotecario bibInList(LoginBean loginBean, List<Bibliotecario> list) {
        for (Bibliotecario bib : list) {
            if (loginBean.getUsername().equals(bib.getUsername()) && loginBean.getPassword().equals(bib.getPassword())) {
                return bib;
            }
        }
        return null;
    }

}
