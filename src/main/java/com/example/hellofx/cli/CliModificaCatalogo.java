package com.example.hellofx.cli;

import com.example.hellofx.bean.GenereBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;

import java.util.List;
import java.util.Scanner;

public class CliModificaCatalogo {
    private Scanner scanner;
    private AggiornaCatController aggiornaCatController;
    static final String CONTINUE = "Premi Invio per continuare...";
    static final String SEPARATOR = "------------------------------------------------------------------------------------------------";

    public CliModificaCatalogo(Scanner scanner) {
        this.scanner = scanner;
        this.aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== MODIFICA CATALOGO ===");
            System.out.println("1. Visualizza catalogo");
            System.out.println("2. Aggiungi libro");
            System.out.println("3. Modifica libro");
            System.out.println("4. Elimina libro");
            System.out.println("5. Cerca libro");
            System.out.println("6. Salva modifiche");
            System.out.println("7. Torna alla home");
            System.out.print("Seleziona un'opzione: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    mostraCatalogoModificabile();
                    break;
                case 2:
                    aggiungiLibro();
                    break;
                case 3:
                    modificaLibro();
                    break;
                case 4:
                    eliminaLibro();
                    break;
                case 5:
                    cercaLibro();
                    break;
                case 6:
                    salvaModifiche();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void mostraCatalogoModificabile() {
        List<LibroBean> catalogo = aggiornaCatController.getCatalogo();

        if (catalogo.isEmpty()) {
            System.out.println("\nIl catalogo è vuoto.");
        } else {
            System.out.println("\nCATALOGO LIBRI:");
            System.out.println(SEPARATOR);
            System.out.printf("%-15s %-20s %-20s %-15s %-10s %-10s %-15s %-15s%n",
                    "ISBN", "Titolo", "Autore", "Editore", "Anno", "Genere", "Copie Tot", "Copie Disp");
            System.out.println(SEPARATOR);

            for (LibroBean libro : catalogo) {
                System.out.printf("%-15s %-20s %-20s %-15s %-10s %-10s %-10d %-10d%n",
                        libro.getIsbn(),
                        abbreviate(libro.getTitolo(), 20),
                        abbreviate(libro.getAutore(), 20),
                        abbreviate(libro.getEditore(), 15),
                        libro.getAnnoPubblicazione(),
                        abbreviate(libro.getGenere().getNome(), 10),
                        libro.getNumCopie()[0],
                        libro.getNumCopie()[1]);
            }
        }

        System.out.println("\n" + CONTINUE);
        scanner.nextLine();
    }

    private void aggiungiLibro() throws Exception {
        LibroBean libroBean = new LibroBean();
        System.out.println("\n=== AGGIUNGI NUOVO LIBRO ===");

        System.out.print("ISBN: ");
        libroBean.setIsbn(scanner.nextLine());

        System.out.print("Titolo: ");
        libroBean.setTitolo(scanner.nextLine());

        System.out.print("Autore: ");
        libroBean.setAutore(scanner.nextLine());

        System.out.print("Editore: ");
        libroBean.setEditore(scanner.nextLine());

        System.out.print("Anno di pubblicazione: ");
        libroBean.setAnnoPubblicazione(Integer.parseInt(scanner.nextLine()));

        System.out.print("Genere: ");
        libroBean.setGenere(GenereBean.fromString(scanner.nextLine()));

        System.out.print("Numero di copie: ");
        int copie = scanner.nextInt();
        Integer[] numCopie = {copie, copie};
        libroBean.setNumCopie(numCopie);
        scanner.nextLine(); // Consume newline

        aggiornaCatController.add(libroBean);

        System.out.println("\nLibro aggiunto con successo!");
        System.out.println(CONTINUE);
        scanner.nextLine();
    }

    private void modificaLibro() {
        System.out.println("\n=== MODIFICA LIBRO ===");
        mostraCatalogoModificabile();

        System.out.print("\nInserisci l'ISBN del libro da modificare: ");
        String isbn = scanner.nextLine();

        List<LibroBean> listLibri = aggiornaCatController.searchByField(isbn,"isbn"); //l'isbn è univoco: searchByField ritornerà una lista con 0 o 1 elemento
        if (listLibri.isEmpty()) {
            System.out.println("Libro non trovato!");
            return;
        }

        LibroBean libro = listLibri.getFirst();
        System.out.println("\nModifica i campi (lascia vuoto per mantenere il valore corrente):");

        System.out.print("Titolo [" + libro.getTitolo() + "]: ");
        String titolo = scanner.nextLine();
        if (!titolo.isEmpty()) libro.setTitolo(titolo);

        System.out.print("Autore [" + libro.getAutore() + "]: ");
        String autore = scanner.nextLine();
        if (!autore.isEmpty()) libro.setAutore(autore);

        System.out.print("Editore [" + libro.getEditore() + "]: ");
        String editore = scanner.nextLine();
        if (!editore.isEmpty()) libro.setEditore(editore);

        System.out.print("Anno di pubblicazione [" + libro.getAnnoPubblicazione() + "]: ");
        String anno = scanner.nextLine();
        if (!anno.isEmpty()) libro.setAnnoPubblicazione(Integer.parseInt(anno));

        System.out.print("Genere [" + libro.getGenere() + "] ");
        GenereBean genereBean = richiediGenere();
        if (genereBean!= null) libro.setGenere(genereBean);

        System.out.print("Numero di copie [" + libro.getNumCopie()[0] + "]: ");
        String copieInput = scanner.nextLine();
        if (!copieInput.isEmpty()) {
            int copie = Integer.parseInt(copieInput);
            Integer[] numCopie = {copie, libro.getNumCopie()[1]};
            libro.setNumCopie(numCopie);
        }

        System.out.println("\nLibro modificato con successo!");
        System.out.println(CONTINUE);
        scanner.nextLine();
    }

    private GenereBean richiediGenere() {

        System.out.println("Generi disponibili:");
        // Stampa elenco puntato
        GenereBean[] generi = GenereBean.values();
        System.out.println("0. Non modificare il genere");
        for (int i = 0; i < generi.length; i++) {
            System.out.println((i + 1) + ". " + generi[i].getNome());
        }

        int scelta = scanner.nextInt();
        scanner.nextLine();

        // Validazione input
        if (scelta < 1 || scelta > generi.length) {
            System.out.println("Indice non valido.");
            return null;
        }

        return generi[scelta - 1];
    }

    private void eliminaLibro() {
        System.out.println("\n=== ELIMINA LIBRO ===");
        mostraCatalogoModificabile();

        System.out.print("\nInserisci l'ISBN del libro da eliminare: ");
        String isbn = scanner.nextLine();

        List<LibroBean> listLibri = aggiornaCatController.searchByField(isbn,"isbn"); //l'isbn è univoco: searchByField ritornerà una lista con 0 o 1 elemento
        if (listLibri.isEmpty()) {
            System.out.println("Libro non trovato!");
            return;
        }
        LibroBean libro = listLibri.getFirst();

        System.out.println("\nSei sicuro di voler eliminare il libro:");
        System.out.println("Titolo: " + libro.getTitolo());
        System.out.println("Autore: " + libro.getAutore());
        System.out.print("Confermi? (s/n): ");

        String conferma = scanner.nextLine();
        if (conferma.equalsIgnoreCase("s")) {
            aggiornaCatController.delete(libro);
            System.out.println("Libro eliminato con successo!");
        } else {
            System.out.println("Operazione annullata.");
        }

        System.out.println(CONTINUE);
        scanner.nextLine();
    }

    private void cercaLibro() {
        System.out.println("\n=== CERCA LIBRO ===");
        System.out.println("1. Cerca per titolo");
        System.out.println("2. Cerca per autore");
        System.out.println("3. Cerca per ISBN");
        System.out.println("4. Cerca per genere");
        System.out.print("Seleziona il tipo di ricerca: ");

        int tipoRicerca = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String campo = "";
        switch (tipoRicerca) {
            case 1:
                campo = "titolo";
                break;
            case 2:
                campo = "autore";
                break;
            case 3:
                campo = "isbn";
                break;
            case 4:
                campo = "genere";
                break;
            default:
                System.out.println("Scelta non valida.");
                return;
        }

        System.out.print("Inserisci il termine di ricerca: ");
        String termine = scanner.nextLine();

        List<LibroBean> risultati = aggiornaCatController.searchByField(termine, campo);

        if (risultati.isEmpty()) {
            System.out.println("\nNessun risultato trovato.");
        } else {
            System.out.println("\nRISULTATI RICERCA:");
            System.out.println(SEPARATOR);
            System.out.printf("%-15s %-20s %-20s %-15s %-10s %-10s %-15s %-15s%n",
                    "ISBN", "Titolo", "Autore", "Editore", "Anno", "Genere", "Copie Tot", "Copie Disp");
            System.out.println(SEPARATOR);

            for (LibroBean libro : risultati) {
                System.out.printf("%-15s %-20s %-20s %-15s %-10s %-10s %-10d %-10d%n",
                        libro.getIsbn(),
                        abbreviate(libro.getTitolo(), 20),
                        abbreviate(libro.getAutore(), 20),
                        abbreviate(libro.getEditore(), 15),
                        libro.getAnnoPubblicazione(),
                        abbreviate(libro.getGenere().getNome(), 10),
                        libro.getNumCopie()[0],
                        libro.getNumCopie()[1]);
            }
        }

        System.out.println("\n" + CONTINUE);
        scanner.nextLine();
    }

    private void salvaModifiche() {
        aggiornaCatController.save();
        System.out.println("\nModifiche salvate con successo!");
        System.out.println(CONTINUE);
        scanner.nextLine();
    }

    private String abbreviate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}