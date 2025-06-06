package com.example.hellofx.service;

import com.example.hellofx.dao.DaoFactory;
import com.example.hellofx.dao.PersistenceType;
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
    private static UtenteDao utenteDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoUtente();
    private static NoleggioDao noleggioDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoNoleggio();
    private static PrenotazioneDao prenotazioneDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoPrenotazione();
    private static FiltriDao filtriDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoFiltri();

    private UtenteService() {}

    public static Utente getUtente(String username){
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

        // salvataggio in cache dell'utente creato
        DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoUtente().storeUtente(u);
        return u;
    }
}