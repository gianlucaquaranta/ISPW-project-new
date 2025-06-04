package com.example.hellofx.cli;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PrenotazioniBibController;
import com.example.hellofx.controllerfactory.PrenotazioniBibControllerFactory;
import java.util.List;
import java.util.Scanner;

public class CliPrenotazioniBibliotecario {
    private Scanner scanner;
    private PrenotazioniBibController prenotazioniController;

    public CliPrenotazioniBibliotecario(Scanner scanner) {
        this.scanner = scanner;
        this.prenotazioniController = PrenotazioniBibControllerFactory.getInstance().createPrenotazioniBibController();
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== GESTIONE PRENOTAZIONI ===");
            System.out.println("1. Visualizza tutte le prenotazioni");
            System.out.println("2. Cerca prenotazioni");
            System.out.println("3. Torna alla home");
            System.out.print("Seleziona un'opzione: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma newline

            switch (choice) {
                case 1:
                    mostraPrenotazioni(prenotazioniController.getPrenotazioni());
                    break;
                case 2:
                    cercaPrenotazioni();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    private void mostraPrenotazioni(List<PrenotazioneBean> prenotazioni) {
        if (prenotazioni.isEmpty()) {
            System.out.println("\nNessuna prenotazione trovata.");
        } else {
            System.out.println("\nELENCO PRENOTAZIONI:");
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.printf("%-8s %-15s %-20s %-15s %-25s %-15s %-15s%n",
                    "ID", "ISBN", "Titolo", "Username", "Email", "Data Inizio", "Scadenza");
            System.out.println("------------------------------------------------------------------------------------------------");

            for (PrenotazioneBean prenotazione : prenotazioni) {
                System.out.printf("%-8s %-15s %-20s %-15s %-25s %-15s %-15s%n",
                        prenotazione.getId(),
                        prenotazione.getIsbn(),
                        abbreviate(prenotazione.getTitolo(), 20),
                        abbreviate(prenotazione.getUtente().getUsername(), 15),
                        abbreviate(prenotazione.getUtente().getEmail(), 25),
                        prenotazione.getDataInizio(),
                        prenotazione.getDataScadenza());
            }

            gestisciSelezionePrenotazione(prenotazioni);
        }

        System.out.println("\nPremi Invio per continuare...");
        scanner.nextLine();
    }

    private void gestisciSelezionePrenotazione(List<PrenotazioneBean> prenotazioni) {
        System.out.print("\nInserisci l'ID della prenotazione da gestire (0 per annullare): ");
        String id = scanner.nextLine();
        scanner.nextLine(); // Consume newline

        if (id.equals("0")) return;

        PrenotazioneBean selected = null;
        for (PrenotazioneBean p : prenotazioni) {
            if (p.getId().equalsIgnoreCase(id)) {
                selected = p;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Prenotazione non trovata!");
            return;
        }

        System.out.println("\nHai selezionato:");
        System.out.println("ID: " + selected.getId());
        System.out.println("Libro: " + selected.getTitolo() + " (ISBN: " + selected.getIsbn() + ")");
        System.out.println("Utente: " + selected.getUtente().getUsername() + " (" + selected.getUtente().getEmail() + ")");
        System.out.println("Data inizio: " + selected.getDataInizio());
        System.out.println("Scadenza: " + selected.getDataScadenza());

        System.out.println("\n1. Trasforma in noleggio");
        System.out.println("2. Annulla operazione");
        System.out.print("Scelta: ");

        int scelta = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (scelta == 1) {
            convertiInNoleggio(selected);
        }
    }

    private void convertiInNoleggio(PrenotazioneBean prenotazione) {
        System.out.println("\nConfermi di voler trasformare questa prenotazione in noleggio?");
        System.out.print("Digita 'SI' per confermare: ");
        String conferma = scanner.nextLine();

        if (conferma.equalsIgnoreCase("SI")) {
            System.out.println("Prenotazione trasformata con successo in noleggio!"); //dummy
        } else {
            System.out.println("Operazione annullata.");
        }
    }

    private void cercaPrenotazioni() {
        System.out.println("\n=== CERCA PRENOTAZIONI ===");
        System.out.println("1. Cerca per ISBN");
        System.out.println("2. Cerca per username");
        System.out.println("3. Cerca per email");
        System.out.println("4. Cerca per titolo libro");
        System.out.print("Seleziona il tipo di ricerca: ");

        int tipoRicerca = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String campo = "";
        switch (tipoRicerca) {
            case 1: campo = "isbn"; break;
            case 2: campo = "username"; break;
            case 3: campo = "email"; break;
            case 4: campo = "titolo"; break;
            default:
                System.out.println("Scelta non valida.");
                return;
        }

        System.out.print("Inserisci il termine di ricerca: ");
        String termine = scanner.nextLine();

        List<PrenotazioneBean> risultati = prenotazioniController.searchByField(termine, campo);
        mostraPrenotazioni(risultati);
    }

    private String abbreviate(String str, int maxLength) {
        if (str == null) return "";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
}
