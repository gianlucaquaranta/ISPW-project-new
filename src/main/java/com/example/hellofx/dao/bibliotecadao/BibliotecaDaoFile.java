package com.example.hellofx.dao.bibliotecadao;

import com.example.hellofx.model.Biblioteca;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaDaoFile implements BibliotecaDao {

    private static final String FILE_PATH = "biblioteche.dat"; // Percorso del file di persistenza

        @Override
        public List<Biblioteca> loadAll() {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>(); // Se il file non esiste, restituisce una lista vuota

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (List<Biblioteca>) ois.readObject(); // Legge e restituisce la lista di biblioteche
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return new ArrayList<>(); // In caso di errore, restituisce una lista vuota
            }
        }


    // Metodo privato per salvare tutte le biblioteche nel file
        private void saveAll(List<Biblioteca> biblioteche) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                oos.writeObject(biblioteche); // Scrive la lista di biblioteche nel file
            } catch (IOException e) {
                e.printStackTrace(); // Gestisce eventuali errori durante la scrittura
            }
        }

        @Override
        // Caricare una singola biblioteca tramite ID
        public Biblioteca loadOne(String id) {
            return loadAll().stream()
                    .filter(b -> b.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }

        // Salva una nuova biblioteca
        private void store(Biblioteca biblioteca) {
            List<Biblioteca> biblioteche = loadAll();
            biblioteche.add(biblioteca);
            saveAll(biblioteche);
        }

        @Override
        // Aggiorna una biblioteca già esistente
        public void update(Biblioteca updatedBiblioteca) {
            List<Biblioteca> biblioteche = loadAll();
            Biblioteca b = loadOne(updatedBiblioteca.getId());
            if (b != null) {
                // Rimuove biblioteca esistente
                biblioteche.remove(b);
                // Aggiunge la biblioteca aggiornata
                biblioteche.add(b);
                saveAll(biblioteche);
            } else {
                this.store(updatedBiblioteca);
            }
        }


}
