package com.example.hellofx.converter;

import com.example.hellofx.bean.*;
import com.example.hellofx.model.*;
import com.example.hellofx.model.modelfactory.*;
import com.example.hellofx.session.BibliotecarioSession;

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
        u.setEmail(utenteBean.getEmail());
        return u;
    }


    public static UtenteBean utenteTobean(Utente utente){
        UtenteBean u = new UtenteBean();
        u.setUsername(utente.getUsername());
        u.setEmail(utente.getEmail());
        return u;
    }

    public static Biblioteca beanToBiblioteca(BibliotecaBean bibliotecaBean){
        Biblioteca b = BibliotecaFactory.getInstance().createBiblioteca();
        b.setNome(bibliotecaBean.getNome());
        Posizione posizione = new Posizione(bibliotecaBean.getCap(), bibliotecaBean.getIndirizzo(), bibliotecaBean.getNumeroCivico(), bibliotecaBean.getCitta(), bibliotecaBean.getProvincia());
        b.setPosizione(posizione);
        //dopo aver settato nome e posizione è possibile creare l'id
        b.setCatalogo(null);
        b.setPrenotazioniAttive(null);
        b.setCopie(null);
        b.setNoleggiAttivi(null);
        b.setId(b.getNome());
        return b;
    }

    public static BibliotecaBean bibliotecaToBean(Biblioteca biblioteca){
        BibliotecaBean b = new BibliotecaBean();
        b.setIdBiblioteca(biblioteca.getId());
        b.setNome(biblioteca.getNome());
        b.setIndirizzo(biblioteca.getPosizione().getIndirizzo());
        b.setCap(biblioteca.getPosizione().getCap());
        b.setNumeroCivico(biblioteca.getPosizione().getNumeroCivico());
        b.setCitta(biblioteca.getPosizione().getCitta());
        b.setProvincia(biblioteca.getPosizione().getProvincia());

        return b;
    }

    public static Prenotazione beanToPrenotazione(PrenotazioneBean pb){
        Prenotazione p = PrenotazioneFactory.getInstance().createPrenotazione();

        p.setDataInizio(pb.getDataInizioT());
        p.setDataScadenza(pb.getDataScadenzaT());

        String[] datiUtente = new String[2];
        datiUtente[0] = pb.getUtente().getUsername();
        datiUtente[1] = pb.getUtente().getEmail();

        p.setDatiUtente(datiUtente);
        p.setIsbn(pb.getLibro().getIsbn());
        p.setIdBiblioteca(pb.getBibliotecaB().getIdBiblioteca());
        p.setIdPrenotazione();
        return p;
    }

    public static PrenotazioneBean prenotazioneBibliotecaToBean(Prenotazione p, Biblioteca b){
        UtenteBean utenteBean = new UtenteBean();
        utenteBean.setUsername(p.getDatiUtente()[0]);
        utenteBean.setEmail(p.getDatiUtente()[1]);
        BibliotecaBean bibliotecaBean = Converter.bibliotecaToBean(b);
        LibroBean libroBean = Converter.libroToBean(b.getLibroByIsbn(p.getIsbn()));

        return new PrenotazioneBean(p.getIdPrenotazione(), p.getDataInizio(), p.getDataScadenza(), utenteBean, bibliotecaBean, libroBean);
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
