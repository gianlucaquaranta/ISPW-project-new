package com.example.hellofx.service;

import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.model.Noleggio;
import com.example.hellofx.model.Prenotazione;
import com.example.hellofx.model.Utente;

import java.util.ArrayList;
import java.util.List;

public class UtenteService {
    private static final String DB = "db";
    private UtenteDao utenteDao;
    private NoleggioDao noleggioDao;
    private PrenotazioneDao prenotazioneDao;
    private FiltriDao filtriDao;

    public UtenteService(boolean isFile) {
        this.noleggioDao = FactoryProducer.getFactory(DB).createDaoNoleggio();
        this.filtriDao = FactoryProducer.getFactory(DB).createDaoFiltri();
        this.utenteDao = FactoryProducer.getFactory(DB).createDaoUtente();

        if(isFile) {
            this.prenotazioneDao = FactoryProducer.getFactory("file").createDaoPrenotazione();
        } else {
            this.prenotazioneDao = FactoryProducer.getFactory(DB).createDaoPrenotazione();
        }
    }

    public Utente getUtente(String username){
        Utente u = utenteDao.loadUtente(username);

        //aggiungo le entity filtri all'utente
        u.setRicercheSalvate(filtriDao.loadAllUtente(username));

        //aggiungo le entity noleggio all'utente
        List<Noleggio> noleggi = noleggioDao.loadAllUtente(username);
        u.setNoleggiAttivi(noleggi);

        //aggiungo le entity prenotazione all'utente
        List<Prenotazione> prenotazioni = prenotazioneDao.loadAllUtente(username);
        u.setPrenotazioniAttive(prenotazioni);

        u.setNoleggiAttivi(new ArrayList<>());

        return u;
    }
}