package com.example.hellofx.controller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.dao.DaoFactory;
import com.example.hellofx.dao.PersistenceType;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Utente;
import com.example.hellofx.service.BibliotecaService;
import com.example.hellofx.service.UtenteService;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import com.example.hellofx.session.UtenteSession;

public class LoginController {
    private final UtenteDao utenteDaoMemory = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoUtente();
    private final BibliotecaDao bibliotecaDaoMemory = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoBiblioteca();

    public LoginResult authenticate(LoginBean loginBean) {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        if (password.equals("u")) {
            Utente u = utenteDaoMemory.loadUtente(username);
            if (u == null && Session.isFull()) {
                u = UtenteService.getUtente(username);
            }

            if (u != null) {
                UtenteSession session = UtenteSession.getInstance();
                session.setUtente(u);
                SessionManager.setSession(session);
                return LoginResult.UTENTE;
            }

        } else if (password.equals("b")) {
            Biblioteca b = bibliotecaDaoMemory.loadOne(username);
            if (b == null && Session.isFull()) {
                b = BibliotecaService.load(username);
            }

            if (b != null) {
                BibliotecarioSession session = BibliotecarioSession.getInstance();
                session.setBiblioteca(b);
                SessionManager.setSession(session);

                return LoginResult.BIBLIOTECARIO;
            }
        }

        // Se password non è "u" o "b", o non esiste username
        UtenteSession session = UtenteSession.getInstance();
        session.setUtente(null);
        SessionManager.setSession(session);
        return LoginResult.NON_AUTENTICATO;

    }
}