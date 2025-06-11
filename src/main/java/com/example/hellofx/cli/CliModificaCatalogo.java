package com.example.hellofx.cli;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.exception.CopieDisponibiliException;
import com.example.hellofx.exception.LibroGiaPresenteException;
import com.example.hellofx.bean.GenereBean;

import java.util.List;
import java.util.Scanner;

public class CliModificaCatalogo {
    private final Scanner scanner;
    private final AggiornaCatController aggiornaCatController;
    private boolean isEditMode = false;
    private static final String MENU_PLACEHOLDER ="%d. %s - %s (ISBN: %s)%n";
    private static final String CHOICE = "Scelta: ";

    public CliModificaCatalogo(Scanner scanner, AggiornaCatController controller) {
        this.scanner = scanner;
        this.aggiornaCatController = controller;
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== GESTIONE CATALOGO ===");
            System.out.println("1. Visualizza catalogo");
            System.out.println("2. Aggiungi libro");
            System.out.println("3. Modifica libro");
            System.out.println("4. Elimina libro");
            System.out.println("5. Torna indietro");
            System.out.print(CHOICE);

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (choice) {
                case 1:
                    mostraCatalogo();
                    break;
                case 2:
                    aggiungiLibro();
                    break;
                case 3:
                    modificaLibro();
                    break;
                case 4:
                    eliminaLibro();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Scelta non valida!");
            }
        }
    }

    private void mostraCatalogo() {
        List<LibroBean> catalogo = aggiornaCatController.getCatalogo();

        if (catalogo.isEmpty()) {
            System.out.println("\nNessun libro presente nel catalogo.");
            return;
        }

        System.out.println("\n=== CATALOGO LIBRI ===");
        CliCatalogoTableConfigurator.mostraTabellaCatalogo(catalogo);

        attendiInput();
    }

    private void aggiungiLibro() {
        isEditMode = false;
        mostraFormLibro(null);
    }

    private void modificaLibro() {
        List<LibroBean> catalogo = aggiornaCatController.getCatalogo();

        if (catalogo.isEmpty()) {
            System.out.println("\nNessun libro presente nel catalogo da modificare.");
            return;
        }

        System.out.println("\nSeleziona il libro da modificare:");
        for (int i = 0; i < catalogo.size(); i++) {
            LibroBean libro = catalogo.get(i);
            System.out.printf(MENU_PLACEHOLDER,
                    i+1,
                    libro.getTitolo(),
                    libro.getAutore(),
                    libro.getIsbn());
        }

        System.out.print(CHOICE);
        int scelta = scanner.nextInt();
        scanner.nextLine();

        if (scelta < 1 || scelta > catalogo.size()) {
            System.out.println("Selezione non valida!");
            return;
        }

        isEditMode = true;
        LibroBean libroInModifica = catalogo.get(scelta - 1);
        mostraFormLibro(libroInModifica);
    }

    private void mostraFormLibro(LibroBean libro) {
        System.out.println("\n=== " + (isEditMode ? "MODIFICA LIBRO" : "AGGIUNGI LIBRO") + " ===");

        try {
            LibroBean bean = raccogliDatiLibro(libro);
            aggiornaCatController.validateLibroBean(bean);
            salvaLibro(bean);
        } catch (NumberFormatException _) {
            System.out.println("Errore: Inserire un numero valido per anno o copie");
        } catch (IllegalArgumentException | LibroGiaPresenteException | CopieDisponibiliException e) {
            System.out.println("Errore: " + e.getMessage());
        }

        attendiInput();
    }

    private LibroBean raccogliDatiLibro(LibroBean libro) {
        String titolo = chiediInput("Titolo", libro != null ? libro.getTitolo() : "");

        String isbn;
        if (isEditMode) {
            isbn = libro != null ? libro.getIsbn() : "";
            System.out.println("ISBN [non modificabile]: " + isbn);
        } else {
            isbn = chiediInput("ISBN", libro != null ? libro.getIsbn() : "");
        }

        String autore = chiediInput("Autore", libro != null ? libro.getAutore() : "");
        String editore = chiediInput("Editore", libro != null ? libro.getEditore() : "");
        String genereStr = chiediGenere(libro != null ? libro.getGenere().getNome() : null);
        int anno = chiediNumero("Anno pubblicazione", libro != null ? libro.getAnnoPubblicazione() : 0);
        int copie = chiediNumero("Copie totali", libro != null ? libro.getCopie() : 0);

        return creaLibroBean(titolo, isbn, autore, editore, genereStr, anno, copie);
    }

    private void salvaLibro(LibroBean bean) throws LibroGiaPresenteException, CopieDisponibiliException {
        if (isEditMode) {
            aggiornaCatController.update(bean);
            System.out.println("\nLibro modificato con successo!");
        } else {
            aggiornaCatController.add(bean);
            System.out.println("\nLibro aggiunto con successo!");
        }
    }


    private void eliminaLibro() {
        List<LibroBean> catalogo = aggiornaCatController.getCatalogo();

        if (catalogo.isEmpty()) {
            System.out.println("\nNessun libro presente nel catalogo da eliminare.");
            return;
        }

        System.out.println("\nSeleziona il libro da eliminare:");
        CliCatalogoTableConfigurator.mostraTabellaCatalogo(catalogo);

        System.out.print(CHOICE);
        int scelta = scanner.nextInt();
        scanner.nextLine();

        if (scelta < 1 || scelta > catalogo.size()) {
            System.out.println("Selezione non valida!");
            return;
        }

        LibroBean libro = catalogo.get(scelta - 1);

        System.out.print("Sei sicuro di voler eliminare \"" + libro.getTitolo() + "\"? (s/n): ");
        String conferma = scanner.nextLine();

        if (conferma.equalsIgnoreCase("s")) {
            aggiornaCatController.delete(libro);
            System.out.println("Libro eliminato con successo!");
        } else {
            System.out.println("Operazione annullata.");
        }

        attendiInput();
    }

    private void attendiInput() {
        System.out.print("\nPremi INVIO per continuare...");
        scanner.nextLine();
    }

    private String chiediInput(String campo, String valorePredefinito) {
        System.out.print(campo + " [" + valorePredefinito + "]: ");
        String input = scanner.nextLine();
        return input.isEmpty() ? valorePredefinito : input;
    }

    private int chiediNumero(String campo, int valorePredefinito) {
        String input = chiediInput(campo, String.valueOf(valorePredefinito));
        return Integer.parseInt(input);  // L’eccezione è già gestita nel metodo principale
    }

    private String chiediGenere(String valorePredefinito) {
        System.out.println("Scegli il genere:");
        GenereBean[] generi = GenereBean.values();

        for (int i = 0; i < generi.length; i++) {
            System.out.println((i + 1) + ". " + generi[i].getNome());
        }

        System.out.print("Numero [" + (valorePredefinito != null ? valorePredefinito : "nessuno") + "]: ");
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            return valorePredefinito;
        }

        try {
            int scelta = Integer.parseInt(input);
            if (scelta >= 1 && scelta <= generi.length) {
                return generi[scelta - 1].getNome();
            } else {
                System.out.println("Scelta non valida, manterrò il valore predefinito.");
                return valorePredefinito;
            }
        } catch (NumberFormatException _) {
            System.out.println("Input non valido, manterrò il valore predefinito.");
            return valorePredefinito;
        }
    }


    private LibroBean creaLibroBean(String titolo, String isbn, String autore, String editore,
                                    String genere, int anno, int copie) {
        LibroBean bean = new LibroBean();
        bean.setTitolo(titolo);
        bean.setIsbn(isbn);
        bean.setAutore(autore);
        bean.setEditore(editore);
        bean.setGenere(GenereBean.fromString(genere));
        bean.setAnnoPubblicazione(anno);
        bean.setNumCopie(new Integer[]{copie, -1});
        return bean;
    }
}