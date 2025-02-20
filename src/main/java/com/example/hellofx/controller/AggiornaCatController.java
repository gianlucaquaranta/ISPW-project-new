package com.example.hellofx.controller;

import com.example.hellofx.bean.GenereBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.bibliotecariodao.BibliotecarioDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Libro;
import com.example.hellofx.entity.entityfactory.LibroFactory;
import com.example.hellofx.exception.LibroGiaPresenteException;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;

import java.util.ArrayList;
import java.util.List;

public class AggiornaCatController {
    private Session session = BibliotecarioSession.getInstance();
    private LibroFactory libroFactory = LibroFactory.getInstance();
    private LibroDao libroDaoMemory = FactoryProducer.getFactory("memory").createDaoLibro();

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
                b.getCatalogo().add(this.beanToLibro(libroBean));
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
            LibroBean bean = this.libroToBean(l);
            Integer[] copie = b.getCopie().get(l.getIsbn());
            bean.setNumCopie(copie);
            list.add(bean);
        }
        return list;
    }

    public void add(LibroBean libroBean) throws Exception {
        Biblioteca b = session.getBiblioteca();
        Libro libroAggiunto = null;
        // Controllo se il libro è già nel catalogo della biblioteca
        if (b.isLibroInCatalogo(libroBean.getIsbn())) {
            throw new LibroGiaPresenteException("Il libro con ISBN " + libroBean.getIsbn() + " è già presente nel catalogo.");
        }

        boolean found = false;

        // Check in memoria
        for (Libro l : libroDaoMemory.loadAll()) {
            if (l.getIsbn().equals(libroBean.getIsbn())) {
                found = true;
                libroAggiunto = l;
                break;
            }
        }

        // Check in persistenza
        LibroDao libroDaoPers = null;
        if (session.isFull() && !found) {
            libroDaoPers = FactoryProducer.getFactory("daodbfactory").createDaoLibro();
            for (Libro l : libroDaoPers.loadAll()) {
                if (l.getIsbn().equals(libroBean.getIsbn())) {
                    found = true;
                    libroAggiunto = l;
                    break;
                }
            }
        }

        if (!found) {
            // Se il libro non esiste, lo creo e lo salvo
            libroAggiunto = this.beanToLibro(libroBean);
            libroDaoMemory.store(libroAggiunto);

            if (session.isFull() && libroDaoPers != null) {
                libroDaoPers.store(libroAggiunto);
            }
        }

        b.getCatalogo().add(libroAggiunto);
        session.setBiblioteca(b);
    }

    public void save(){
        BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory("memory").createDaoBiblioteca();
        BibliotecarioDao bibliotecarioDaoMemory = FactoryProducer.getFactory("memory").createDaoBibliotecario();

        bibliotecaDaoMemory.store(session.getBiblioteca());
        bibliotecarioDaoMemory.store(session.getBibliotecario());

        if(session.isFull()){
            BibliotecaDao bibliotecaDaoPers = FactoryProducer.getFactory("db").createDaoBiblioteca();
            BibliotecarioDao bibliotecarioDaoPers = FactoryProducer.getFactory("db").createDaoBibliotecario();

            bibliotecaDaoPers.store(session.getBiblioteca());
            bibliotecarioDaoPers.store(session.getBibliotecario());
        }
    }

    private Libro beanToLibro(LibroBean libroBean){
        Libro l = libroFactory.createLibro();
        l.setIsbn(libroBean.getIsbn());
        l.setTitolo(libroBean.getTitolo());
        l.setAutore(libroBean.getAutore());
        l.setEditore(libroBean.getEditore());
        l.setGenere(libroBean.getGenere().getNome());
        l.setAnnoPubblicazione(libroBean.getAnnoPubblicazione());
        return l;
    }

    private LibroBean libroToBean(Libro l){
        LibroBean lb = new LibroBean();
        lb.setIsbn(l.getIsbn());
        lb.setTitolo(l.getTitolo());
        lb.setAutore(l.getAutore());
        lb.setEditore(l.getEditore());
        lb.setAnnoPubblicazione(l.getAnnoPubblicazione());
        lb.setGenere(GenereBean.fromString(l.getGenere()));
        return lb;
    }
}