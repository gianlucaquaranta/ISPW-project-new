package com.example.hellofx.controller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.bean.UtenteBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Prenotazione;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PrenotazioniBibController {
    private Session session = SessionManager.getSession();

    public List<PrenotazioneBean> getPrenotazioni(){
        List<PrenotazioneBean> prenotazioni = new ArrayList<>();
        for(Prenotazione p: ((BibliotecarioSession)session).getBiblioteca().getPrenotazioniAttive()){
            prenotazioni.add(this.prenotazioneToBean(p));
        }
        return prenotazioni;
    }

    public List<PrenotazioneBean> searchByField(String field, String fieldType){
        List<Prenotazione> list = ((BibliotecarioSession)session).getBiblioteca().getPrenotazioniAttive();
        List<PrenotazioneBean> results = new ArrayList<>();

        Map<String, Function<Prenotazione, String>> fieldExtractors = Map.of(
                "isbn", Prenotazione::getIsbn,
                "id", Prenotazione::getIdPrenotazione,
                "username", p -> p.getDatiUtente()[0]
        );

        Function<Prenotazione, String> extractor = fieldExtractors.get(fieldType.toLowerCase());

        if (extractor == null) return this.getPrenotazioni(); // fieldType Mostra tutti

        for (Prenotazione p : list) {
            if (extractor.apply(p).equals(field)) {
                results.add(this.prenotazioneToBean(p));
            }
        }
        return results;
    }

    private PrenotazioneBean prenotazioneToBean(Prenotazione p){
        UtenteBean utenteBean = new UtenteBean();
        utenteBean.setUsername(p.getDatiUtente()[0]);
        utenteBean.setEmail(p.getDatiUtente()[1]);
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        BibliotecaBean bibliotecaBean = Converter.bibliotecaToBean(b);
        LibroBean libroBean = Converter.libroToBean(b.getLibroByIsbn(p.getIsbn()));

        return new PrenotazioneBean(p.getIdPrenotazione(), p.getDataInizio(), p.getDataScadenza(), utenteBean, bibliotecaBean, libroBean);
    }
}