package com.example.hellofx.dao.bibliotecadao;

import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Filtri;

import java.util.List;

public class BibliotecaDaoFile implements BibliotecaDao {

    private BibliotecaDaoMemory bibliotecaDaoMemory = BibliotecaDaoMemory.getInstance();

    @Override
    public List<Biblioteca> loadFiltered(Filtri filtri) {
        return List.of();
    }

    @Override
    public Biblioteca loadOne(String id) {
        return null;
    }

    @Override
    public void store(Biblioteca biblioteca) {

    }

    @Override
    public void update(Biblioteca biblioteca) {

    }
}
