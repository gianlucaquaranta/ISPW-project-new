package com.example.hellofx.dao.librodao;

import com.example.hellofx.model.Libro;

import java.util.List;

public interface LibroDao {
    Libro load(String isbn, String idBiblioteca);
    List<Libro> loadAll(String idBiblioteca);
    void store(Libro libro, String idBiblioteca); //can also update existing books
}
