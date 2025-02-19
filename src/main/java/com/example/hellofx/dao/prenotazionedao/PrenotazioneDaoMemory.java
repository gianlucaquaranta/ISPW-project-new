package com.example.hellofx.dao.prenotazionedao;

import com.example.hellofx.entity.Prenotazione;

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
    public void delete(String[] idPrenotazione) {
        String key = idPrenotazione[0] +"*"+ idPrenotazione[1] +"*"+ idPrenotazione[2];
        if(prenotazioniMap.containsKey(key)) {
            prenotazioniMap.remove(key);
        } else throw new IllegalArgumentException("Prenotazione non trovata");
    }

    @Override
    public Prenotazione loadOne(String[] idPrenotazione) {
        String key = idPrenotazione[0] +"*"+ idPrenotazione[1] +"*"+ idPrenotazione[2];
        if(prenotazioniMap.containsKey(key)) {
            return prenotazioniMap.get(key);
        } else throw new IllegalArgumentException("Prenotazione non trovata");
    }

    @Override
    public void store(Prenotazione p) {
        String[] keyList = p.getIdPrenotazione();
        prenotazioniMap.put(keyList[0] +"*"+ keyList[1] +"*"+ keyList[2], p);
    }

    @Override
    public List<Prenotazione> loadAllUtente(String username) {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        for(String key : prenotazioniMap.keySet()) {
            if(key.startsWith(username + "*")) {
                prenotazioni.add(prenotazioniMap.get(key));
            }
        }
        return prenotazioni;
    }

    @Override
    public List<Prenotazione> loadAllBiblioteca(String id) {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        for(String key : prenotazioniMap.keySet()) {
            if(key.contains("*" + id + "*")) {
                prenotazioni.add(prenotazioniMap.get(key));
            }
        }
        return prenotazioni;
    }

}