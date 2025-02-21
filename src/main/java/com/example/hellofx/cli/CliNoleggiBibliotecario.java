package com.example.hellofx.cli;

import com.example.hellofx.bean.NoleggioBean;
import com.example.hellofx.controller.GestNoleggiController;
import com.example.hellofx.controllerfactory.GestNoleggiControllerFactory;

import java.util.List;
import java.util.Scanner;

public class CliNoleggiBibliotecario implements ShowInterface{
    private GestNoleggiController gestNoleggiController = GestNoleggiControllerFactory.getInstance().createGestNoleggiController();

    @Override
    public String show(Scanner scanner) {
        while (true) {
            System.out.println("\n=== NOLEGGI ATTIVI ===\n");
            printListNoleggioBean(gestNoleggiController.getNoleggiBiblioteca());

            System.out.println("Opzioni:\n0. Home\nCLOSE");
            String risposta = scanner.nextLine();

            switch (risposta.toUpperCase()) {
                case "0", "CLOSE":
                    return risposta;
                default:
                    System.out.println("Input non valido. Riprova.");
            }
        }
    }

    public void printListNoleggioBean(List<NoleggioBean> l) {
        int index = 0;
        for(NoleggioBean n : l){
            System.out.println("---------------------------------------------");
            System.out.println(++index + ". Libro: " + n.getIsbn());
            System.out.println("Username: " + n.getDatiUtente()[0]);
            System.out.println("Email: " + n.getDatiUtente()[0]);
            System.out.println("Data di inizio: " + n.getDataInizio());
            System.out.println("Data di scadenza: " + n.getDataScadenza());
            System.out.println("Giorni rimanenti: " + Integer.parseInt(n.getGiorniRimasti()));
            System.out.println("---------------------------------------------");
        }
    }




}
