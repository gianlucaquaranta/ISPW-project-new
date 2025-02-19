package com.example.hellofx.service;

import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.entity.*;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {
    private BibliotecaDao bibliotecaDao;
    private LibroDao libroDao;
    private NoleggioDao noleggioDao;
    private PrenotazioneDao prenotazioneDao;
    private BibliotecarioDao bibliotecarioDao;

    public BibliotecaService(BibliotecaDao bibliotecaDao, LibroDao libroDao, NoleggioDao noleggioDao, PrenotazioneDao prenotazioneDao, BibliotecarioDao bibliotecarioDao) {
        this.bibliotecaDao = bibliotecaDao;
        this.libroDao = libroDao;
        this.noleggioDao = noleggioDao;
        this.prenotazioneDao = prenotazioneDao;
        this.bibliotecarioDao = bibliotecarioDao;
    }

    private BibliotecaService(){}

    public Biblioteca getBiblioteca(String id){
        Biblioteca b = bibliotecaDao.loadOne(id);

        //aggiungo le entity libro alla biblioteca
        List<Libro> catalogo = new ArrayList<>();
        for(Libro l : b.getCatalogo()){
            catalogo.add(libroDao.load(l.getIsbn()));
        }
        b.setCatalogo(catalogo);

        //aggiungo le entity bibliotecario alla biblioteca
        List<Bibliotecario> bibliotecari = new ArrayList<>();
        for(Bibliotecario u : b.getBibliotecari()){
            bibliotecari.add(bibliotecarioDao.load(u.getUsername()));
        }
        b.setBibliotecari(bibliotecari);


        //aggiungo le entity noleggio alla biblioteca
        List<Noleggio> noleggi = noleggioDao.loadAllBiblioteca(id);
        b.setNoleggiAttivi(noleggi);

        //aggiungo le entity prenotazione alla biblioteca
        List<Prenotazione> prenotazioni = prenotazioneDao.loadAllBiblioteca(id);
        b.setPrenotazioniAttive(prenotazioni);

        return b;
    }

}
