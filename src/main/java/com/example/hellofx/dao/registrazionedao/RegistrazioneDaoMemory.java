package com.example.hellofx.dao.registrazionedao;

import java.util.List;

public class RegistrazioneDaoMemory implements RegistrazioneDao {
    private static List<String> BibliotecaUsername;
    private static List<String> UtentiUsername;
    private static RegistrazioneDaoMemory instance = null;

    public static RegistrazioneDaoMemory getInstance() {
        if (instance == null) {
            instance = new RegistrazioneDaoMemory();
        }
        return instance;
    }

    private RegistrazioneDaoMemory() {}

    @Override
    public List<String> loadAll(String type) {
        if(type.equalsIgnoreCase("utente")){
            return UtentiUsername;
        } else {
            return BibliotecaUsername;
        }
    }

    @Override
    public void store(String type, String username) {
        if(type.equalsIgnoreCase("utente")){
            UtentiUsername.add(username);
        } else {
            BibliotecaUsername.add(username);
        }
    }
}
