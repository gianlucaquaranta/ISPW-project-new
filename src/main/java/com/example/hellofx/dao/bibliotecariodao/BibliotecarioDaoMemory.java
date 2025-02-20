package com.example.hellofx.dao.bibliotecariodao;

import com.example.hellofx.entity.Bibliotecario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BibliotecarioDaoMemory implements BibliotecarioDao {

    private static BibliotecarioDaoMemory instance = null;

    public static BibliotecarioDao getInstance() {
        if (instance == null) {
            instance = new BibliotecarioDaoMemory();
        }
        return instance;
    }

    private BibliotecarioDaoMemory() {}

    private static Map<String, Bibliotecario> bibliotecariMap = new HashMap<>();

    @Override
    public List<Bibliotecario> loadAll() {
        List<Bibliotecario> list = new ArrayList<>();
        for(Map.Entry<String, Bibliotecario> entry : bibliotecariMap.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public Bibliotecario load(String username) {
        if(bibliotecariMap.containsKey(username)){
            return bibliotecariMap.get(username);
        } else throw new IllegalArgumentException("Bibliotecario non trovato");
    }

    @Override
    public void store(Bibliotecario bibliotecario) {
        bibliotecariMap.put(bibliotecario.getUsername(), bibliotecario);
    }

    @Override
    public void update(Bibliotecario bibliotecario) {
        bibliotecariMap.replace(bibliotecario.getUsername(), bibliotecario);
    }

    @Override
    public void delete(String username) {
        if (bibliotecariMap.containsKey(username)) {
            bibliotecariMap.remove(username);
        } else throw new IllegalArgumentException("Bibliotecario non trovato");
    }

}
