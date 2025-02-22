package com.example.hellofx.controller;

import com.example.hellofx.bean.NoleggioBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.entity.Noleggio;
import com.example.hellofx.entity.Utente;
import com.example.hellofx.service.BibliotecaService;
import com.example.hellofx.service.UtenteService;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GestNoleggiController {
    private Session bibliotecarioSession = BibliotecarioSession.getInstance();
    private Session utenteSession = UtenteSession.getInstance();

    private static final String MEMORY = "memory";
    private final NoleggioDao noleggioDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoNoleggio();
    private final UtenteDao utenteDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoUtente();
    private final BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();

    public List<NoleggioBean> getNoleggiBiblioteca(){
        if(!Session.isFull()) {
            return this.search(noleggioDaoMemory, bibliotecarioSession);
            } else {
            return this.search(FactoryProducer.getFactory("db").createDaoNoleggio(), bibliotecarioSession);
        }
    }

    public List<NoleggioBean> getNoleggiUtente(){
        if(!Session.isFull()) {
            return this.search(noleggioDaoMemory, utenteSession);
        } else {
            return this.search(FactoryProducer.getFactory("db").createDaoNoleggio(), utenteSession);
        }
    }

    public void add(NoleggioBean noleggioBean){
        Noleggio noleggio = Converter.beanToNoleggio(noleggioBean);
        //store nella cache
        noleggioDaoMemory.store(noleggio);

        //aggiungere alla lista di noleggi della biblioteca corrente
        bibliotecarioSession.getBiblioteca().getNoleggiAttivi().add(noleggio);

        Utente utente = utenteDaoMemory.loadUtente(noleggio.getDatiUtente()[0]);
        if(utente != null) {
            utente.getNoleggiAttivi().add(noleggio);
        } else if(Session.isFull()) {
            UtenteService utenteService = new UtenteService(bibliotecarioSession);
            utente = utenteService.getUtente(noleggio.getDatiUtente()[0]);
        }

        utente.getNoleggiAttivi().add(noleggio);

        utenteDaoMemory.storeUtente(utente);
        bibliotecaDaoMemory.store(bibliotecarioSession.getBiblioteca());

        if(Session.isFull()) {
            UtenteService utenteService = new UtenteService(bibliotecarioSession);
            BibliotecaService bibliotecaService = new BibliotecaService(bibliotecarioSession);
            utenteService.updateUtente(utente);
            bibliotecaService.updateBiblioteca(bibliotecarioSession.getBiblioteca());
        }
    }

    private void delete(List<Noleggio> toBeDeleted){
        for(Noleggio noleggio : toBeDeleted) {
            noleggioDaoMemory.delete(noleggio.getIdNoleggio());
            if (Session.isFull()) {
                FactoryProducer.getFactory("db").createDaoNoleggio().delete(noleggio.getIdNoleggio());
            }
        }

    }

    private List<NoleggioBean> search(NoleggioDao dao, Session session){
        List<NoleggioBean> beanList = new ArrayList<>();
        List<Noleggio> toBeDeleted = new ArrayList<>();
        List<Noleggio> noleggi;

        if (session instanceof BibliotecarioSession) {
            noleggi = dao.loadAllBiblioteca(bibliotecarioSession.getBiblioteca().getId());
        } else {
            noleggi = dao.loadAllUtente(utenteSession.getUtente().getUsername());
        }

        for (Noleggio n : noleggi) {
            if (Timestamp.valueOf(LocalDateTime.now().plusDays(1))
                    .after(Converter.stringToTimestamp(n.getDataScadenza()))) {
                toBeDeleted.add(n);
            } else {
                beanList.add(Converter.noleggioToBean(n));
            }
        }

        delete(toBeDeleted);
        return beanList;
    }

}
