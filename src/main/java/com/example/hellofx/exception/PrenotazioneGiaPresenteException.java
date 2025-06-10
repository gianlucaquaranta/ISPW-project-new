package com.example.hellofx.exception;

public class PrenotazioneGiaPresenteException extends RuntimeException {
    public PrenotazioneGiaPresenteException(String isbn, String nomeBib) {
      super("Hai già una prenotazione attiva per il libro con isbn: "+isbn+" nella biblioteca: "+nomeBib+".");
    }
}
