package com.example.hellofx.dao.noleggiodao;

import com.example.hellofx.model.Noleggio;

import java.util.List;

public interface NoleggioDao {
    Noleggio loadOne(String[] idNoleggio);
    List<Noleggio> loadAllUtente(String username);
    List<Noleggio> loadAllBiblioteca (String id);
    void delete(String[] idNoleggio);
    void store(Noleggio noleggio);
    void update(Noleggio noleggio);
}
