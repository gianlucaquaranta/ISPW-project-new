package com.example.hellofx.controller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Utente;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import com.example.hellofx.session.UtenteSession;

import java.util.List;

public class LoginController {
    private static final String MEMORY = "memory";
    private final UtenteDao utenteDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoUtente();
    private final BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();

    public LoginResult authenticate(LoginBean loginBean) {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        if ("u".equals(password)) {
            Utente u = utenteDaoMemory.loadUtente(username);
            if (u == null && Session.isFull()) {
                if (Session.isFile()) {
                    UtenteDao utenteDaoFile = FactoryProducer.getFactory("file").createDaoUtente();
                    u = utenteDaoFile.loadUtente(username);
                } else {
                    // qui è necessario usare UtenteService
                    UtenteDao utenteDaoDb = FactoryProducer.getFactory("db").createDaoUtente();
                    u = utenteDaoDb.loadUtente(username);
                }
            }

            if (u != null) {
                UtenteSession session = UtenteSession.getInstance();
                session.setUtente(u);
                SessionManager.setSession(session);
                return LoginResult.UTENTE;
            }

        } else if ("b".equals(password)) {
            Biblioteca b = bibliotecaDaoMemory.loadOne(username);
            if (b == null && Session.isFull()) {
                BibliotecaDao bibliotecaDaoFile = FactoryProducer.getFactory("file").createDaoBiblioteca();
                b = bibliotecaDaoFile.loadOne(username);
            }

            if (b != null) {
                BibliotecarioSession session = BibliotecarioSession.getInstance();
                session.setBiblioteca(b);
                SessionManager.setSession(session);
                return LoginResult.UTENTE;
            }
        }

        // Se password non è "u" o "b", o non esiste username
        UtenteSession session = UtenteSession.getInstance();
        session.setUtente(null);
        SessionManager.setSession(session);
        return LoginResult.NON_AUTENTICATO;

    }
}