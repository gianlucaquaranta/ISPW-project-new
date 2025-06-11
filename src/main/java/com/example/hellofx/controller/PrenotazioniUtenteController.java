package com.example.hellofx.controller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.bean.UtenteBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.DaoFactory;
import com.example.hellofx.dao.PersistenceType;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Libro;
import com.example.hellofx.model.Prenotazione;
import com.example.hellofx.service.BibliotecaService;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import com.example.hellofx.session.UtenteSession;

import java.util.ArrayList;
import java.util.List;


public class PrenotazioniUtenteController {
    private static final String MEM = "memory";
    private Session session = SessionManager.getSession();
    List<Prenotazione> plist = ((UtenteSession)session).getUtente().getPrenotazioniAttive();
    BibliotecaDao bibliotecaDaoM = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoBiblioteca();

    public List<PrenotazioneBean> retrievePrenotazioni(){

        List<PrenotazioneBean> pblist = new ArrayList<>();

        if(plist.isEmpty()){
            return pblist; //ritorna una lista vuota
        }

        for(Prenotazione p : plist){

            Biblioteca b;

            if(Session.isFull()){
                b = bibliotecaDaoM.loadOne(p.getIdBiblioteca());
                if(b==null){
                    b = BibliotecaService.load(p.getIdBiblioteca());
                }
            }else{
                b = bibliotecaDaoM.loadOne(p.getIdBiblioteca());
            }

            Libro l = b.getLibroByIsbn(p.getIsbn());
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

        PrenotazioneDao prenotazioneDao;
        Biblioteca b;

        if(Session.isFull()){
            prenotazioneDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoPrenotazione();
            b = bibliotecaDaoM.loadOne(pb.getBibliotecaB().getIdBiblioteca());

            if(b==null){
                b = BibliotecaService.load(pb.getBibliotecaB().getIdBiblioteca());
            }

            b.getCopie().get(pb.getIsbn())[1] ++;
            BibliotecaDao bibliotecaDao =  DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoBiblioteca();
            bibliotecaDao.update(b);



        } else {
            prenotazioneDao = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoPrenotazione();
            b = bibliotecaDaoM.loadOne(pb.getBibliotecaB().getIdBiblioteca());
            b.getCopie().get(pb.getIsbn())[1] ++;
        }

        b.getPrenotazioniAttive().removeIf(p -> p.getIdPrenotazione().equals(pb.getId()));
        prenotazioneDao.delete(pb.getId());

    }

}
