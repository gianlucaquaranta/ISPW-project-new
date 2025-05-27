package com.example.hellofx.dao.noleggiodao;

import com.example.hellofx.model.Noleggio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoleggioDaoMemory implements NoleggioDao {
    private static Map<String, Noleggio> noleggiMap = new HashMap<>();
    private static NoleggioDaoMemory instance = null;



    public static NoleggioDaoMemory getInstance() {
        if (instance == null) {
            instance = new NoleggioDaoMemory();
        }
        return instance;
    }

    private NoleggioDaoMemory() {}

    @Override
    public Noleggio loadOne(String[] idNoleggio) {
        String key = idNoleggio[0] +"*"+ idNoleggio[1] +"*"+ idNoleggio[2];
        if (noleggiMap.containsKey(key)) {
            return noleggiMap.get(key);
        } else throw new IllegalArgumentException("l'id non corrisponde a nessun noleggio");
    }

    @Override
    public List<Noleggio> loadAllUtente(String username) {
        return noleggiMap.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(username + "*"))
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public List<Noleggio> loadAllBiblioteca(String id) {
        return noleggiMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains("*" + id + "*"))
                .map(Map.Entry::getValue)
                .toList();
    }

    @Override
    public void delete(String[] idNoleggio) {
        String key = idNoleggio[0] +"*"+ idNoleggio[1] +"*"+ idNoleggio[2];
        if (noleggiMap.containsKey(key)) {
            noleggiMap.remove(key);
        } else throw new IllegalArgumentException("l'id non corrisponde a nessun noleggio");
    }

    @Override
    public void store(Noleggio noleggio) {
        String[] keyList = noleggio.getIdNoleggio();
        noleggiMap.put(keyList[0] +"*"+ keyList[1] +"*"+ keyList[2], noleggio);
    }

    @Override
    public void update(Noleggio noleggio) {
        String[] keyList = noleggio.getIdNoleggio();
        noleggiMap.replace(keyList[0] +"*"+ keyList[1] +"*"+ keyList[2], noleggio);
    }

}

