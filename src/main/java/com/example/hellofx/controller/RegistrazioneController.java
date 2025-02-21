package com.example.hellofx.controller;

import com.example.hellofx.bean.BibliotecarioBean;
import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.UtenteBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Bibliotecario;
import com.example.hellofx.entity.Utente;
import com.example.hellofx.exception.RegistrationException;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;
import java.util.List;

public class RegistrazioneController {

    private static final String MEMORY = "memory";

    private final UtenteDao utenteDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoUtente();
    private final BibliotecarioDao bibliotecarioDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBibliotecario();
    private final BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();
    private UtenteDao utenteDao;
    private BibliotecarioDao bibliotecarioDao;
    private BibliotecaDao bibliotecaDao = FactoryProducer.getFactory("file").createDaoBiblioteca();

    public boolean registerUtente(UtenteBean utenteBean) throws RegistrationException {
        boolean found = findUtente(utenteBean, utenteDaoMemory.loadAllUtenti());
        if(!found) {
            if(Session.isFull() && Session.isFile()){
                utenteDao = FactoryProducer.getFactory("file").createDaoUtente();
            } else if(Session.isFull() && !Session.isFile()){
                utenteDao = FactoryProducer.getFactory("db").createDaoUtente();
            }
            found = findUtente(utenteBean, utenteDao.loadAllUtenti());
        }

        if(!found) {
            Utente utente = Converter.beanToUtente(utenteBean);
            utente.setPrenotazioniAttive(null);
            utente.setRicercheSalvate(null);
            utente.setNoleggiAttivi(null);
            UtenteSession session = UtenteSession.getInstance();
            session.setUtente(utente);
            return true;
        } else throw new RegistrationException("Utente già esistente");
    }

    public boolean registerBibliotecario(BibliotecarioBean bibliotecarioBean, BibliotecaBean bibliotecaBean) throws RegistrationException {
        boolean foundBibliotecario = findBibliotecario(bibliotecarioBean, bibliotecarioDaoMemory.loadAll());
        Biblioteca biblioteca = findBiblioteca(bibliotecaBean, bibliotecaDaoMemory.loadAll());
        boolean foundBiblioteca = biblioteca != null;

        if (Session.isFull() && !foundBibliotecario) {
            bibliotecarioDao = FactoryProducer.getFactory("db").createDaoBibliotecario();
            foundBibliotecario = findBibliotecario(bibliotecarioBean, bibliotecarioDao.loadAll());
        }
        if (Session.isFull() && !foundBiblioteca) {
            bibliotecaDao = FactoryProducer.getFactory("file").createDaoBiblioteca();
            biblioteca = findBiblioteca(bibliotecaBean, bibliotecaDao.loadAll());
            foundBiblioteca = biblioteca != null;
        }

        if (foundBibliotecario) {
            throw new RegistrationException("Bibliotecario già esistente");
        }

        Bibliotecario bibliotecario = Converter.beanToBibliotecario(bibliotecarioBean);
        saveBibliotecario(bibliotecario);

        if (!foundBiblioteca) {
            biblioteca = createBiblioteca(bibliotecaBean, bibliotecario);
            saveBiblioteca(biblioteca);
        } else {
            addBibliotecarioToBiblioteca(biblioteca, bibliotecario);
            saveBiblioteca(biblioteca);
        }

        setBibliotecarioSession(bibliotecario, biblioteca);
        return true;
    }

    private void saveBibliotecario(Bibliotecario bibliotecario) {
        bibliotecarioDaoMemory.store(bibliotecario);
        if (Session.isFull()) {
            bibliotecarioDao.store(bibliotecario);
        }
    }

    private Biblioteca createBiblioteca(BibliotecaBean bibliotecaBean, Bibliotecario bibliotecario) {
        Biblioteca biblioteca = Converter.beanToBiblioteca(bibliotecaBean);
        biblioteca.getBibliotecari().add(bibliotecario);
        return biblioteca;
    }

    private void addBibliotecarioToBiblioteca(Biblioteca biblioteca, Bibliotecario bibliotecario) {
        biblioteca.getBibliotecari().add(bibliotecario);
    }

    private void saveBiblioteca(Biblioteca biblioteca) {
        bibliotecaDaoMemory.store(biblioteca);
        if (Session.isFull()) {
            bibliotecaDao.store(biblioteca);
        }
    }

    private void setBibliotecarioSession(Bibliotecario bibliotecario, Biblioteca biblioteca) {
        BibliotecarioSession session = BibliotecarioSession.getInstance();
        session.setBibliotecario(bibliotecario);
        session.setBiblioteca(biblioteca);
    }

    private boolean findUtente(UtenteBean utenteBean, List<Utente> list) {
        return list.stream()
                .map(Utente::getUsername)
                .anyMatch(username -> username.equals(utenteBean.getUsername()));
    }

    private boolean findBibliotecario(BibliotecarioBean bibliotecarioBean, List<Bibliotecario> list) {
        return list.stream()
                .map(Bibliotecario::getUsername)
                .anyMatch(username -> username.equals(bibliotecarioBean.getUsername()));
    }

    private Biblioteca findBiblioteca(BibliotecaBean bibliotecaBean, List<Biblioteca> list) {
        return list.stream()
                .filter(biblioteca -> biblioteca.getBibliotecari().stream()
                        .anyMatch(b -> b.getUsername().equals(bibliotecaBean.getUsernameBibliotecario())))
                .findFirst()
                .orElse(null);
    }
}
