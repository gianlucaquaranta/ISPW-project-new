package com.example.hellofx.cli;

import java.util.Scanner;

public class CliSchermataInizialeUtente {
    private Scanner scanner;
    private static final String NOT_AVAILABLE = "Funzionalità non ancora disponibile";

    public CliSchermataInizialeUtente(Scanner s) {
        scanner = s;
    }

    public void start() {
        System.out.println("=== BIBLIOTECA DIGITALE ===");
        mostraMenuPrincipale();
    }

    private void mostraMenuPrincipale() {
        while (true) {
            System.out.println("\n--- MENU PRINCIPALE ---");
            System.out.println("1. Home");
            System.out.println("2. Profilo utente");
            System.out.println("3. Prenota libro");
            System.out.println("4. Trova prezzi");
            System.out.println("5. Chiudi l'applicazione");
            System.out.print("Seleziona un'opzione: ");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    avviaCliHome();
                    break;
                case 2:
                    avviaCliProfilo();
                    break;
                case 3:
                    avviaCliPrenotaLibro();
                    break;
                case 4:
                    avviaCliTrovaPrezzi();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opzione non valida! Riprova.");
            }
        }
    }

    private void avviaCliPrenotaLibro() {
        System.out.println("\nCaricamento Prenotazione Libri...");
        CliPrenotaLibro prenotaCli = new CliPrenotaLibro(scanner);
        prenotaCli.start();
    }

    private void avviaCliProfilo() {
        System.out.println("\nCaricamento Profilo...");
        CliProfiloUtente profiloCli = new CliProfiloUtente(scanner);
        profiloCli.start();
    }

    private void avviaCliHome() {
        System.out.println(NOT_AVAILABLE);
    }

    private void avviaCliTrovaPrezzi() {
        System.out.println(NOT_AVAILABLE);
    }

}


