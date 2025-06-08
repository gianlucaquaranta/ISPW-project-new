package com.example.hellofx.exception;

public class LibroGiaPresenteException extends Exception { //checked
    public LibroGiaPresenteException(String isbn) {
        super("Il libro con ISBN " + isbn + " è già presente nel catalogo.");
    }
}
