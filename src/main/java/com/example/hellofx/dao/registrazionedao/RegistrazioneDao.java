package com.example.hellofx.dao.registrazionedao;

import java.util.List;

public interface RegistrazioneDao {
    List<String> loadAll(String type);
    void store(String type, String username);
}
