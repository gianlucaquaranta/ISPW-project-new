package com.example.hellofx.dao;

import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDaoDb;
import com.example.hellofx.dao.filtridao.FiltriDaoDb;
import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.librodao.LibroDaoDb;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDaoDb;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDaoDb;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.dao.utentedao.UtenteDaoDb;

public class DaoDbFactory implements DaoFactory {

    public static DaoDbFactory instance = null;

    public static DaoDbFactory getInstance() {
        if (instance == null) {
            instance = new DaoDbFactory();
        }
        return instance;
    }

    private DaoDbFactory() {}

    @Override
    public BibliotecarioDao createDaoBibliotecario() {
        return new BibliotecarioDaoDb();
    }

    @Override
    public UtenteDao createDaoUtente() {
        return new UtenteDaoDb();
    }

    @Override
    public LibroDao createDaoLibro() {
        return new LibroDaoDb();
    }

    @Override
    public PrenotazioneDao createDaoPrenotazione() {
        return new PrenotazioneDaoDb();
    }

    @Override
    public BibliotecaDao createDaoBiblioteca() {
        return null;
    }
    @Override
    public FiltriDao createDaoFiltri() {
        return new FiltriDaoDb();
    }

    @Override
    public NoleggioDao createDaoNoleggio() {
        return new NoleggioDaoDb();
    }
}
