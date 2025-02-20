package com.example.hellofx.dao.bibliotecadao;

import com.example.hellofx.entity.*;

import java.util.List;

public interface BibliotecaDao {
    List<Biblioteca> loadFiltered(Filtri filtri);
    Biblioteca loadOne(String id);
    Biblioteca loadOneFromBibliotecario(String username);
    void store(Biblioteca biblioteca);
    void update(Biblioteca biblioteca);
}
