package com.example.hellofx.cli;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.BibliotecarioBean;
import com.example.hellofx.bean.UtenteBean;
import com.example.hellofx.controller.RegistrazioneController;

import java.util.Scanner;

public class CliRegistrazione implements ShowInterface {
    RegistrazioneController registrazioneController = RegistrazioneControllerFactory.getInstance().createRegistrazioneController();

    public CliRegistrazione() {
        //necessario all'ottenimento della classe a partire dal suo package
    }

    @Override
    public String show(Scanner scanner) {
        while(true) {
            System.out.println("=== MENU DI REGISTRAZIONE ===");
            System.out.println("Selezionare il proprio ruolo:\n 0. Bibliotecario\n1. Utente\n2. Torna al log in\nCLOSE\n");
            String risposta = scanner.nextLine();

            if (risposta.equals("0")) {
                BibliotecarioBean bibliotecarioBean = new BibliotecarioBean();
                BibliotecaBean bibliotecaBean = new BibliotecaBean();

                this.bibliotecarioForm(scanner, bibliotecarioBean);
                this.bibliotecaForm(scanner, bibliotecaBean);

                System.out.println("\nConfermare i dati inseriti? (Y/N): ");
                if (scanner.nextLine().equalsIgnoreCase("Y") && registrazioneController.registerBibliotecario(bibliotecarioBean, bibliotecaBean)) {
                    return bibliotecarioBean.getClass().getSimpleName();
                }
            } else if (risposta.equals("1")) {
                UtenteBean utenteBean = new UtenteBean();

                this.utenteForm(scanner, utenteBean);

                System.out.println("\nConfermare i dati inseriti? (Y/N): ");
                if (scanner.nextLine().equalsIgnoreCase("Y") && registrazioneController.registerUtente(utenteBean)) {
                    return utenteBean.getClass().getSimpleName();
                }
            } else if (risposta.equalsIgnoreCase("CLOSE") || risposta.equals("2")) {
                return risposta;
            } else {
                System.out.println("\nInput inserito invalido. Riprova.\n");
            }
        }

    }


    public void bibliotecarioForm(Scanner scanner, BibliotecarioBean bibliotecarioBean) {
        System.out.print("Inserire le credenziali per effettuare la registrazione (premere ENTER per confermare).\nNome: ");
        bibliotecarioBean.setNome(scanner.nextLine());
        System.out.println("\nCognome: ");
        bibliotecarioBean.setCognome(scanner.nextLine());
        System.out.println("\nUsername: ");
        bibliotecarioBean.setUsername(scanner.nextLine());
        System.out.println("\nPassword: ");
        bibliotecarioBean.setPassword(scanner.nextLine());
    }

    public void bibliotecaForm(Scanner scanner, BibliotecaBean bibliotecaBean) {
        System.out.print("Inserire i dati della biblioteca (premere ENTER per confermare).\nNome: ");
        bibliotecaBean.setNome(scanner.nextLine());
        System.out.println("\nIndirizzo: ");
        bibliotecaBean.setIndirizzo(scanner.nextLine());
        System.out.println("\nNumero civico: ");
        bibliotecaBean.setNumeroCivico(scanner.nextLine());
        System.out.println("\nCittà: ");
        bibliotecaBean.setCitta(scanner.nextLine());
        System.out.println("\nCAP: ");
        bibliotecaBean.setCap(scanner.nextLine());
        System.out.println("\nProvincia: ");
        bibliotecaBean.setProvincia(scanner.nextLine());
        System.out.println("\nURL del sito ufficiale: ");
        bibliotecaBean.setUrl(scanner.nextLine());
    }

    public void utenteForm(Scanner scanner, UtenteBean utenteBean) {
        System.out.print("Inserire le credenziali per effettuare la registrazione (premere ENTER per confermare).\nUsername: ");
        utenteBean.setUsername(scanner.nextLine());
        System.out.println("\nPassword: ");
        utenteBean.setPassword(scanner.nextLine());
    }

}

