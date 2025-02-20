package com.example.hellofx.dao.bibliotecariodao;

import com.example.hellofx.entity.Bibliotecario;

import java.util.List;

public interface BibliotecarioDao {
    List<Bibliotecario> loadAll();
    Bibliotecario load(String username);
    void store(Bibliotecario bibliotecario);
    void update(Bibliotecario bibliotecario);
    void delete(String username);
}
