package com.example.hellofx.cli;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.FiltriBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.PLControllerFactory;

import java.util.List;
import java.util.Scanner;

public class CliPrenotaLibro {

    private Scanner scanner;
    private PLController plController = PLControllerFactory.getInstance().createPLController();
    private CliSchermataInizialeUtente csu;
    private List<LibroBean> datiTemp;  // Mantiene lo stato tra le schermate
    static final String SEPARATOR = "+----+--------------------------------+----------------------+----------------------+---------------+";
    static final String CHOICE = "Scelta: ";
    static final String NOT_VALID_OPTION = "Opzione non valida!";

    public CliPrenotaLibro(Scanner s) {
        scanner = s;
    }

    public void start() {
        System.out.println("=== PRENOTAZIONE LIBRO ===");
        mostraMenuRicerca();
    }

    private void mostraMenuRicerca() {
        while (true) {
            System.out.println("\n--- RICERCA LIBRI ---");
            System.out.println("1. Cerca libro");
            System.out.println("2. Rimuovi filtri");
            System.out.println("3. Torna alla schermata iniziale");
            System.out.print(CHOICE);

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    int result = cercaLibri();
                    if(result == 1){
                        break;
                    } else {
                        return;
                    }
                case 2:
                    rimuoviFiltri();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(NOT_VALID_OPTION);
            }
        }
    }

    private int cercaLibri() { //I metodi da cercaLibri() in poi restituiscono 0 solo se la prenotazione è avvenuta, altrimenti se in una "schermata" qualsiasi l'utente vuole tornare indietro o ci sono malfunzionamenti vari, ritornano 1
        System.out.println("\n--- FILTRI DI RICERCA ---");

        // Richiesta input
        String titolo = richiediInput("Titolo (lascia vuoto per ignorare): ");
        String autore = richiediInput("Autore (lascia vuoto per ignorare): ");
        String isbn = richiediInput("ISBN (lascia vuoto per ignorare): ");
        String genere = richiediGenere();
        String biblioteca = richiediInput("Biblioteca (lascia vuoto per ignorare): ");
        String cap = richiediInput("CAP (lascia vuoto per ignorare): ");

        // Creazione bean e chiamata al controller
        FiltriBean filtriBean = new FiltriBean(titolo, autore, genere, isbn, biblioteca, cap);
        List<LibroBean> risultati = plController.filtra(filtriBean);

        // Visualizzazione risultati
        if (risultati.isEmpty()) {
            System.out.println("\nNessun risultato trovato.");
            return 1;
        } else {
            stampaRisultati(risultati);
            return gestisciSelezioneLibro(risultati);
        }
    }

    private String richiediGenere() {

        System.out.println("Generi disponibili:");
        System.out.println("1. Narrativa");
        System.out.println("2. Saggistica");
        System.out.println("3. Fantascienza");
        System.out.println("4. Horror");
        System.out.println("5. Ignora genere");
        System.out.print(CHOICE);

        int scelta = scanner.nextInt();
        scanner.nextLine();

        switch (scelta) {
            case 1: return "Narrativa";
            case 2: return "Saggistica";
            case 3: return "Fantascienza";
            case 4: return "Horror";
            default: return null;
        }
    }

    private void rimuoviFiltri() {
        System.out.println("\nFiltri rimossi correttamente.");
    }

    private String richiediInput(String messaggio) {
        System.out.print(messaggio);
        return scanner.nextLine();
    }

    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }

    private void stampaRisultati(List<LibroBean> libri) {

        System.out.println("\nRISULTATI TROVATI:");
        System.out.println(SEPARATOR);
        System.out.println("| #  | TITOLO                         | AUTORE               | EDITORE              | ISBN          |");
        System.out.println(SEPARATOR);

        for (int i = 0; i < libri.size(); i++) {
            LibroBean libro = libri.get(i);
            System.out.printf("| %-2d | %-30s | %-20s | %-20s | %-13s |\n",
                    i + 1,
                    truncate(libro.getTitolo(), 30),
                    truncate(libro.getAutore(), 20),
                    truncate(libro.getEditore(), 20),
                    libro.getIsbn());
        }
        System.out.println(SEPARATOR);
    }


    private int gestisciSelezioneLibro(List<LibroBean> libri) {
        while(true){
            System.out.print("\nSeleziona un libro (1-" + libri.size() + ") o 0 per annullare: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            if (scelta > 0 && scelta <= libri.size()) {
                LibroBean libroSelezionato = libri.get(scelta - 1);
                datiTemp = libri;// Salva per eventuale ritorno indietro
                List<BibliotecaBean> biblioteche = plController.getBiblioteche(libroSelezionato);
/*
                if (biblioteche.isEmpty()) {
                    System.out.println("\nNessuna biblioteca disponibile per questo libro.");
                    return 1;
                }

 */
                stampaBiblioteche(biblioteche);
                int result = gestisciSelezioneBiblioteca(biblioteche);

                if(result == 0) {
                    return 0;
                }

            } else if(scelta==0) {
                return 1;

            } else {
                System.out.println("Scelta non valida!");
            }
        }
    }


    private int gestisciSelezioneBiblioteca(List<BibliotecaBean> biblioteche) {

        while (true) {
            System.out.println("\n1. Prenota libro");
            System.out.println("2. Torna indietro");
            System.out.print(CHOICE);

            int scelta = scanner.nextInt();
            scanner.nextLine();

            switch (scelta) {
                case 1:
                    int result = prenotaLibro(biblioteche);
                    if(result == 1){
                        break;
                    } else {
                        return 0;
                    }
                case 2:
                    stampaRisultati(datiTemp);
                    return 1;
                default:
                    System.out.println(NOT_VALID_OPTION);
            }
        }
    }


    private void stampaBiblioteche(List<BibliotecaBean> biblioteche) {
        System.out.println("\n--- BIBLIOTECHE DISPONIBILI ---");
        System.out.println(SEPARATOR);
        System.out.println("| #  | NOME                         | INDIRIZZO                   | CIVICO| CITTA     | CAP      | PROVINCIA |");
        System.out.println(SEPARATOR);

        for (int i = 0; i < biblioteche.size(); i++) {
            BibliotecaBean bib = biblioteche.get(i);
            System.out.printf("| %-2d | %-28s | %-27s | %-5s | %-9s | %-8s | %-7s |\n",
                    i + 1,
                    truncate(bib.getNome(), 28),
                    truncate(bib.getIndirizzo(), 27),
                    truncate(bib.getNumeroCivico(), 5),
                    truncate(bib.getCitta(), 9),
                    bib.getCap(),
                    bib.getProvincia());
        }
        System.out.println(SEPARATOR);
    }


    private int prenotaLibro(List<BibliotecaBean> biblioteche) { //Creare confermaPrenotazione() a parte

        System.out.print("\nSeleziona biblioteca (1-" + biblioteche.size() + "): ");
        int scelta = scanner.nextInt();
        scanner.nextLine();

        while(true){
            if (scelta > 0 && scelta <= biblioteche.size()) {
                BibliotecaBean biblioteca = biblioteche.get(scelta - 1);
                PrenotazioneBean pb = plController.creaRiepilogo(biblioteca);

                // Visualizzazione riepilogo
                System.out.println("\n=== RIEPILOGO PRENOTAZIONE ===");
                System.out.println("Titolo: " + pb.getLibro().getTitolo());
                System.out.println("Autore: " + pb.getLibro().getAutore());
                System.out.println("Editore: " + pb.getLibro().getEditore());
                System.out.println("ISBN: " + pb.getLibro().getIsbn());
                System.out.println("\nBiblioteca: " + pb.getBiblioteca().getNome());
                System.out.println("Indirizzo: " + pb.getBiblioteca().getIndirizzoCompleto());
                System.out.println("\nData inizio: " + pb.getDataInizioS());
                System.out.println("Data scadenza: " + pb.getDataScadenzaS());

                int result = confermaPrenotazione();

                if (result == 1) {
                    stampaBiblioteche(biblioteche);
                    return 1;
                } else {
                    return 0;
                }

            } else {
                System.out.println(NOT_VALID_OPTION);
            }
        }
    }


    private int confermaPrenotazione() {
        while (true) {
            System.out.println("\n1. Conferma prenotazione");
            System.out.println("2. Annulla");
            System.out.print(CHOICE);

            int opzione = scanner.nextInt();
            scanner.nextLine();

            switch (opzione) {
                case 1:
                    boolean success = plController.registraPrenotazione();
                    if (success) {
                        System.out.println("\nPRENOTAZIONE REGISTRATA CON SUCCESSO!");
                        return 0;
                    } else {
                        System.out.println("\nERRORE durante la registrazione!");
                        break;
                    }
                case 2:
                    System.out.println("Prenotazione annullata.");
                    return 1;
                default:
                    System.out.println(NOT_VALID_OPTION);
                    break;
            }
        }
    }

}