package com.example.hellofx.service;

import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.entity.*;
import com.example.hellofx.session.Session;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {

    private BibliotecaDao bibliotecaDao;
    private LibroDao libroDao;
    private NoleggioDao noleggioDao;
    private PrenotazioneDao prenotazioneDao;
    private BibliotecarioDao bibliotecarioDao;

    public BibliotecaService(Session session) {
        this.bibliotecaDao = FactoryProducer.getFactory("file").createDaoBiblioteca();
        this.noleggioDao = FactoryProducer.getFactory("db").createDaoNoleggio();
        this.libroDao = FactoryProducer.getFactory("db").createDaoLibro();
        this.bibliotecarioDao = FactoryProducer.getFactory("db").createDaoBibliotecario();

        if(session.isFile()) {
            this.prenotazioneDao = FactoryProducer.getFactory("file").createDaoPrenotazione();
        } else {
            this.prenotazioneDao = FactoryProducer.getFactory("db").createDaoPrenotazione();
        }
    }
    
    public List<Biblioteca> getFilteredBiblioteca(Filtri filtri){
        List<Biblioteca> biblioteche = bibliotecaDao.loadFiltered(filtri);
        for(Biblioteca b : biblioteche){
            this.createBiblioteca(b);
        }
        return biblioteche;
    }
    
    
    public Biblioteca getBibliotecaFromId(String id){
        Biblioteca b = bibliotecaDao.loadOne(id);
        this.createBiblioteca(b);
        return b;
    }

    public Biblioteca getBibliotecaFromBibliotecario(String username){
        Biblioteca b = bibliotecaDao.loadOneFromBibliotecario(username);
        this.createBiblioteca(b);
        return b;
    }

    private void createBiblioteca(Biblioteca b){
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

        String id = b.getId();
        //aggiungo le entity noleggio alla biblioteca
        List<Noleggio> noleggi = noleggioDao.loadAllBiblioteca(id);
        b.setNoleggiAttivi(noleggi);

        //aggiungo le entity prenotazione alla biblioteca
        List<Prenotazione> prenotazioni = prenotazioneDao.loadAllBiblioteca(id);
        b.setPrenotazioniAttive(prenotazioni);
    }

    public void storeBiblioteca(Biblioteca b) {
        bibliotecaDao.store(b);
    }

    public void updateBiblioteca(Biblioteca b){
        bibliotecaDao.update(b);
    }

}
