package com.example.hellofx.dao.librodao;

import com.example.hellofx.model.Libro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibroDaoMemory implements LibroDao {

    //idBiblioteca -> (isbn -> Libro)
    private static Map<String, Map<String, Libro>> bibliotecheMap = new HashMap<>();

    private static LibroDaoMemory instance = null;

    public static LibroDaoMemory getInstance() {
        if (instance == null) {
            instance = new LibroDaoMemory();
        }
        return instance;
    }

    private LibroDaoMemory() {}

    @Override
    public Libro load(String isbn, String idBiblioteca) {
        if (!bibliotecheMap.containsKey(idBiblioteca)) {
            return null;
        }
        return bibliotecheMap.get(idBiblioteca).get(isbn);
    }

    @Override
    public List<Libro> loadAll(String idBiblioteca) {
        if (!bibliotecheMap.containsKey(idBiblioteca)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(bibliotecheMap.get(idBiblioteca).values());
    }

    @Override
    public void store(Libro libro, String idBiblioteca) {
        // Se la biblioteca non esiste ancora, la creiamo
        bibliotecheMap.computeIfAbsent(idBiblioteca, k -> new HashMap<>());

        // Aggiungiamo/sostituiamo il libro nella biblioteca specificata
        bibliotecheMap.get(idBiblioteca).put(libro.getIsbn(), libro);
    }

    // Metodo aggiuntivo per rimuovere una biblioteca
    public void removeBiblioteca(String idBiblioteca) {
        bibliotecheMap.remove(idBiblioteca);
    }

    // Metodo aggiuntivo per verificare l'esistenza di una biblioteca
    public boolean containsBiblioteca(String idBiblioteca) {
        return bibliotecheMap.containsKey(idBiblioteca);
    }
}