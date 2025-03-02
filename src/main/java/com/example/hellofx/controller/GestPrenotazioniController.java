package com.example.hellofx.controller;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Prenotazione;
import com.example.hellofx.service.BibliotecaService;
import com.example.hellofx.service.UtenteService;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestPrenotazioniController {
    private Session bibliotecarioSession = BibliotecarioSession.getInstance();
    private Session utenteSession = UtenteSession.getInstance();

    private static final String MEMORY = "memory";
    private final PrenotazioneDao prenotazioneDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoPrenotazione();
    private final UtenteDao utenteDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoUtente();
    private final BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();

    public List<PrenotazioneBean> getPrenotazioniBiblioteca(){
        if(!Session.isFull()) {
            return this.search(prenotazioneDaoMemory, bibliotecarioSession);
        } else if (Session.isFile()) {
            return this.search(FactoryProducer.getFactory("file").createDaoPrenotazione(), bibliotecarioSession);
        } else {
            return this.search(FactoryProducer.getFactory("db").createDaoPrenotazione(), bibliotecarioSession);
        }
    }

    public List<PrenotazioneBean> getPrenotazioniUtente(){
        if(!Session.isFull()) {
            return this.search(prenotazioneDaoMemory, utenteSession);
        } else if (Session.isFile()) {
            return this.search(FactoryProducer.getFactory("file").createDaoPrenotazione(), bibliotecarioSession);
        } else {
            return this.search(FactoryProducer.getFactory("db").createDaoPrenotazione(), bibliotecarioSession);
        }
    }

    public void add(PrenotazioneBean pb){
        Prenotazione prenotazione = Converter.beanToPrenotazione(pb);
        //store nella cache
        prenotazioneDaoMemory.store(prenotazione);

        //aggiungere alla lista di prenotazioni dell'utente corrente
        utenteSession.getUtente().getPrenotazioniAttive().add(prenotazione);

        //ottenere la biblioteca a cui la prenotazione si riferisce e aggiungere la prenotazione alla sua lista
        Biblioteca biblioteca = bibliotecaDaoMemory.loadOne(prenotazione.getIdBiblioteca());
        if(biblioteca != null) {
            biblioteca.getPrenotazioniAttive().add(prenotazione);
        } else if(Session.isFull()) {
            BibliotecaService bibliotecaService = new BibliotecaService(utenteSession);
            biblioteca = bibliotecaService.getBibliotecaFromId(prenotazione.getIdBiblioteca());
        }

        utenteDaoMemory.storeUtente(utenteSession.getUtente());
        bibliotecaDaoMemory.store(biblioteca);

        if(Session.isFull()) {
            UtenteService utenteService = new UtenteService(utenteSession.isFile());
            BibliotecaService bibliotecaService = new BibliotecaService(utenteSession);
            utenteService.updateUtente(utenteSession.getUtente());
            bibliotecaService.updateBiblioteca(biblioteca);
        }
    }

    private void delete(List<Prenotazione> toBeDeleted){
        for(Prenotazione p : toBeDeleted) {
            prenotazioneDaoMemory.delete(p.getIdPrenotazione());
            if (Session.isFull() && Session.isFile()) {
                FactoryProducer.getFactory("file").createDaoPrenotazione().delete(p.getIdPrenotazione());
            } else if (Session.isFull()) {
                FactoryProducer.getFactory("db").createDaoPrenotazione().delete(p.getIdPrenotazione());
            }
        }
    }

    private List<PrenotazioneBean> search(PrenotazioneDao dao, Session session){
        List<PrenotazioneBean> beanList = new ArrayList<>();
        List<Prenotazione> toBeDeleted = new ArrayList<>();
        List<Prenotazione> prenotazioni;

        if (session instanceof BibliotecarioSession) {
            prenotazioni = dao.loadAllBiblioteca(bibliotecarioSession.getBiblioteca().getId());
        } else {
            prenotazioni = dao.loadAllUtente(utenteSession.getUtente().getUsername());
        }

        for (Prenotazione p : prenotazioni) {
            if (Timestamp.valueOf(LocalDateTime.now().plusDays(1))
                    .after(Converter.stringToTimestamp(p.getDataScadenza()))) {
                toBeDeleted.add(p);
            } else {
                beanList.add(Converter.prenotazioneToBean(p));
            }
        }

        delete(toBeDeleted);
        return beanList;
    }
}
