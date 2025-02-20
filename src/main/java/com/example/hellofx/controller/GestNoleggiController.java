package com.example.hellofx.controller;

import com.example.hellofx.bean.NoleggioBean;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;

import java.util.List;

public class GestNoleggiController {
    private Session bibliotecarioSession = BibliotecarioSession.getInstance();
    private Session utenteSession = UtenteSession.getInstance();
    public List<NoleggioBean> getNoleggiBiblioteca(){
        //dalla sessione ricava la biblioteca
        //Chiama il dao cercando i noleggi per biblioteca
        //popola i noleggioBean e mettili in una lista
        //ritorna la lista alla boundary
    }

    public List<NoleggioBean> getNoleggiUtente(){
        //dalla sessione ricava la biblioteca
        //Chiama il dao cercando i noleggi per l'utente
        //popola i noleggioBean e mettili in una lista
        //ritorna la lista alla boundary
    }

    public void add(NoleggioBean noleggioBean){
        //Store del noleggio avente come chiave l'id di questo noleggio
    }

    public void delete(NoleggioBean noleggioBean){

    }

}
