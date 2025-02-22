package com.example.hellofx.converter;

import com.example.hellofx.bean.*;
import com.example.hellofx.entity.*;
import com.example.hellofx.entity.entityfactory.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {

    private Converter() {}

    public static Libro beanToLibro(LibroBean libroBean){
        Libro l = LibroFactory.getInstance().createLibro();
        l.setIsbn(libroBean.getIsbn());
        l.setTitolo(libroBean.getTitolo());
        l.setAutore(libroBean.getAutore());
        l.setEditore(libroBean.getEditore());
        l.setGenere(libroBean.getGenere().getNome());
        l.setAnnoPubblicazione(libroBean.getAnnoPubblicazione());
        return l;
    }

    public static LibroBean libroToBean(Libro l){
        LibroBean lb = new LibroBean();
        lb.setIsbn(l.getIsbn());
        lb.setTitolo(l.getTitolo());
        lb.setAutore(l.getAutore());
        lb.setEditore(l.getEditore());
        lb.setAnnoPubblicazione(l.getAnnoPubblicazione());
        lb.setGenere(GenereBean.fromString(l.getGenere()));
        return lb;
    }

    public static Utente beanToUtente(UtenteBean utenteBean){
        Utente u = UtenteFactory.getInstance().createUtente();
        u.setUsername(utenteBean.getUsername());
        u.setPassword(utenteBean.getPassword());
        u.setEmail(utenteBean.getEmail());
        return u;
    }

    public static Bibliotecario beanToBibliotecario(BibliotecarioBean bibliotecarioBean){
        Bibliotecario bibliotecario = BibliotecarioFactory.getInstance().createBibliotecario();
        bibliotecario.setUsername(bibliotecarioBean.getUsername());
        bibliotecario.setPassword(bibliotecarioBean.getPassword());
        bibliotecario.setNome(bibliotecarioBean.getNome());
        bibliotecario.setCognome(bibliotecarioBean.getCognome());
        return bibliotecario;
    }

    public static Biblioteca beanToBiblioteca(BibliotecaBean bibliotecaBean){
        Biblioteca b = BibliotecaFactory.getInstance().createBiblioteca();
        b.setNome(bibliotecaBean.getNome());
        Posizione posizione = new Posizione(bibliotecaBean.getCap(), bibliotecaBean.getIndirizzo(), bibliotecaBean.getNumeroCivico(), bibliotecaBean.getCitta(), bibliotecaBean.getProvincia());
        b.setPosizione(posizione);
        b.setUrl(bibliotecaBean.getUrl());
        //dopo aver settato nome e posizione è possibile creare l'id
        b.setId();
        b.setCatalogo(null);
        b.setPrenotazioniAttive(null);
        b.setCopie(null);
        b.setNoleggiAttivi(null);
        return b;
    }

    public static NoleggioBean noleggioToBean(Noleggio n){
        NoleggioBean nb = new NoleggioBean();
        nb.setIdNoleggio(n.getIdNoleggio());
        nb.setDataInizio(n.getDataInizio());
        nb.setDataScadenza(n.getDataInizio());
        nb.setIsbn(n.getIsbn());
        nb.setDatiUtente(n.getDatiUtente());
        nb.setIdBiblioteca(n.getIdBiblioteca());
        return nb;
    }

    public static Noleggio beanToNoleggio(NoleggioBean nb){
        Noleggio n = NoleggioFactory.getInstance().createNoleggio();
        n.setDataInizio(nb.getDataInizio());
        n.setDataScadenza(nb.getDataInizio());
        n.setIsbn(n.getIsbn());
        n.setDatiUtente(nb.getDatiUtente());
        n.setIdBiblioteca(nb.getIdBiblioteca());
        n.setIdNoleggio();
        return n;
    }

    public static Prenotazione beanToPrenotazione(PrenotazioneBean pb){
        Prenotazione p = PrenotazioneFactory.getInstance().createPrenotazione();
        p.setIdPrenotazione(pb.getIdPrenotazione());
        p.setDataInizio(pb.getDataInizio());
        p.setDataScadenza();
        p.setIsbn(pb.getIsbn());
        p.setDatiUtente(pb.getDatiUtente());
        p.setIdBiblioteca(pb.getIdBiblioteca());
        return p;
    }

    public static PrenotazioneBean prenotazioneToBean(Prenotazione p){
        PrenotazioneBean pb = new PrenotazioneBean();
        pb.setDataInizio(p.getDataInizio());
        pb.setDataScadenza();
        pb.setIsbn(p.getIsbn());
        pb.setDatiUtente(p.getDatiUtente());
        pb.setIdBiblioteca(p.getIdBiblioteca());
        pb.setIdPrenotazione();
        return pb;
    }

    public static String timestampToString(Timestamp timestamp){
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       return localDateTime.format(formatter);
    }

    public static Timestamp stringToTimestamp(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return Timestamp.valueOf(localDate.atStartOfDay());
    }

}
