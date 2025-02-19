package com.example.hellofx.dao.utentedao;

import com.example.hellofx.entity.Utente;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class UtenteDaoFile implements UtenteDao {
    private UtenteDaoMemory utenteDaoMemory = UtenteDaoMemory.getInstance();
    private static final String FILE_PATH = "utenti.txt";  // File dove salviamo gli utenti


    @Override
    public Utente loadUtente(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(username)) {
                    String password = reader.readLine();  // La password è nella riga successiva
                    String isbnLibriString = reader.readLine();  // Gli ISBN sono nella riga successiva
                    List<String> isbnLibri = List.of(isbnLibriString.split(","));
                    reader.readLine();  // Salta il separatore
                    return new Utente(username, password, );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;  // Se non viene trovato l'utente, restituisci null
    }

    @Override
    public List<Utente> loadAllUtenti() {
        List<Utente> utenti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String username = line;
                String password = reader.readLine();
                String isbnLibriString = reader.readLine();
                List<String> isbnLibri = List.of(isbnLibriString.split(","));
                reader.readLine();
                utenti.add(new Utente(username, password, isbnLibri));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return utenti;
    }

    @Override
    public void storeUtente(Utente utente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Scrivi username, password e ISBN su file
            writer.write(utente.getUsername());
            writer.newLine();  // Vai a capo
            writer.write(utente.getPassword());
            writer.newLine();  // Vai a capo
            writer.write(String.join(",", utente.getIsbnLibri()));  // Unisci la lista ISBN separata da virgole
            writer.newLine();  // Vai a capo
            writer.write("---");
            writer.newLine();  // Vai a capo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUtente(Utente utente) {
        List<Utente> utenti = loadAllUtenti();
        boolean trovato = false;

        for (int i = 0; i < utenti.size(); i++) {
            if (utenti.get(i).getUsername().equals(utente.getUsername())) {
                utenti.set(i, utente);  // Sostituisci l'utente
                trovato = true;
                break;
            }
        }

        if (trovato) {
            // Riscrivi tutti gli utenti nel file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Utente u : utenti) {
                    writer.write(u.getUsername());
                    writer.newLine();
                    writer.write(u.getPassword());
                    writer.newLine();
                    writer.write(String.join(",", u.getIsbnLibri()));
                    writer.newLine();
                    writer.write("---");
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Utente non trovato per l'aggiornamento.");
        }
    }

    @Override
    public void deleteUtente(String username) {
        List<Utente> utenti = loadAllUtenti();
        utenti = utenti.stream()
                .filter(utente -> !utente.getUsername().equals(username))  // Filtra l'utente da eliminare
                .collect(Collectors.toList());

        // Riscrivi tutti gli utenti nel file, tranne quello da eliminare
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Utente u : utenti) {
                writer.write(u.getUsername());
                writer.newLine();
                writer.write(u.getPassword());
                writer.newLine();
                writer.write(String.join(",", u.getIsbnLibri()));
                writer.newLine();
                writer.write("---");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
