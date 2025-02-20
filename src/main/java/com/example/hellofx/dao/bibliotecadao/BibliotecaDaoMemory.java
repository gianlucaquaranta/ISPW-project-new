package com.example.hellofx.dao.bibliotecadao;

import com.example.hellofx.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BibliotecaDaoMemory implements BibliotecaDao {

    private Map<String, Biblioteca> bibliotecheMap = new HashMap<>();
    private static BibliotecaDaoMemory instance = null;

    public static BibliotecaDaoMemory getInstance() {
        if (instance == null) {
            instance = new BibliotecaDaoMemory();
        }
        return instance;
    }

    private BibliotecaDaoMemory() {}

    @Override
    public List<Biblioteca> loadAll() {
        List<Biblioteca> biblioteche = new ArrayList<>();
        for (Map.Entry<String, Biblioteca> entry : bibliotecheMap.entrySet()) {
            biblioteche.add(entry.getValue());
        }
        return biblioteche;
    }


    @Override
    public List<Biblioteca> loadFiltered(Filtri filtri) {
        return bibliotecheMap.values().stream()
                .filter(b -> (filtri.getBiblioteca() == null || b.getNome().toLowerCase().contains(filtri.getBiblioteca().toLowerCase())))
                .filter(b -> (filtri.getCap() == null || b.getPosizione().getCap().equals(filtri.getCap())))
                .filter(b -> (filtri.getIsbn() == null || b.isLibroInCatalogo(filtri.getIsbn())))
                .toList();
    }

    @Override
    public Biblioteca loadOne(String id) {
        if(bibliotecheMap.containsKey(id)) {
            return bibliotecheMap.get(id);
        } else throw new IllegalArgumentException("Id " + id + " non trovato");
    }

    @Override
    public Biblioteca loadOneFromBibliotecario(String username) {
        for (Map.Entry<String, Biblioteca> entry : bibliotecheMap.entrySet()) {
            Biblioteca biblioteca = entry.getValue();
            if (biblioteca.getBibliotecari().stream().anyMatch(b -> b.getUsername().equals(username))) {
                return biblioteca;
            }
        }
        throw new IllegalArgumentException("Nessuna biblioteca trovata per il bibliotecario con username: " + username);
    }

    @Override
    public void store(Biblioteca biblioteca) {
        String key = biblioteca.getId();
        if(!bibliotecheMap.containsKey(key)){
            bibliotecheMap.put(key, biblioteca);
        } else throw new IllegalArgumentException("biblioteca already exists");
    }

    @Override
    public void update(Biblioteca biblioteca) {
        String key = biblioteca.getId();
        if(bibliotecheMap.containsKey(key)) {
            bibliotecheMap.replace(key, biblioteca);
        } else throw new IllegalArgumentException("id not found");
    }
}