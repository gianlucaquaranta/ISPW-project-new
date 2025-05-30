package com.example.hellofx.dao.bibliotecadao;

import com.example.hellofx.model.*;

import java.util.List;

public interface BibliotecaDao {
    List<Biblioteca> loadAll();
    Biblioteca loadOne(String id);
    void update(Biblioteca biblioteca);
}