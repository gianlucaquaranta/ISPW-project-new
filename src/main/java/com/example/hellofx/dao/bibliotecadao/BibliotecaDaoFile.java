package com.example.hellofx.dao.bibliotecadao;

import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Posizione;
import com.example.hellofx.model.modelfactory.BibliotecaFactory;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BibliotecaDaoFile implements BibliotecaDao {

    private final File file = new File("Biblioteche.txt");

    public Biblioteca loadOne(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 8);
                if (parts[0].equals(id)) {
                    return parseLine(parts);
                }
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(BibliotecaDaoFile.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal file Biblioteche.txt");
        }
        return null;
    }


    public List<Biblioteca> loadAll() {
        List<Biblioteca> biblioteche = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 8);
                Biblioteca b = parseLine(parts);
                biblioteche.add(b);

            }
        } catch (IOException e) {
        Logger logger = Logger.getLogger(BibliotecaDaoFile.class.getName());
        logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal file Biblioteche.txt");
        }
        return biblioteche;
    }

    public void update(Biblioteca b) {
        List<Biblioteca> biblioteche = loadAll();
        boolean updated = false;

        // Cerca e aggiorna
        for (int i = 0; i < biblioteche.size(); i++) {
            if (biblioteche.get(i).getId().equals(b.getId())) {
                biblioteche.set(i, b);
                updated = true;
                break;
            }
        }

        // Se non esiste, aggiungi
        if (!updated) {
            biblioteche.add(b);
        }

        // Riscrivi tutto il file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Biblioteca bib : biblioteche) {
                writer.write(formatBiblioteca(bib));
                writer.newLine();
            }
        } catch (IOException e) {
            Logger logger = Logger.getLogger(BibliotecaDaoFile.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore l'update del file Biblioteche.txt");
        }
    }

    private Biblioteca parseLine(String[] parts) {

        Biblioteca b = BibliotecaFactory.getInstance().createBiblioteca();
        b.setId(parts[0]);
        b.setNome(parts[1]);
        Posizione posizione = new Posizione(parts[2], parts[3], parts[4], parts[5], parts[6]);
        b.setPosizione(posizione);

        Map<String, Integer[]> copie = parseCopie(parts[7]);
        b.setCopie(copie);

        return b;
    }

    private Map<String, Integer[]> parseCopie(String copieString) {
        Map<String, Integer[]> copie = new HashMap<>();
        if (copieString == null || copieString.trim().isEmpty()) {
            return copie;
        }
        // Esempio: 9781234567890:5,3;9780987654321:10,7
        String[] entries = copieString.split(";");
        for (String entry : entries) {
            String[] keyValue = entry.split(":");

            String isbn = keyValue[0];
            String[] counts = keyValue[1].split(",");

            try {
                int totali = Integer.parseInt(counts[0]);
                int disponibili = Integer.parseInt(counts[1]);
                copie.put(isbn, new Integer[]{totali, disponibili});
            } catch (NumberFormatException e){
                Logger logger = Logger.getLogger(BibliotecaDaoFile.class.getName());
                logger.log(Level.SEVERE, e, () -> "Errore di formato");
            }
        }
        return copie;
    }

    private String formatBiblioteca(Biblioteca b) {
        StringBuilder sb = new StringBuilder();
        sb.append(b.getId()).append(",");
        sb.append(b.getNome()).append(",");
        Posizione p = b.getPosizione();
        sb.append(p.getCap()).append(",");
        sb.append(p.getIndirizzo()).append(",");
        sb.append(p.getNumeroCivico()).append(",");
        sb.append(p.getCitta()).append(",");
        sb.append(p.getProvincia()).append(",");

        // format copie
        Map<String, Integer[]> copie = b.getCopie();
        List<String> entries = new ArrayList<>();
        for (Map.Entry<String, Integer[]> entry : copie.entrySet()) {
            String isbn = entry.getKey();
            Integer[] counts = entry.getValue();
            entries.add(isbn + ":" + counts[0] + "," + counts[1]);
        }
        sb.append(String.join(";", entries));

        return sb.toString();
    }
}
