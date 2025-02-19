package com.example.hellofx.dao;

import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.filtridao.FiltriDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.dao.noleggiodao.NoleggioDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;

public interface DaoFactory {
    BibliotecarioDao createDaoBibliotecario();
    UtenteDao createDaoUtente();
    LibroDao createDaoLibro();
    PrenotazioneDao createDaoPrenotazione();
    BibliotecaDao createDaoBiblioteca();
    FiltriDao createDaoFiltri();
    NoleggioDao createDaoNoleggio();
}
