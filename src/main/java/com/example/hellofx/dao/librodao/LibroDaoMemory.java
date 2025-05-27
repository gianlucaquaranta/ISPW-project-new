package com.example.hellofx.dao.librodao;

import com.example.hellofx.model.Filtri;
import com.example.hellofx.model.Libro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroDaoMemory implements LibroDao {

    private static Map<String, Libro> libriMap = new HashMap<>();

    private static LibroDaoMemory instance = null;

    public static LibroDaoMemory getInstance() {
        if (instance == null) {
            instance = new LibroDaoMemory();
        }
        return instance;
    }

    private LibroDaoMemory() {}

    @Override
    public Libro load(String isbn) {
        if(libriMap.containsKey(isbn)) {
            return libriMap.get(isbn);
        } else throw new IllegalArgumentException("isbn not found");
    }

    @Override
    public List<Libro> loadAll() {
        List<Libro> list = new ArrayList<>();
        for(Libro libro : libriMap.values()) {
            list.add(libro);
        }
        return list;
    }

    @Override
    public void store(Libro libro) {
        if(!libriMap.containsKey(libro.getIsbn())) {
            libriMap.put(libro.getIsbn(), libro);
        } else {
            libriMap.replace(libro.getIsbn(), libro);
        }
    }

}
