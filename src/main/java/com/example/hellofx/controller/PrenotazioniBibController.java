package com.example.hellofx.controller;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.model.Prenotazione;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import com.example.hellofx.session.UtenteSession;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioniBibController {
    private static final String MEMORY = "memory";
    private Session session = SessionManager.getSession();
    private PrenotazioneDao prenotazioneDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoPrenotazione();
    private final BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();

    public List<PrenotazioneBean> getPrenotazioni(){
        if(!Session.isFull()) {
            return this.search(prenotazioneDaoMemory, session);
        } else if (Session.isFile()) {
            return this.search(FactoryProducer.getFactory("file").createDaoPrenotazione(), session);
        } else {
            return this.search(FactoryProducer.getFactory("db").createDaoPrenotazione(), session);
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

        prenotazioni = dao.loadAllBiblioteca(((BibliotecarioSession)session).getBiblioteca().getId());

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
