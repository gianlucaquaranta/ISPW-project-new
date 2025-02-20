package com.example.hellofx.dao.utentedao;

import com.example.hellofx.entity.Utente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtenteDaoMemory implements UtenteDao {

    private static UtenteDaoMemory instance = null;

    public static UtenteDaoMemory getInstance() {
        if(instance == null) {
            instance = new UtenteDaoMemory();
        }
        return instance;
    }

    private UtenteDaoMemory() {}

    private static Map<String, Utente> utentiMap = new HashMap<>();

    @Override
    public Utente loadUtente(String username) {
        if (utentiMap.containsKey(username)) {
            return utentiMap.get(username);
        } else throw new IllegalArgumentException("Utente non trovato"); //throws IllegalArgEx nella segnatura
    }

    @Override
    public List<Utente> loadAllUtenti() {
        List<Utente> list = new ArrayList<>();
        for (Utente u : utentiMap.values()) {
            list.add(u);
        }
        return list;
    }

    @Override
    public void storeUtente(Utente utente) {
        if (!utentiMap.containsKey(utente.getUsername())) {
            utentiMap.put(utente.getUsername(), utente);
        } else throw new IllegalArgumentException("utente già esistente");
    }

    @Override
    public void updateUtente(Utente utente) {
        if (utentiMap.containsKey(utente.getUsername())) {
            utentiMap.replace(utente.getUsername(), utente);
        } else throw new IllegalArgumentException("utente non trovato");
    }

    @Override
    public void deleteUtente(String username) {
        if (utentiMap.containsKey(username)) {
            utentiMap.remove(username);
        } else throw new IllegalArgumentException("Utente non trovato");
    }

}
