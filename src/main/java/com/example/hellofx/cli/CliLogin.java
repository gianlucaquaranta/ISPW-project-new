package com.example.hellofx.cli;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.controller.LoginController;
import com.example.hellofx.controller.LoginResult;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.LoginControllerFactory;
import com.example.hellofx.exception.PrenotazioneGiaPresenteException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CliLogin {
    private Scanner scanner;
    private final LoginController loginController = LoginControllerFactory.getInstance().createLoginController();
    private PLController plController = null; // Per gestire il caso speciale

    public CliLogin(Scanner s) {
        this.scanner = s;
    }
    public void start() {
        System.out.println("=== LOGIN ===");
        mostraMenuLogin();
    }

    public void start(PLController plController) {
        this.plController = plController;
        start();
    }

    private void mostraMenuLogin() {
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Accedi senza account");
            System.out.println("3. Registrati");
            System.out.println("4. Esci");
            System.out.print("Scelta: ");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (scelta) {
                case 1:
                    effettuaLogin();
                    if(plController != null) {
                        return;
                    }
                    break;
                case 2:
                    loginSenzaAccount();
                    if(plController != null) {
                        return;
                    }
                    break;
                case 3:
                    registrazione();
                    break;
                case 4:
                    System.out.println("Arrivederci!");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Opzione non valida!");
                    break;
            }
        }
    }

    private void effettuaLogin() {
        System.out.println("\n--- INSERISCI CREDENZIALI ---");
        String username = richiediInput("Username: ");
        String password = richiediInput("Password: ");

        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword(password);

        LoginResult result = loginController.authenticate(loginBean);

        switch (result) {
            case LoginResult.UTENTE:
                gestisciUtenteAutenticato();
                break;
            case LoginResult.BIBLIOTECARIO:
                avviaHomeBibliotecario();
                break;
            case LoginResult.NON_AUTENTICATO:
                printLogger("Credenziali non valide");
                break;
        }
    }

    private void gestisciUtenteAutenticato() {
        if (plController != null) {
            try {
                plController.registraPrenotazione();
            } catch(PrenotazioneGiaPresenteException e){
                printLogger(e.getMessage());
            }
            return;
        }
        avviaSchermateUtente("Accesso effettuato come utente");
    }

    private void loginSenzaAccount() {
        if(plController!=null){
            return;
        }
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("");
        loginBean.setPassword("");
        loginController.authenticate(loginBean);
        avviaSchermateUtente("Accesso effettuato come utente non loggato");
    }

    private void registrazione() {
        CliRegistrazione cliRegistrazione = new CliRegistrazione(scanner);
        cliRegistrazione.start();
    }

    private void avviaSchermateUtente(String message) {
        System.out.println("\n"+message);
        CliSchermataInizialeUtente schermateUtente = new CliSchermataInizialeUtente(scanner);
        schermateUtente.start();
    }

    private void avviaHomeBibliotecario() {
        System.out.println("\nAccesso effettuato come bibliotecario");
        /*CliHomeBibliotecario homeBibliotecario = new CliHomeBibliotecario(scanner);
        homeBibliotecario.start();*/
    }

    private String richiediInput(String messaggio) {
        System.out.print(messaggio);
        return scanner.nextLine();
    }

    void printLogger(String message) {
        Logger logger = Logger.getLogger(CliLogin.class.getName());
        logger.log(Level.SEVERE, message);
    }
}