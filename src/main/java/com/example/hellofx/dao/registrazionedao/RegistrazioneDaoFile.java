package com.example.hellofx.dao.registrazionedao;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RegistrazioneDaoFile implements RegistrazioneDao {

    // Percorsi fissi ai file
    private static final String FILE_PATH_UTENTI = "utenti.txt";
    private static final String FILE_PATH_BIBLIOTECARI = "bibliotecari.txt";

    /**
     * Restituisce la lista degli username dal file corrispondente al tipo.
     * @param type "utente" oppure "bib"
     * @return lista di username
     */

    @Override
    public List<String> loadAll(String type) {
        String filePath = getFilePathFromType(type);
        List<String> usernames = new ArrayList<>();

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path); // crea il file se non esiste
            }

            usernames = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usernames;
    }

    /**
     * Scrive un nuovo username nel file corrispondente al tipo.
     * @param type "utente" oppure "bib"
     * @param username username da scrivere
     * @return true se scritto con successo
     */

    @Override
    public void store(String type, String username) {
        String filePath = getFilePathFromType(type);

        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createFile(path); // crea il file se non esiste
            }

            Files.write(path, (username + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mappa il tipo al file corrispondente.
     * @param type "utente" o "bib"
     * @return percorso file
     */
    private String getFilePathFromType(String type) {
        return switch (type.toLowerCase()) {
            case "utente" -> FILE_PATH_UTENTI;
            case "bib" -> FILE_PATH_BIBLIOTECARI;
            default -> throw new IllegalArgumentException("Tipo non valido: " + type);
        };
    }
}