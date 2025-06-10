package com.example.hellofx.cli;

import com.example.hellofx.bean.RegistrazioneBibliotecaBean;
import com.example.hellofx.bean.RegistrazioneUtenteBean;
import com.example.hellofx.controller.RegistrazioneController;
import com.example.hellofx.controllerfactory.RegistrazioneControllerFactory;

import java.util.Scanner;

public class CliRegistrazione {
    Scanner scanner;
    private final RegistrazioneController registrazioneController = RegistrazioneControllerFactory.getInstance().creteRegistrazioneController();

    CliRegistrazione(Scanner s) {
        scanner = s;
    }
    public void start() {
        System.out.println("=== REGISTRAZIONE ===");
        mostraSceltaTipoRegistrazione();
    }

    private void mostraSceltaTipoRegistrazione() {

        while (true) {
            System.out.println("\nRegistrati come:");
            System.out.println("1. Utente");
            System.out.println("2. Biblioteca");
            System.out.println("3. Torna al login");
            System.out.print("Scelta: ");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    registraUtente();
                    return;
                case 2:
                    registraBiblioteca();
                    return;
                case 3:
                    System.out.println("Torno al login...");
                    return;
                default:
                    System.out.println("Opzione non valida!");
            }
        }
    }

    private void registraUtente() {
        System.out.println("\n--- REGISTRAZIONE UTENTE ---");
        String username = richiediInput("Username: ");
        String email = richiediInput("Email: ");
        String password = richiediInput("Password: ");

        RegistrazioneUtenteBean regBean = new RegistrazioneUtenteBean(username, email, password);
        boolean success = registrazioneController.registraUtente(regBean);

        if (success) {
            System.out.println("\nRegistrazione completata con successo!");
            System.out.println("Ora puoi effettuare il login");
        } else {
            System.out.println("\nERRORE: Username già esistente!");
        }
    }

    private void registraBiblioteca() {
        System.out.println("\n--- REGISTRAZIONE BIBLIOTECA ---");
        String nome = richiediInput("Nome biblioteca: ");
        String indirizzo = richiediInput("Indirizzo: ");
        String numCivico = richiediInput("Numero civico: ");
        String citta = richiediInput("Città: ");
        String provincia = richiediInput("Provincia: ");
        String cap = richiediInput("CAP: ");
        String password = richiediInput("Password: ");

        RegistrazioneBibliotecaBean regBean = new RegistrazioneBibliotecaBean(
                nome, password, indirizzo, cap, numCivico, citta, provincia);

        boolean success = registrazioneController.registraBiblioteca(regBean);

        if (success) {
            System.out.println("\nBiblioteca registrata con successo!");
            System.out.println("Ora puoi effettuare il login");
        } else {
            System.out.println("\nERRORE: Nome biblioteca già esistente!");
        }
    }

    private String richiediInput(String messaggio) {
        System.out.print(messaggio);
        return scanner.nextLine();
    }

}

