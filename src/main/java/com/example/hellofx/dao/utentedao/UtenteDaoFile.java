package com.example.hellofx.dao.utentedao;

import com.example.hellofx.model.Utente;

import java.io.*;
import java.util.*;

public class UtenteDaoFile implements UtenteDao {

    private static final String FILE_PATH = "utenti.dat"; // Percorso del file di persistenza

    @Override
    public List<Utente> loadAllUtenti() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return null; // Se il file non esiste, restituisce null

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Utente>) ois.readObject(); // Restituisce la lista di utenti
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Salva tutti gli utenti nel file
    private void saveAll(List<Utente> utenti) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(utenti); // Scrive o sovrascrive la lista di utenti nel file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Utente loadUtente(String username) {
        List<Utente> utenti = loadAllUtenti();
        for (Utente u : utenti) {
            if (u.getUsername().equals(username)) return u;

        }
        return null;
    }

    @Override
    // Salva un nuovo utente
    public void storeUtente(Utente utente) {
        List<Utente> utenti = loadAllUtenti();
        utenti.add(utente);
        saveAll(utenti);
    }

    @Override
    // Aggiorna un utente già esistente
    public void updateUtente(Utente updatedUtente) {
        List<Utente> utenti = loadAllUtenti();

        Utente u = loadUtente(updatedUtente.getUsername());
        if (u != null) {
            // Rimuove l'utente esistente
            utenti.remove(u);
            // Aggiunge l'utente aggiornato
            utenti.add(updatedUtente);
            saveAll(utenti);
        } else {
            System.out.println("Utente con username " + updatedUtente.getUsername() + " non trovato.");
        }
    }

    @Override
    public void deleteUtente(String username) {
        List<Utente> utenti = loadAllUtenti();
        for(Utente u : utenti) {
            if (u.getUsername().equals(username)) {
                utenti.remove(u);
                break;
            }

        }
        saveAll(utenti);
    }
}

