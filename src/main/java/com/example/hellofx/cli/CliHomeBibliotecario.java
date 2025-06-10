package com.example.hellofx.cli;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controller.Logout;
import com.example.hellofx.controller.PrenotazioniBibController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import com.example.hellofx.controllerfactory.PrenotazioniBibControllerFactory;

import java.util.List;
import java.util.Scanner;

public class CliHomeBibliotecario {
    private final Scanner scanner;
    private final AggiornaCatController aggiornaCatController;
    private final PrenotazioniBibController prenotazioniBibController;
    private static final String CONTINUE = "\nPremi INVIO per continuare...";

    public CliHomeBibliotecario(Scanner scanner) {
        this.scanner = scanner;
        this.aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();
        this.prenotazioniBibController = PrenotazioniBibControllerFactory.getInstance().createPrenotazioniBibController();
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENU BIBLIOTECARIO ===");
            System.out.println("1. Visualizza Catalogo");
            System.out.println("2. Visualizza Registro Noleggi");
            System.out.println("3. Visualizza Prenotazioni");
            System.out.println("4. Logout");
            System.out.print("Scelta: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (choice) {
                case 1:
                    visualizzaCatalogo();
                    break;
                case 2:
                    visualizzaRegistroNoleggi();
                    break;
                case 3:
                    visualizzaPrenotazioni();
                    break;
                case 4:
                    running = false;
                    logout();
                    break;
                default:
                    System.out.println("Scelta non valida!");
            }
        }
    }

    private void visualizzaCatalogo() {
        System.out.println("\n=== CATALOGO LIBRI ===");
        List<LibroBean> catalogo = aggiornaCatController.getCatalogo();

        if (catalogo.isEmpty()) {
            System.out.println("Nessun libro presente nel catalogo.");
            return;
        } else {
            for (int i = 0; i < catalogo.size(); i++) {
                LibroBean libro = catalogo.get(i);
                System.out.printf("%d. %s - %s (ISBN: %s)%n",
                        i + 1,
                        libro.getTitolo(),
                        libro.getAutore(),
                        libro.getIsbn());
            }
        }

        System.out.print("Opzioni: ");
        System.out.print("\n1. Modifica catalogo");
        System.out.print("\n2. Indietro\n");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline

        if(choice == 1) {
                CliModificaCatalogo modificaCatalogo = new CliModificaCatalogo(scanner, aggiornaCatController);
                modificaCatalogo.start();
        }
    }

    private void visualizzaRegistroNoleggi() {
        System.out.println("\n=== REGISTRO NOLEGGI ===");
        // Implementazione visualizzazione noleggi
        System.out.println("Funzionalità non ancora implementata");
        System.out.println(CONTINUE);
        scanner.nextLine();
    }

    private void visualizzaPrenotazioni() {
        System.out.println("\n=== PRENOTAZIONI ===");
        List<PrenotazioneBean> prenotazioni = prenotazioniBibController.getPrenotazioni();

        if (prenotazioni.isEmpty()) {
            System.out.println("Nessuna prenotazione attiva.");
            return;
        }

        for (int i = 0; i < prenotazioni.size(); i++) {
            PrenotazioneBean p = prenotazioni.get(i);
            System.out.printf("%d. %s - %s (ISBN: %s)%n",
                    i+1,
                    p.getUtente().getUsername(),
                    p.getLibro().getTitolo(),
                    p.getLibro().getIsbn());
        }

        System.out.println(CONTINUE);
        scanner.nextLine();
    }

    private void logout() {
        new Logout().logout();
        System.out.println("Logout effettuato con successo.");
    }
}