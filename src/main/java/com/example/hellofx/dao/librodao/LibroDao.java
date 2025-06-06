package com.example.hellofx.dao.librodao;

import com.example.hellofx.model.Libro;

import java.util.List;

public interface LibroDao {
    Libro load(String isbn);
    List<Libro> loadAll();
    void store(Libro libro); //can also update existing books
}
