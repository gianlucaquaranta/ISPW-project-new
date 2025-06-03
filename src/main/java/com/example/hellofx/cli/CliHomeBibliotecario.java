package com.example.hellofx.cli;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controller.Logout;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import java.util.List;
import java.util.Scanner;

public class CliHomeBibliotecario {
    private Scanner scanner;
    private boolean running;

    public CliHomeBibliotecario(Scanner scanner) {
        this.scanner = scanner;
        this.running = true;
    }

    public void start() {
        System.out.println("=== Biblioteca System ===");

        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            handleChoice(choice);
        }

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== Home - La tua Biblioteca ===");
        System.out.println("1. Visualizza Catalogo");
        System.out.println("2. Visualizza Registro Noleggi");
        System.out.println("3. Visualizza Prenotazioni");
        System.out.println("4. Logout");
        System.out.print("Seleziona un'opzione: ");
    }

    private int getMenuChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Input non valido. Inserisci un numero.");
            scanner.next(); // discard non-integer input
        }
        return scanner.nextInt();
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                visualizzaCatalogo();
                askForModification();
                break;
            case 2:
                visualizzaRegistroNoleggi();
                break;
            case 3:
                visualizzaPrenotazioni();
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
        }
    }

    private void visualizzaCatalogo() {
        System.out.println("\n=== Visualizzazione Catalogo ===");

        try {
            // Recupera il catalogo
            AggiornaCatController aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();
            List<LibroBean> catalogo = aggiornaCatController.getCatalogo();

            // Display catalog COME IN climodcat
            if (catalogo.isEmpty()) {
                System.out.println("Il catalogo è vuoto.");
            } else {
                System.out.println("Libri nel catalogo:");
                for (LibroBean libro : catalogo) {
                    System.out.println("- " + libro.getTitolo() + " di " + libro.getAutore() +
                            " (ISBN: " + libro.getIsbn() + ")");
                }
            }

        } catch (Exception e) {
            System.out.println("Errore durante il recupero del catalogo: " + e.getMessage());
        }
    }

    private void askForModification() {
        System.out.println("\nCosa vuoi fare?");
        System.out.println("1. Modifica catalogo");
        System.out.println("2. Torna al menu principale");
        System.out.print("Scelta: ");

        int choice = getMenuChoice();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            CliModificaCatalogo modificaCLI = new CliModificaCatalogo(this.scanner);
            modificaCLI.start();
        } else if (choice != 2) {
            System.out.println("Scelta non valida. Torno al menu principale.");
        }
    }

    private void visualizzaRegistroNoleggi() {
        System.out.println("\n=== Registro Noleggi ===");

        System.out.println("Funzionalità di visualizzazione noleggi non ancora implementata.");

        System.out.println("\nPremi Invio per continuare...");
        scanner.nextLine(); // consume newline
        scanner.nextLine(); // wait for enter
    }

    private void visualizzaPrenotazioni() {
        System.out.println("\n=== Prenotazioni ===");

        System.out.println("Funzionalità di visualizzazione prenotazioni non ancora implementata.");

        System.out.println("\nPremi Invio per continuare...");
        scanner.nextLine(); // consume newline
        scanner.nextLine(); // wait for enter
    }

    private void logout() {
        System.out.println("\nEffettuando il logout...");
        new Logout().logout();
        this.running = false;
        System.out.println("Logout completato. Arrivederci!");
    }
}