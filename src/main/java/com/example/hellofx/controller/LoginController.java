package com.example.hellofx.controller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
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
    private static final String MEMORY = "memory";

    private final UtenteDao utenteDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoUtente();
    private final BibliotecarioDao bibliotecarioDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBibliotecario();
    private final BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();

    public LoginBean authenticate(LoginBean loginBean) throws LoginException {
        // Prova autenticazione come utente
        Utente utente = authenticateUtente(loginBean);
        if (utente != null) {
            setupUtenteSession(utente);
            loginBean.setType("utente");
            return loginBean;
        }

        // Prova autenticazione come bibliotecario
        Bibliotecario bibliotecario = authenticateBibliotecario(loginBean);
        if (bibliotecario != null) {
            setupBibliotecarioSession(bibliotecario);
            loginBean.setType("bibliotecario");
            return loginBean;
        }

        throw new LoginException("Credenziali errate.");
    }

    private Utente authenticateUtente(LoginBean loginBean) {
        Utente utente = findUtente(loginBean, utenteDaoMemory.loadAllUtenti());
        if (utente == null && Session.isFull()) {
            utente = findUtente(loginBean, getUtenteDao().loadAllUtenti());
        }
        return utente;
    }

    private Bibliotecario authenticateBibliotecario(LoginBean loginBean) {
        Bibliotecario bib = findBibliotecario(loginBean, bibliotecarioDaoMemory.loadAll());
        if (bib == null && Session.isFull()) {
            bib = findBibliotecario(loginBean, getBibliotecarioDao().loadAll());
        }
        return bib;
    }

    private void setupUtenteSession(Utente utente) {
        UtenteSession utenteSession = UtenteSession.getInstance();
        if (Session.isFull()) {
            UtenteService utenteService = new UtenteService(utenteSession);
            utente = utenteService.getUtente(utente.getUsername());
        }
        utenteSession.setUtente(utente);
    }

    private void setupBibliotecarioSession(Bibliotecario bib) {
        BibliotecarioSession bibliotecarioSession = BibliotecarioSession.getInstance();
        bibliotecarioSession.setBibliotecario(bib);
        if (Session.isFull()) {
            BibliotecaService bibliotecaService = new BibliotecaService(bibliotecarioSession);
            bibliotecarioSession.setBiblioteca(bibliotecaService.getBibliotecaFromBibliotecario(bib.getUsername()));
        } else {
            bibliotecarioSession.setBiblioteca(bibliotecaDaoMemory.loadOneFromBibliotecario(bib.getUsername()));
        }
    }

    private UtenteDao getUtenteDao() {
        return Session.isFile() ? FactoryProducer.getFactory("file").createDaoUtente()
                : FactoryProducer.getFactory("db").createDaoUtente();
    }

    private BibliotecarioDao getBibliotecarioDao() {
        return Session.isFile() ? FactoryProducer.getFactory("file").createDaoBibliotecario()
                : FactoryProducer.getFactory("db").createDaoBibliotecario();
    }

    private Utente findUtente(LoginBean loginBean, List<Utente> list) {
        return list.stream()
                .filter(u -> u.getUsername().equals(loginBean.getUsername()) && u.getPassword().equals(loginBean.getPassword()))
                .findFirst()
                .orElse(null);
    }

    private Bibliotecario findBibliotecario(LoginBean loginBean, List<Bibliotecario> list) {
        return list.stream()
                .filter(b -> b.getUsername().equals(loginBean.getUsername()) && b.getPassword().equals(loginBean.getPassword()))
                .findFirst()
                .orElse(null);
    }
}