package com.example.hellofx.cli;

import java.util.Scanner;

public class CliPrenotazioniUtente {
    private Scanner scanner;

    public CliPrenotazioniUtente(Scanner s) {
        scanner = s;
    }

    public void start() {
        System.out.println("\n=== PRENOTAZIONI ATTIVE ===");
        mostraPrenotazioniAttive();
    }

    private void mostraPrenotazioniAttive() {
        //TODO
    }
}
