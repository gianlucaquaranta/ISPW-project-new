package com.example.hellofx.cli;

import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;

import java.util.Scanner;

public class CliHomeBibliotecario implements ShowInterface{
    private BibliotecarioSession session = BibliotecarioSession.getInstance();

    @Override
    public String show(Scanner scanner) {
        System.out.println("\nBenvenut*, " + session.getBibliotecario().getNome() + " " + session.getBibliotecario().getCognome());
        System.out.println("\n=== HOME - BIBLIOTECA ===\nSeleziona un indice e premi ENTER:\n0. Catalogo\n1. Registro Noleggi\n2. Prenotazioni attive\n3. Log out\nCLOSE\n");
        return scanner.nextLine();
    }

}
