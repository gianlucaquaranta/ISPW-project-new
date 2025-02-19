package com.example.hellofx.service;

import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.entity.Noleggio;
import com.example.hellofx.entity.Prenotazione;
import com.example.hellofx.entity.Utente;

import java.util.ArrayList;
import java.util.List;

public class UtenteService {
    private UtenteDao utenteDao;
    private NoleggioDao noleggioDao;
    private PrenotazioneDao prenotazioneDao;
    private FiltriDao filtriDao;
    private LibroDao libroDao;

    public UtenteService(UtenteDao utenteDao, NoleggioDao noleggioDao, PrenotazioneDao prenotazioneDao, FiltriDao filtriDao, LibroDao libroDao) {
        this.utenteDao = utenteDao;
        this.noleggioDao = noleggioDao;
        this.prenotazioneDao = prenotazioneDao;
        this.filtriDao = filtriDao;
        this.libroDao = libroDao;
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

        return u;
    }

}

