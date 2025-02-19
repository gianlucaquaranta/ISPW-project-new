package com.example.hellofx.dao.bibliotecariodao;

import com.example.hellofx.entity.Bibliotecario;

public interface BibliotecarioDao {
    Bibliotecario load(String username);
    void store(Bibliotecario bibliotecario);
    void update(Bibliotecario bibliotecario);
    void delete(String username);
}
