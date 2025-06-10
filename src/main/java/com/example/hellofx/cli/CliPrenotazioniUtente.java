package com.example.hellofx.cli;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PrenotazioniUtenteController;
import com.example.hellofx.controllerfactory.PrenotazioniUtenteControllerFactory;

import java.util.List;
import java.util.Scanner;

public class CliPrenotazioniUtente {
    private Scanner scanner;
    private final PrenotazioniUtenteController controller = PrenotazioniUtenteControllerFactory.getInstance().createPrenotazioniUtenteController();

    public CliPrenotazioniUtente(Scanner s) {
        scanner = s;
    }

    public void start() {
        System.out.println("\n=== PRENOTAZIONI ATTIVE ===");
        mostraPrenotazioni();
    }

    private void mostraPrenotazioni() {
        List<PrenotazioneBean> prenotazioni = controller.retrievePrenotazioni();

        if (prenotazioni.isEmpty()) {
            System.out.println("\nNon hai prenotazioni attive!");
            return;
        }

        stampaTabellaPrenotazioni(prenotazioni);
        gestisciInterazioneUtente(prenotazioni);
    }

    private void stampaTabellaPrenotazioni(List<PrenotazioneBean> prenotazioni) {
        System.out.println("\n+----+--------------------------------+----------------------+---------------------------+----------------------+---------------+------------+");
        System.out.println("| #  | Titolo                         | Autore               | Biblioteca                | Editore              | ISBN          | Scadenza   |");
        System.out.println("+----+--------------------------------+----------------------+---------------------------+----------------------+---------------+------------+");

        for (int i = 0; i < prenotazioni.size(); i++) {
            PrenotazioneBean pb = prenotazioni.get(i);
            System.out.printf("| %-2d | %-30s | %-20s | %-25s | %-20s | %-13s | %-10s |%n",
                    i + 1,
                    truncate(pb.getTitolo(), 30),
                    truncate(pb.getAutore(), 20),
                    truncate(pb.getBiblioteca(), 25),
                    truncate(pb.getEditore(), 20),
                    pb.getIsbn(),
                    pb.getDataScadenza());
        }
        System.out.println("+----+--------------------------------+----------------------+---------------------------+----------------------+---------------+------------+");
    }

    private void gestisciInterazioneUtente(List<PrenotazioneBean> prenotazioni) {
        while (true) {
            System.out.println("\n1. Elimina una prenotazione");
            System.out.println("2. Torna indietro");
            System.out.print("Scelta: ");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    eliminaPrenotazione(prenotazioni);
                    return;
                case 2:
                    return;
                default:
                    System.out.println("Opzione non valida!");
            }
        }
    }

    private void eliminaPrenotazione(List<PrenotazioneBean> prenotazioni) {

            System.out.print("\nInserisci il numero della prenotazione da eliminare (0 per tornare indietro): ");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            if (scelta > 0 && scelta <= prenotazioni.size()) {
                PrenotazioneBean pb = prenotazioni.get(scelta - 1);
                controller.delete(pb);
                System.out.println("\nPrenotazione eliminata con successo!");
            } else {
                System.out.println("Opzione non valida!");
            }
    }

    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }

}