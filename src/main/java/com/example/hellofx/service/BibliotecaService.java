package com.example.hellofx.service;

import com.example.hellofx.dao.FactoryProducer;
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

    public Biblioteca load(String id, boolean isFile){

        BibliotecaDao bDao = FactoryProducer.getFactory("file").createDaoBiblioteca();
        LibroDao lDaoD = FactoryProducer.getFactory("db").createDaoLibro();
        LibroDao lDaoM = FactoryProducer.getFactory("memory").createDaoLibro();
        PrenotazioneDao pDao;
        if(isFile){
            pDao = FactoryProducer.getFactory("file").createDaoPrenotazione();
        } else {
            pDao = FactoryProducer.getFactory("db").createDaoPrenotazione();
        }

        Biblioteca b = bDao.loadOne(id); //istanza di biblioteca senza catalogo, prenotazioniAttive, noleggiAttivi

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

        return b;
    }
}
