package com.example.hellofx.cli;

import com.example.hellofx.session.UtenteSession;

import java.util.Scanner;

public class CliHomeUtente implements ShowInterface{
    private UtenteSession utenteSession = UtenteSession.getInstance();

    @Override
    public String show(Scanner scanner) {
        System.out.println("\nBenvenut*, " + UtenteSession.getUtente().getUsername());
        System.out.println("\n=== HOME - UTENTE ===\nSeleziona un indice e premi ENTER:\n0. Prenota un libro\n1. Trova prezzi\n2. Profilo\nCLOSE\n");
        return scanner.nextLine();
    }
}
