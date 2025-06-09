package com.example.hellofx.dao.prenotazionedao;

import com.example.hellofx.model.Prenotazione;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrenotazioneDaoFile implements PrenotazioneDao{

    private static final String FILE_PATH = "prenotazioni.dat";

    public Prenotazione loadOne(String idPrenotazione) {
        List<Prenotazione> prenotazioni = loadAll();
        for (Prenotazione p : prenotazioni) {
            if (matchesId(p, idPrenotazione)) {
                return p;
            }
        }
        return null;
    }

    // Carica tutte le prenotazioni attive di un utente
    public List<Prenotazione> loadAllUtente(String username) {
        List<Prenotazione> result = new ArrayList<>();
        for (Prenotazione p : loadAll()) {
            if (p.getDatiUtente()[0].equals(username)) {
                result.add(p);
            }
        }
        return result;
    }

    // Carica tutte le prenotazioni attive di una biblioteca
    public List<Prenotazione> loadAllBiblioteca(String bibliotecaId) {
        List<Prenotazione> result = new ArrayList<>();
        for (Prenotazione p : loadAll()) {
            if (p.getIdBiblioteca().equals(bibliotecaId)) {
                result.add(p);
            }
        }
        return result;
    }

    //salva una nuova prenotazione
    public void store(Prenotazione p) {
        List<Prenotazione> prenotazioni = loadAll();
        prenotazioni.add(p);
        saveAll(prenotazioni);
    }

    public void delete(String idPrenotazione) {
        List<Prenotazione> prenotazioni = loadAll();
        prenotazioni.removeIf(p -> matchesId(p, idPrenotazione));
        saveAll(prenotazioni);
    }

    private List<Prenotazione> loadAll() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Prenotazione>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger logger = Logger.getLogger(PrenotazioneDaoFile.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal file " + FILE_PATH);
            return new ArrayList<>();
        }
    }

    private void saveAll(List<Prenotazione> prenotazioni) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(prenotazioni);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(PrenotazioneDaoFile.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il salvataggio nel file " + FILE_PATH);
        }
    }

    private boolean matchesId(Prenotazione p, String idPrenotazione) {
        return p.getDatiUtente()[0].equals(idPrenotazione.split("/")[0]) &&
                p.getIdBiblioteca().equals(idPrenotazione.split("/")[1]) &&
                p.getIsbn().equals(idPrenotazione.split("/")[2]);
    }
}