package com.example.hellofx.dao.librodao;

import com.example.hellofx.entity.Filtri;
import com.example.hellofx.entity.Libro;

import java.util.List;

public interface LibroDao {
    Libro load(String isbn);
    List<Libro> loadAll();
    List<Libro> loadFilteredLibro(Filtri filtri);
    void store(Libro libro); //can also update existing books
}
