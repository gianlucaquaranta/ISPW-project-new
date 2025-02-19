package com.example.hellofx.cli;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.controller.LoginController;
import com.example.hellofx.controllerfactory.LoginControllerFactory;

import java.util.Scanner;

public class CliLogin implements ShowInterface{
    LoginBean loginBean = new LoginBean();
    LoginController loginController = LoginControllerFactory.getInstance().createLoginController();

    public CliLogin() {
        //necessario all'ottenimento della classe a partire dal suo package
    }

    @Override
    public String show(Scanner scanner) {
        while(true) {
            System.out.println("=== MENU DI LOG IN ===");
            System.out.print("Scegliere la modalità di accesso:\n0. Accedi tramite le credenziali\n1. Accedi come utente ospite\nCLOSE");
            System.out.print("\nInserire 0, 1 o CLOSE e premere ENTER per confermare: ");
            String risposta = scanner.nextLine();
            if(risposta.equals("1")){
                loginBean.setUsername("");
                loginBean.setPassword("");
                loginBean.setType("utente");
                return loginBean.getType();
            } else if(risposta.equalsIgnoreCase("CLOSE")) {
                return risposta;
            } else if(risposta.equals("0")) {
                System.out.print("\nInserire le credenziali per effettuare l'accesso (premere ENTER per confermare).\nUsername: ");
                loginBean.setUsername(scanner.nextLine());
                System.out.print("\nPassword: ");
                loginBean.setPassword(scanner.nextLine());
                loginBean = loginController.authenticate(loginBean);
                if (loginBean.getType() == null) {
                    System.out.println("\nLogin Fallito.\n");
                } else {
                    System.out.println("\nAutenticazione eseguita come " + loginBean.getUsername() + " con ruolo " + loginBean.getType() + ".\n");
                    return loginBean.getType();
                }
            } else {
                System.out.println("\nInput inserito invalido. Riprova.\n");
            }

        }

    }

}
