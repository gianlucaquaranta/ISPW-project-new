package com.example.hellofx.cli;

import com.example.hellofx.bean.FiltriBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.exception.EmptyFiltersException;

import java.util.List;
import java.util.Scanner;

public class CliPrenotaLibro implements ShowInterface{


    @Override
    public String show(Scanner scanner) {
        while (true) {
            System.out.println("\n=== PRENOTA UN LIBRO ===\n");
            FiltriBean filtriBean = this.inserisciFiltri(scanner);
            List<LibroBean> = PLController.filtraRisultati(filtriBean);
            System.out.println("Opzioni:\n0. Home\nCLOSE");
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


    private FiltriBean inserisciFiltri(Scanner scanner) throws EmptyFiltersException {
        FiltriBean filtriBean = new FiltriBean();
        System.out.println("Inserisci i valori con cui vuoi effettuare la ricerca. Campo vuoto = filtro non inserito.");
        System.out.println("Titolo: ");
        filtriBean.setTitolo(scanner.nextLine());
        System.out.println("Autore: ");
        filtriBean.setAutore(scanner.nextLine());
        System.out.println("Genere: ");
        filtriBean.setGenere(scanner.nextLine());
        System.out.println("Isbn: ");
        filtriBean.setIsbn(scanner.nextLine());
        System.out.println("Biblioteca: ");
        filtriBean.setBiblioteca(scanner.nextLine());
        System.out.println("Cap: ");
        filtriBean.setCap(scanner.nextLine());
        System.out.println("Raggio (km): ");
        filtriBean.setRaggio(scanner.nextLine());

        if(this.isEmpty(filtriBean)) throw new EmptyFiltersException("Inserire almeno un criterio di ricerca");
        return filtriBean;
    }

    private boolean isEmpty(FiltriBean filtriBean) {
            return filtriBean.getAutore().isEmpty() && filtriBean.getTitolo().isEmpty() && filtriBean.getGenere().isEmpty() &&
                    filtriBean.getIsbn().isEmpty() && filtriBean.getBiblioteca().isEmpty() && filtriBean.getCap().isEmpty() &&
                    filtriBean.getRaggio().isEmpty();
        }

    }


}
