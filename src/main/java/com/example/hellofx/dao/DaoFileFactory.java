package com.example.hellofx.dao;

import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDaoFile;
import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDaoFile;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.dao.utentedao.UtenteDaoFile;

public class DaoFileFactory implements DaoFactory {
    private static DaoFileFactory instance = null;

    private DaoFileFactory() {}
    public static DaoFileFactory getInstance() {
        if (instance == null) {
            instance = new DaoFileFactory();
        }
        return instance;
    }


    @Override
    public UtenteDao createDaoUtente() {
        return new UtenteDaoFile();
    }

    @Override
    public LibroDao createDaoLibro() {
        return null;
    }

    @Override
    public PrenotazioneDao createDaoPrenotazione() {
        return new PrenotazioneDaoFile();
    }

    @Override
    public BibliotecaDao createDaoBiblioteca() {
        return new BibliotecaDaoFile();
    }

    @Override
    public FiltriDao createDaoFiltri() {
        return null;
    }

    @Override
    public NoleggioDao createDaoNoleggio() {
        return null;
    }
}
