package com.example.hellofx.cli;

import com.example.hellofx.session.UtenteSession;

import java.util.Scanner;

public class CliProfiloUtente implements ShowInterface{

    private UtenteSession utenteSession = UtenteSession.getInstance();

    @Override
    public String show(Scanner scanner) {
        System.out.println("\nBenvenut*, " + UtenteSession.getUtente().getUsername());
        System.out.println("\n=== HOME - UTENTE ===\nSeleziona un indice e premi ENTER:\n0. Prenotazioni attive\n1. Noleggi attivi\n2. Log out\n3. Home\nCLOSE\n");
        return scanner.nextLine();
    }
}
