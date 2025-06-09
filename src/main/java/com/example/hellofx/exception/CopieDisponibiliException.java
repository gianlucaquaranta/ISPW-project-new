package com.example.hellofx.exception;

public class CopieDisponibiliException extends Exception {
    public CopieDisponibiliException(int copiePrenotate) {
        super("Sono state già prenotate " + copiePrenotate + " copie di questo libro, inserire un numero maggiore di "+ copiePrenotate + "!");
    }
}
