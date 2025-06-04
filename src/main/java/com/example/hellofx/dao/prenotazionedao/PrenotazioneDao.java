package com.example.hellofx.dao.prenotazionedao;

import com.example.hellofx.model.Prenotazione;

import java.util.List;

public interface PrenotazioneDao {
    Prenotazione loadOne(String idPrenotazione);
    List<Prenotazione> loadAllUtente(String username);
    List<Prenotazione> loadAllBiblioteca(String id);
    void store(Prenotazione p);
    void delete(String idPrenotazione);
}
