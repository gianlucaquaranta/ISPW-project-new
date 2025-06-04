package com.example.hellofx.dao.prenotazionedao;

import com.example.hellofx.model.Prenotazione;

import java.util.*;

public class PrenotazioneDaoMemory implements PrenotazioneDao {
    private static Map<String, Prenotazione> prenotazioniMap = new HashMap<>();
    private static PrenotazioneDaoMemory instance = null;

    public static PrenotazioneDaoMemory getInstance() {
        if (instance == null) {
            instance = new PrenotazioneDaoMemory();
        }
        return instance;
    }

    private PrenotazioneDaoMemory() {}

    @Override
    public void delete(String idPrenotazione) {
        if(prenotazioniMap.containsKey(idPrenotazione)) {
            prenotazioniMap.remove(idPrenotazione);
        } else throw new IllegalArgumentException("Prenotazione non trovata");
    }

    @Override
    public Prenotazione loadOne(String idPrenotazione) {
        if(prenotazioniMap.containsKey(idPrenotazione)) {
            return prenotazioniMap.get(idPrenotazione);
        } else throw new IllegalArgumentException("Prenotazione non trovata");
    }

    @Override
    public void store(Prenotazione p) {
        prenotazioniMap.put(p.getIdPrenotazione(), p);
    }

    @Override
    public List<Prenotazione> loadAllUtente(String username) {
        return prenotazioniMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(username + "/"))
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public List<Prenotazione> loadAllBiblioteca(String id) {
        return prenotazioniMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains("/" + id + "/"))
                .map(Map.Entry::getValue)
                .toList();
    }

}