package com.example.hellofx.dao;

import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDaoMemory;
import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.filtridao.FiltriDaoMemory;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.librodao.LibroDaoMemory;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDaoMemory;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDaoMemory;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.dao.utentedao.UtenteDaoMemory;

public class DaoMemoryFactory extends DaoFactory {
    private static DaoMemoryFactory instance = null;

    public static DaoMemoryFactory getInstance() {
        if (instance == null) {
            instance = new DaoMemoryFactory();
        }
        return instance;
    }

    private DaoMemoryFactory() {}


    @Override
    public UtenteDao createDaoUtente() {
        return UtenteDaoMemory.getInstance();
    }

    @Override
    public LibroDao createDaoLibro() {
        return LibroDaoMemory.getInstance();
    }

    @Override
    public PrenotazioneDao createDaoPrenotazione() {
        return PrenotazioneDaoMemory.getInstance();
    }

    @Override
    public BibliotecaDao createDaoBiblioteca() {
        return BibliotecaDaoMemory.getInstance();
    }

    @Override
    public FiltriDao createDaoFiltri() {
        return FiltriDaoMemory.getInstance();
    }

    @Override
    public NoleggioDao createDaoNoleggio() {
        return NoleggioDaoMemory.getInstance();
    }
}
