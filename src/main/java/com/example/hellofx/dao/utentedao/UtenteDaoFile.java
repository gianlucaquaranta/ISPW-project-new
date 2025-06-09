package com.example.hellofx.dao.utentedao;

import com.example.hellofx.model.Utente;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteDaoFile implements UtenteDao {

    private static final String FILE_PATH = "utenti.dat"; // Percorso del file di persistenza

    @Override
    public List<Utente> loadAllUtenti() {
        List<Utente> utenti = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return utenti; // Se il file non esiste, restituisce null

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Utente>) ois.readObject(); // Restituisce la lista di utenti
        } catch (IOException | ClassNotFoundException e) {
            Logger logger = Logger.getLogger(UtenteDaoFile.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal file " + FILE_PATH);
            return utenti;
        }
    }


    // Salva tutti gli utenti nel file
    private void saveAll(List<Utente> utenti) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(utenti); // Scrive o sovrascrive la lista di utenti nel file
        } catch (IOException e) {
            Logger logger = Logger.getLogger(UtenteDaoFile.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il salvataggio nel file " + FILE_PATH);        }
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

