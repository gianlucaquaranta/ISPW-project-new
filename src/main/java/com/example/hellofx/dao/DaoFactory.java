package com.example.hellofx.dao;

import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;

public abstract class DaoFactory {

    public static synchronized DaoFactory getDaoFactory(PersistenceType type) {
        DaoFactory me;

        if (type == PersistenceType.MEMORY) {
            me = DaoMemoryFactory.getInstance();
        } else {
            me = new DaoHybridPersistenceFactory();
        }

        return me;
    }

    public abstract UtenteDao createDaoUtente();

    public abstract LibroDao createDaoLibro();

    public abstract PrenotazioneDao createDaoPrenotazione();

    public abstract BibliotecaDao createDaoBiblioteca();

    public abstract FiltriDao createDaoFiltri();

    public abstract NoleggioDao createDaoNoleggio();
}
