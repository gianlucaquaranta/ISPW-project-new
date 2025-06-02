package com.example.hellofx.cli;

import com.example.hellofx.controller.Logout;

import java.util.Scanner;

public class CliProfiloUtente {
    private Scanner scanner;

    public CliProfiloUtente(Scanner s) {
        scanner = s;
    }

    public void start() {
        System.out.println("\n=== PROFILO UTENTE ===");
        mostraMenuProfilo();
    }

    private void mostraMenuProfilo() {
        while (true) {
            System.out.println("\n--- MENU PROFILO ---");
            System.out.println("1. Noleggi attivi");
            System.out.println("2. Prenotazioni attive");
            System.out.println("3. Le mie letture");
            System.out.println("4. Wishlist");
            System.out.println("5. Gestisci il tuo profilo");
            System.out.println("6. Log out");
            System.out.println("7. Torna alla schermata iniziale");
            System.out.print("Seleziona un'opzione: ");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    System.out.println("\nFunzionalità non ancora disponibile: Noleggi attivi");
                    break;
                case 2:
                    avviaCliPrenotazioniUtente();
                    break;
                case 3:
                    System.out.println("\nFunzionalità non ancora disponibile: Le mie letture");
                    break;
                case 4:
                    System.out.println("\nFunzionalità non ancora disponibile: WishList");
                    break;
                case 5:
                    System.out.println("\nFunzionalità non ancora disponibile: Gestisci il tuo profilo");
                    break;
                case 6:
                    Logout logoutController = new Logout();
                    logoutController.logout();
                    System.out.println("\nLogout effettuato con successo!");
                    return; //Gestire cli quando viene effettuato logout per non creare stack overflow
                case 7:
                    return;
                default:
                    System.out.println("\nOpzione non valida! Riprova.");
            }
        }
    }

    private void avviaCliPrenotazioniUtente() {
        System.out.println("\nCaricamento Prenotazioni attive...");
        CliPrenotazioniUtente prenotazioniCli = new CliPrenotazioniUtente(scanner);
        prenotazioniCli.start();
    }
}
