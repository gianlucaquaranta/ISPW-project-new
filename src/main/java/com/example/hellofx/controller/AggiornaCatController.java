package com.example.hellofx.controller;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Libro;
import com.example.hellofx.exception.LibroGiaPresenteException;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AggiornaCatController {
    private static final String MEMORY = "memory";
    private Session session = SessionManager.getSession();
    private LibroDao libroDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoLibro();

    public void delete(LibroBean libroBean){
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        b.getCatalogo().removeIf(l -> l.getIsbn().equals(libroBean.getIsbn()));
        b.getCopie().remove(libroBean.getIsbn());
    }

    public void update(LibroBean libroBean){
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        for(Libro l : b.getCatalogo()){
            if(l.getIsbn().equals(libroBean.getIsbn())){
                b.getCatalogo().removeIf(libro -> libro.getIsbn().equals(libroBean.getIsbn()));
                b.getCatalogo().add(this.retrieveLibro(libroBean, Session.isFull()));
                Integer[] copie = {libroBean.getNumCopie()[0], libroBean.getNumCopie()[1]};
                b.getCopie().replace(l.getIsbn(), copie);
                break;
            }
        }
    }

    public List<LibroBean> getCatalogo(String order){
        List<LibroBean> list = new ArrayList<>();
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        for(Libro l : b.getCatalogo()){
            LibroBean bean = Converter.libroToBean(l);
            Integer[] copie = b.getCopie().get(l.getIsbn());
            bean.setNumCopie(copie);
            list.add(bean);
        }
        this.orderByIsbn(list, order);
        return list;
    }

    public void add(LibroBean libroBean) throws Exception {
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();

        // Controlla se il libro è già nel catalogo della biblioteca
        if (b.isLibroInCatalogo(libroBean.getIsbn())) {
            throw new LibroGiaPresenteException("Il libro con ISBN " + libroBean.getIsbn() + " è già presente nel catalogo.");
        }

        Libro l = this.retrieveLibro(libroBean, Session.isFull());
        b.getCatalogo().add(l);
        Integer[] copie = {libroBean.getNumCopie()[0], libroBean.getNumCopie()[1]};
        b.getCopie().replace(l.getIsbn(), copie);
    }

    public List<LibroBean> searchByField(String field, String fieldType, String order) {
        List<Libro> list = ((BibliotecarioSession)session).getBiblioteca().getCatalogo();
        List<LibroBean> results = new ArrayList<>();

        Map<String, Function<Libro, String>> fieldExtractors = Map.of(
                "isbn", Libro::getIsbn,
                "autore", Libro::getAutore,
                "titolo", Libro::getTitolo,
                "genere", Libro::getGenere,
                "editore", Libro::getEditore
        );

        Function<Libro, String> extractor = fieldExtractors.get(fieldType.toLowerCase());

        if (extractor == null) return results; // fieldType non valido

        for (Libro l : list) {
            if (extractor.apply(l).equals(field)) {
                results.add(Converter.libroToBean(l));
            }
        }

        this.orderByIsbn(results, order);
        return results;
    }

    public void save(){
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        BibliotecaDao bibliotecaDaoMemory = FactoryProducer.getFactory(MEMORY).createDaoBiblioteca();
        bibliotecaDaoMemory.update(b);

        if(Session.isFull()){
            BibliotecaDao bibliotecaDaoPers = FactoryProducer.getFactory("db").createDaoBiblioteca();
            bibliotecaDaoPers.update(((BibliotecarioSession)session).getBiblioteca());
        }
    }

    private void orderByIsbn(List<LibroBean> catalogo, String order){ //fallo lavorare col cat di model
        if(order.equalsIgnoreCase("decrescente")) {
            catalogo.sort(Comparator.comparing(LibroBean::getIsbn).reversed());
        } else { //default: ordine crescente
            catalogo.sort(Comparator.comparing(LibroBean::getIsbn));
        }
    }

    private Libro retrieveLibro(LibroBean libroBean, boolean isFull){
        Libro l = libroDaoMemory.load(libroBean.getIsbn());
        if(l != null){ // il libro è in memoria
            return l;
        } else if(isFull){
            LibroDao libroDaoPers = FactoryProducer.getFactory("db").createDaoLibro();
            l = libroDaoPers.load(libroBean.getIsbn());
            if(l == null) { // il libro non è in memoria né in persistenza
                l = Converter.beanToLibro(libroBean);
                libroDaoMemory.store(l);
                libroDaoPers.store(l);
                return l;
            } else return l; // il libro è in persistenza

        } else { // il libro non è presente nel sistema (demo version)
            l = Converter.beanToLibro(libroBean);
            libroDaoMemory.store(l);
        }
        return l;
    }

}