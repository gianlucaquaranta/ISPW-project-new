package com.example.hellofx.controller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.bean.UtenteBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Libro;
import com.example.hellofx.model.Prenotazione;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import com.example.hellofx.session.UtenteSession;

import java.util.ArrayList;
import java.util.List;


public class PrenotazioniUtenteController {
    private static final String MEM = "memory";
    private Session session = SessionManager.getSession();
    List<Prenotazione> plist = ((UtenteSession)session).getUtente().getPrenotazioniAttive();

    public List<PrenotazioneBean> retrievePrenotazioni(){
        LibroDao libroDao;
        BibliotecaDao bibliotecaDao;

        List<PrenotazioneBean> pblist = new ArrayList<>();

        if(plist.isEmpty()){
            return pblist; //ritorna una lista vuota
        }

        if(Session.isFull()){
            libroDao = FactoryProducer.getFactory("db").createDaoLibro();
            bibliotecaDao = FactoryProducer.getFactory("file").createDaoBiblioteca();
        } else {
            libroDao = FactoryProducer.getFactory(MEM).createDaoLibro();
            bibliotecaDao = FactoryProducer.getFactory(MEM).createDaoBiblioteca();
        }

        for(Prenotazione p : plist){
            Libro l = libroDao.load(p.getIsbn());
            Biblioteca b = bibliotecaDao.loadOne(p.getIdBiblioteca());

            LibroBean lb = Converter.libroToBean(l);
            BibliotecaBean bb = Converter.bibliotecaToBean(b);
            UtenteBean ub = Converter.utenteTobean(((UtenteSession)session).getUtente());

            PrenotazioneBean pb = new PrenotazioneBean(p.getIdPrenotazione(), p.getDataInizio(), p.getDataScadenza(), ub, bb, lb);
            pblist.add(pb);
        }

        return pblist;
    }

    public void delete(PrenotazioneBean pb){
        plist.removeIf(p -> p.getIdPrenotazione().equals(pb.getId()));
    }

}
