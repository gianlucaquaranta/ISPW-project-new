package com.example.hellofx.service;

import com.example.hellofx.dao.DaoFactory;
import com.example.hellofx.dao.PersistenceType;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Libro;
import com.example.hellofx.model.Prenotazione;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BibliotecaService {

    private static BibliotecaDao bDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoBiblioteca();
    private static LibroDao lDaoD = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoLibro();
    private static LibroDao lDaoM = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoLibro();
    private static PrenotazioneDao pDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoPrenotazione();

    private BibliotecaService() { }

    public static Biblioteca load(String id){

        Biblioteca b = bDao.loadOne(id); //istanza di biblioteca senza catalogo, prenotazioniAttive, noleggiAttivi

        return componiBiblioteca(b);
    }

    public static List<Biblioteca> loadAll(){
        List<Biblioteca> biblioteche = bDao.loadAll();

        for(Biblioteca b : biblioteche){
            componiBiblioteca(b);
        }
        return biblioteche;
    }

    private static Biblioteca componiBiblioteca(Biblioteca b){

        List<Libro> catalogo = new ArrayList<>();
        Libro temp;
        for (Map.Entry<String, Integer[]> entry : b.getCopie().entrySet()) {
            temp = lDaoM.load(entry.getKey());
            if(temp == null){
                temp = lDaoD.load(entry.getKey());
                lDaoM.store(temp);
            }
            catalogo.add(temp);

        }

        b.setCatalogo(catalogo);

        List<Prenotazione> prenotazioniAttive = pDao.loadAllBiblioteca(b.getId());
        b.setPrenotazioniAttive(prenotazioniAttive);
        DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoBiblioteca().update(b);

        return b;

    }
}
