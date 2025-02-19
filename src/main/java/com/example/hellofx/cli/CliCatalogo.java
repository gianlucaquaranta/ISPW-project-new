package com.example.hellofx.cli;

import com.example.hellofx.bean.GenereBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;

import java.util.List;
import java.util.Scanner;

public class CliCatalogo implements ShowInterface{
    private AggiornaCatController aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();

    @Override
    public String show(Scanner scanner) {
        while (true) {
            System.out.println("\n=== CATALOGO ===\n");
            printListLibroBean(aggiornaCatController.getCatalogo());

            System.out.println("Opzioni:\n0. Home\n1. Aggiungi un libro\n2. Modifica un libro\n3. Elimina un libro\nCLOSE");
            String risposta = scanner.nextLine();

            switch (risposta.toUpperCase()) {
                case "0", "CLOSE":
                    return risposta;
                case "1":
                    gestisciAggiuntaLibro(scanner);
                    break;
                case "2":
                    gestisciModificaLibro(scanner);
                    break;
                case "3":
                    gestisciEliminazioneLibro(scanner);
                    break;
                default:
                    System.out.println("Input non valido. Riprova.");
            }
        }
    }

    // Metodo per gestire l'aggiunta di un libro
    private void gestisciAggiuntaLibro(Scanner scanner) {
        LibroBean libroBean = printAggiungi(scanner);
        if (libroBean != null) {
            aggiornaCatController.add(libroBean);
        }
    }

    // Metodo per gestire la modifica di un libro
    private void gestisciModificaLibro(Scanner scanner) {
        System.out.println("Scrivi l'ISBN del libro che vuoi modificare: ");
        LibroBean libroBean = printModifica(scanner.nextLine(), scanner);
        if (libroBean != null) {
            aggiornaCatController.update(libroBean);
        }
    }

    // Metodo per gestire l'eliminazione di un libro
    private void gestisciEliminazioneLibro(Scanner scanner) {
        System.out.println("Scrivi l'ISBN del libro che vuoi eliminare: ");
        LibroBean l = trovaLibro(scanner.nextLine());
        if (l == null) {
            System.out.println("Libro non trovato.");
            return;
        }
        System.out.println("Vuoi davvero eliminare questo libro? (Y/N): ");
        if (scanner.nextLine().equalsIgnoreCase("Y")) {
            aggiornaCatController.delete(l);
        }
    }


    public void printListLibroBean(List<LibroBean> l){
        int index = 0;
        for(LibroBean libro : l){
            System.out.println("---------------------------------------------");
            System.out.println(++index + ". " + libro.getTitolo());
            System.out.println(libro.getAutore());
            System.out.println(libro.getEditore());
            System.out.println(libro.getGenere());
            System.out.println(libro.getIsbn());
            System.out.println("Copie totali: " + libro.getNumCopie()[0] + ", copie disponibili: " + libro.getNumCopie()[1]);
            System.out.println("---------------------------------------------");
        }
    }

    public LibroBean printAggiungi(Scanner scanner){
        LibroBean l = new LibroBean();
        System.out.println("=== Form - Aggiungi un libro ===\nInserisci i dati richiesti.");
        System.out.println("Titolo: ");
        l.setTitolo(scanner.nextLine());
        System.out.println("Autore: ");
        l.setAutore(scanner.nextLine());
        System.out.println("Editore: ");
        l.setEditore(scanner.nextLine());
        System.out.println("Anno di pubblicazione: ");
        l.setAnnoPubblicazione(scanner.nextInt());
        scanner.nextLine();
        System.out.println("Genere (inserisci l'indice del genere corrispondente): ");
        l.setGenere(genereSelection(scanner));

        System.out.println("ISBN: ");
        l.setIsbn(scanner.nextLine());
        System.out.println("Numero di copie totali: ");
        Integer[] numCopie = {scanner.nextInt(), 0};
        l.setNumCopie(numCopie);

        System.out.println("Aggiungere il libro al catalogo? (Y/N): ");
        if(scanner.nextLine().equalsIgnoreCase("Y")){
            return l;
        }
        return null;
    }

    public LibroBean printModifica(String isbn, Scanner scanner) {
            LibroBean l = trovaLibro(isbn);
            if (l == null) {
                System.out.println("Libro non trovato.");
                return null;
            }

            System.out.println("=== Form - Modifica un libro ===");
            System.out.println("Se la stringa inserita è nulla, verrà tenuta la vecchia versione di ogni voce.");

            // Chiede e aggiorna i dati
            l.setTitolo(askForUpdate("Titolo", l.getTitolo(), scanner));
            l.setAutore(askForUpdate("Autore", l.getAutore(), scanner));
            l.setEditore(askForUpdate("Editore", l.getEditore(), scanner));

            System.out.println("Genere: " +l.getGenere().getNome() +"; Nuovo Genere: ");
            if(!scanner.nextLine().isEmpty()) l.setGenere(genereSelection(scanner));

            l.setIsbn(askForUpdate("ISBN", l.getIsbn(), scanner));

            System.out.println("Numero di copie totali: " + l.getNumCopie()[0] + "; Nuovo numero di copie totali: ");
            int nuovoNumCopie = readInt(scanner);
            if (nuovoNumCopie >= 0) {
                l.setNumCopie(new Integer[]{nuovoNumCopie, 0});
            }

            System.out.println("Confermare le modifiche? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                return l;
            }
        return null;
    }

    // Metodo per chiedere un aggiornamento di un campo
    private String askForUpdate(String field, String oldValue, Scanner scanner) {
        System.out.println(field + ": " + oldValue + "; Nuovo " + field + ": ");
        String input = scanner.nextLine();
        return input.isEmpty() ? oldValue : input;
    }

    // Metodo per leggere un intero con gestione degli errori
    private int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Errore! Inserire un numero valido:");
            scanner.next(); // Scarta input errato
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline rimasto
        return value;
    }

    private LibroBean trovaLibro(String isbn) {
        for (LibroBean bean : aggiornaCatController.getCatalogo()) {
            if (bean.getIsbn().equals(isbn)) {
                return bean; // Ritorna il libro se trovato
            }
        }
        return null; // Nessun libro trovato
    }

    public GenereBean genereSelection(Scanner scanner){
        GenereBean[] generi = GenereBean.values();
        for (int i = 0; i <= generi.length; i++) {
            System.out.println((i+1) + ". " + generi[i].getNome());
        }
        int risposta = scanner.nextInt();
        scanner.nextLine();

        while (risposta < 1 || risposta > generi.length) {
            System.out.println("Scelta non valida! Inserisci un numero tra 1 e " + generi.length + ": ");
            risposta = scanner.nextInt();
        }
        return generi[risposta - 1];
    }

}
