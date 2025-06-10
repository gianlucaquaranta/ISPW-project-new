package com.example.hellofx.cli;

import com.example.hellofx.bean.*;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.PLControllerFactory;
import com.example.hellofx.exception.EmptyFiltersException;
import com.example.hellofx.exception.PrenotazioneGiaPresenteException;
import com.example.hellofx.exception.UserNotLoggedException;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CliPrenotaLibro {

    private Scanner scanner;
    private PLController plController;
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
            System.out.println("2. Torna alla schermata iniziale");
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
        FiltriBean filtriBean;
        try {
            filtriBean = new FiltriBean(titolo, autore, genere, isbn, biblioteca, cap);
        } catch(IllegalArgumentException e) {
            showLogger(e.getMessage());
            return 1;
        }

        List<LibroBean> risultati;
        plController = PLControllerFactory.getInstance().createPLController();
        try {
            risultati = plController.filtra(filtriBean);
        } catch(EmptyFiltersException e){
            showLogger(e.getMessage());
            return 1;
        }
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
        // Stampa elenco puntato
        GenereBean[] generi = GenereBean.values();
        System.out.println("0 per ignorare");
        for (int i = 0; i < generi.length; i++) {
            System.out.println((i + 1) + ". " + generi[i].getNome());
        }

        int scelta = scanner.nextInt();
        scanner.nextLine();

        // Validazione input
        if (scelta > generi.length) {
            System.out.println("Indice non valido.");
            return null;
        } else if(scelta==0){
            return "";
        }

        return generi[scelta - 1].getNome();
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
            System.out.printf("| %-2d | %-30s | %-20s | %-20s | %-13s |%n",
                    i + 1,
                    truncate(libro.getTitolo(), 30),
                    truncate(libro.getAutore(), 20),
                    truncate(libro.getEditore(), 20),
                    libro.getIsbn());
        }
        System.out.println(SEPARATOR);
    }


    private int gestisciSelezioneLibro(List<LibroBean> libri) {
        List<BibliotecaBean> biblioteche;
        while(true){
            System.out.print("\nSeleziona un libro (1-" + libri.size() + ") o 0 per annullare: ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            if (scelta > 0 && scelta <= libri.size()) {
                LibroBean libroSelezionato = libri.get(scelta - 1);
                datiTemp = libri; // Salva per eventuale ritorno indietro
                biblioteche = plController.getBiblioteche(libroSelezionato);

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
                    int result = visualizzaRiepilogo(biblioteche);
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
            System.out.printf("| %-2d | %-28s | %-27s | %-5s | %-9s | %-8s | %-7s |%n",
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


    private int visualizzaRiepilogo(List<BibliotecaBean> biblioteche) {

        System.out.print("\nSeleziona biblioteca (1-" + biblioteche.size() + "): ");
        int scelta;

        while(true){
            scelta = scanner.nextInt();
            scanner.nextLine();

            if (scelta > 0 && scelta <= biblioteche.size()) {
                BibliotecaBean biblioteca = biblioteche.get(scelta - 1);
                PrenotazioneBean pb = plController.creaRiepilogo(biblioteca);

                // Visualizzazione riepilogo
                System.out.println("\n=== RIEPILOGO PRENOTAZIONE ===");
                System.out.println("Titolo: " + pb.getLibro().getTitolo());
                System.out.println("Autore: " + pb.getLibro().getAutore());
                System.out.println("Editore: " + pb.getLibro().getEditore());
                System.out.println("ISBN: " + pb.getLibro().getIsbn());
                System.out.println("\nBiblioteca: " + pb.getBibliotecaB().getNome());
                System.out.println("Indirizzo: " + pb.getBibliotecaB().getIndirizzoCompleto());
                System.out.println("\nData inizio: " + pb.getDataInizio());
                System.out.println("Data scadenza: " + pb.getDataScadenza());

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
                    try {
                        plController.registraPrenotazione();
                    } catch (UserNotLoggedException e) {
                        showLogger(e.getMessage());
                        CliLogin cliLogin = new CliLogin(scanner);
                        cliLogin.start(plController);
                        return 0;
                    } catch (PrenotazioneGiaPresenteException e){
                        showLogger(e.getMessage());
                        return 1;
                    }
                    System.out.println("\nPRENOTAZIONE REGISTRATA CON SUCCESSO!");
                    return 0;
                case 2:
                    System.out.println("Prenotazione annullata.");
                    return 1;
                default:
                    System.out.println(NOT_VALID_OPTION);
                    break;
            }
        }
    }

    private void showLogger(String message) {
        Logger logger = Logger.getLogger(CliPrenotaLibro.class.getName());
        logger.log(Level.SEVERE, message);
    }

}