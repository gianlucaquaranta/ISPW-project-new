package com.example.hellofx.dao.filtridao;

import com.example.hellofx.entity.Filtri;

import java.util.List;

public interface FiltriDao {
    List<Filtri> loadAllUtente(String username);
    void storeOne(Filtri filtri, String username);
    void deleteAllUtente(String username);
    void deleteOne(String username, Integer i);
}
