package com.example.hellofx.dao;

import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.session.Session;

public class DaoHybridPersistenceFactory extends DaoFactory {
    private DaoFileFactory fileFactory;
    private DaoDbFactory dbFactory;

    public DaoHybridPersistenceFactory() {
        this.fileFactory = DaoFileFactory.getInstance();
        this.dbFactory = DaoDbFactory.getInstance();
    }

    @Override
    public UtenteDao createDaoUtente() {
        return dbFactory.createDaoUtente();
    }

    @Override
    public LibroDao createDaoLibro() {
        return dbFactory.createDaoLibro();
    }

    @Override
    public PrenotazioneDao createDaoPrenotazione() { //scelta in base alla sessione
        if (Session.isFile()) {
            return fileFactory.createDaoPrenotazione();
        } else {
            return dbFactory.createDaoPrenotazione();
        }
    }

    @Override
    public BibliotecaDao createDaoBiblioteca() {
        return fileFactory.createDaoBiblioteca();
    }

    @Override
    public FiltriDao createDaoFiltri() {
        return dbFactory.createDaoFiltri(); // esempio
    }

}