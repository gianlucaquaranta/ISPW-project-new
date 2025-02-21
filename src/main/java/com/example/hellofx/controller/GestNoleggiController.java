package com.example.hellofx.controller;

import com.example.hellofx.bean.NoleggioBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Noleggio;
import com.example.hellofx.entity.Utente;
import com.example.hellofx.service.UtenteService;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;

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
            return this.searchForBiblioteca(noleggioDaoMemory);
            } else {
            return this.searchForBiblioteca(FactoryProducer.getFactory("db").createDaoNoleggio());
        }
    }

    public List<NoleggioBean> getNoleggiUtente(){
        if(!Session.isFull()) {
            return this.searchForUtente(noleggioDaoMemory);
        } else {
            return this.searchForUtente(FactoryProducer.getFactory("db").createDaoNoleggio());
        }
    }

    public void add(NoleggioBean noleggioBean){
        Noleggio noleggio = Converter.beanToNoleggio(noleggioBean);
        //store nella cache
        noleggioDaoMemory.store(noleggio);

        //aggiungere alla lista di noleggi della biblioteca corrente
        List<Noleggio> list = bibliotecarioSession.getBiblioteca().getNoleggiAttivi();
        list.add(noleggio);
        bibliotecarioSession.getBiblioteca().setNoleggiAttivi(list);

        Utente utente = utenteDaoMemory.loadUtente(noleggioBean.getDatiUtente()[0]);
        if(utente != null) {
            utente.getNoleggiAttivi().add(noleggio);
        } else if(Session.isFull()) {
            UtenteService utenteService = new UtenteService(bibliotecarioSession);
            utente = utenteService.getUtente(noleggioBean.getDatiUtente()[0]);
        }



        //se l'utente non è null aggiungo il noleggio
        //se sto in full e non c'è in mem vado nel db o nel file a seconda di isFile()
        //faccio la get dell'utente tramite sessione e setto il noleggio
        //update dell'utente in file/db
        //update dell'utente in memory

        //update della biblioteca
        //Se sto in full anche nel file
    }

    public void delete(NoleggioBean noleggioBean){

    }

    private List<NoleggioBean> searchForBiblioteca(NoleggioDao dao){
        List<NoleggioBean> beanList = new ArrayList<>();
        for(Noleggio n : noleggioDaoMemory.loadAllBiblioteca(bibliotecarioSession.getBiblioteca().getId())) {
            beanList.add(Converter.noleggioToBean(n));
        }
        return beanList;
    }

    private List<NoleggioBean> searchForUtente(NoleggioDao dao){
        List<NoleggioBean> beanList = new ArrayList<>();
        for(Noleggio n : noleggioDaoMemory.loadAllUtente(utenteSession.getUtente().getUsername())) {
            beanList.add(Converter.noleggioToBean(n));
        }
        return beanList;
    }
}
