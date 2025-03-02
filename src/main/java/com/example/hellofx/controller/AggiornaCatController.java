package com.example.hellofx.controller;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Libro;
import com.example.hellofx.exception.LibroGiaPresenteException;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;

import java.util.ArrayList;
import java.util.List;

public class AggiornaCatController {
    private static final String MEMORY = "memory";
    private Session session = BibliotecarioSession.getInstance();
    private LibroDao libroDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoLibro();

    public void delete(LibroBean libroBean){
        Biblioteca b = session.getBiblioteca();
        b.getCatalogo().removeIf(l -> l.getIsbn().equals(libroBean.getIsbn()));
        session.setBiblioteca(b);
    }

    public void update(LibroBean libroBean){
        Biblioteca b = session.getBiblioteca();
        for(Libro l : b.getCatalogo()){
            if(l.getIsbn().equals(libroBean.getIsbn())){
                b.getCatalogo().removeIf(libro -> libro.getIsbn().equals(libroBean.getIsbn()));
                b.getCatalogo().add(this.retrieveLibro(libroBean, session.isFull()));
                Integer[] copie = {libroBean.getNumCopie()[0], libroBean.getNumCopie()[1]};
                b.getCopie().replace(l.getIsbn(), copie);
                break;
            }
        }
        session.setBiblioteca(b);
    }

    public List<LibroBean> getCatalogo(){
        List<LibroBean> list = new ArrayList<>();
        Biblioteca b = session.getBiblioteca();
        for(Libro l : b.getCatalogo()){
            LibroBean bean = Converter.libroToBean(l);
            Integer[] copie = b.getCopie().get(l.getIsbn());
            bean.setNumCopie(copie);
            list.add(bean);
        }
        return list;
    }

    public void add(LibroBean libroBean) throws Exception {
        Biblioteca b = session.getBiblioteca();

        // Controllo se il libro è già nel catalogo della biblioteca
        if (b.isLibroInCatalogo(libroBean.getIsbn())) {
            throw new LibroGiaPresenteException("Il libro con ISBN " + libroBean.getIsbn() + " è già presente nel catalogo.");
        }

        Libro l = this.retrieveLibro(libroBean, session.isFull());
        b.getCatalogo().add(l);
        session.setBiblioteca(b);
    }

    public void save(){
    BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();
        BibliotecarioDao bibliotecarioDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBibliotecario();

        bibliotecaDaoMemory.store(session.getBiblioteca());
        bibliotecarioDaoMemory.store(session.getBibliotecario());

        if(Session.isFull()){
            BibliotecaDao bibliotecaDaoPers = FactoryProducer.getFactory("db").createDaoBiblioteca();
            BibliotecarioDao bibliotecarioDaoPers = FactoryProducer.getFactory("db").createDaoBibliotecario();

            bibliotecaDaoPers.store(session.getBiblioteca());
            bibliotecarioDaoPers.store(session.getBibliotecario());
        }
    }

    private Libro retrieveLibro(LibroBean libroBean, boolean isFull){
        Libro l = libroDaoMemory.load(libroBean.getIsbn());
        if(l != null){ // il libro è in memoria
            return l;
        } else if(isFull){
            LibroDao libroDao = FactoryProducer.getFactory("db").createDaoLibro();
            l = libroDao.load(libroBean.getIsbn());
            if(l == null) { // il libro non è in memoria nè in persistenza
                l = Converter.beanToLibro(libroBean);
                libroDaoMemory.store(l);
                libroDao.store(l);
                return l;
            } else return l; // il libro è in persistenza

        } else { // il libro non è presente nel sistema (demo version)
            l = Converter.beanToLibro(libroBean);
            libroDaoMemory.store(l);
        }
        return l;
    }
}