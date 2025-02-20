package com.example.hellofx.controller;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;

import java.util.List;

public class GestPrenotazioniController {
    private Session bibliotecarioSession = BibliotecarioSession.getInstance();
    private Session utenteSession = UtenteSession.getInstance();
    public List<PrenotazioneBean> getPrenotazioniBiblioteca(){
        //dalla sessione ricava la biblioteca
        //Chiama il dao cercando i prenotazioni per biblioteca
        //popola le prenBean e mettili in una lista
        //ritorna la lista alla boundary
    }

    public List<PrenotazioneBean> getPrenotazioniUtente(){
        //dalla sessione ricava la biblioteca
        //Chiama il dao cercando le pren per l'utente
        //popola le prenBean e mettili in una lista
        //ritorna la lista alla boundary
    }

    public void add(PrenotazioneBean prenotazioneBean){
        //Store del noleggio avente come chiave l'id di questo noleggio
    }

    public void delete(PrenotazioneBean prenotazioneBean){

    }
}
