package com.example.hellofx.controller;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.DaoFactory;
import com.example.hellofx.dao.PersistenceType;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.librodao.LibroDao;
import com.example.hellofx.exception.CopieDisponibiliException;
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
    private Session session = SessionManager.getSession();
    private LibroDao libroDaoMemory = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoLibro();

    public void delete(LibroBean libroBean){
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        b.getCatalogo().removeIf(l -> l.getIsbn().equals(libroBean.getIsbn()));
        b.getCopie().remove(libroBean.getIsbn());
    }

    public void update(LibroBean libroBean) throws CopieDisponibiliException{
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();

        for(Libro l : b.getCatalogo()){
            if(l.getIsbn().equals(libroBean.getIsbn())){
                int copiePrenotate = b.getCopie().get(l.getIsbn())[0] - b.getCopie().get(l.getIsbn())[1];

                if(libroBean.getNumCopie()[0] >= copiePrenotate) { //copie disponibili >= copie già prenotate
                    b.getCatalogo().removeIf(libro -> libro.getIsbn().equals(libroBean.getIsbn()));
                    b.getCatalogo().add(this.retrieveLibro(libroBean, Session.isFull()));
                    Integer[] copie = {libroBean.getNumCopie()[0], b.getCopie().get(l.getIsbn())[1]}; //set nuovo numero di copie totali e vecchio numero di copie disponibili
                    b.getCopie().replace(l.getIsbn(), copie);
                    break;
                } else {
                    throw new CopieDisponibiliException(copiePrenotate);
                }
            }
        }
    }

    public List<LibroBean> getCatalogo(){
        List<LibroBean> list = new ArrayList<>();
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        for(Libro l : b.getCatalogo()){
            LibroBean bean = Converter.libroToBean(l);
            Integer[] copie = b.getCopie().get(l.getIsbn());
            bean.setNumCopie(copie);
            list.add(bean);
        }
        return list;
    }

    public void add(LibroBean libroBean) throws LibroGiaPresenteException {
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        // Controlla se il libro è già nel catalogo della biblioteca
        if (b.getLibroByIsbn(libroBean.getIsbn()) != null) {
            throw new LibroGiaPresenteException(libroBean.getIsbn());
        }

        Libro l = this.retrieveLibro(libroBean, Session.isFull());
        b.getCatalogo().add(l);
        Integer[] copie = {libroBean.getNumCopie()[0], libroBean.getNumCopie()[1]};
        b.getCopie().replace(l.getIsbn(), copie);
    }

    public List<LibroBean> searchByField(String field, String fieldType) {
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

        if (extractor == null) return this.getCatalogo(); // fieldType Mostra tutti

        for (Libro l : list) {
            if (extractor.apply(l).equals(field)) {
                results.add(Converter.libroToBean(l));
            }
        }
        return results;
    }

    public void orderByIsbn(List<LibroBean> catalogo, String order){
        if(order.equalsIgnoreCase("decrescente")) {
            catalogo.sort(Comparator.comparing(LibroBean::getIsbn).reversed());
        } else { //default: ordine crescente
            catalogo.sort(Comparator.comparing(LibroBean::getIsbn));
        }
    }

    public void save(){
        Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();
        BibliotecaDao bibliotecaDaoMemory = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoBiblioteca();
        bibliotecaDaoMemory.update(b);

        if(Session.isFull()){
            BibliotecaDao bibliotecaDaoPers = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoBiblioteca();
            bibliotecaDaoPers.update(((BibliotecarioSession)session).getBiblioteca());
        }
    }

    private Libro retrieveLibro(LibroBean libroBean, boolean isFull){
        Libro l = libroDaoMemory.load(libroBean.getIsbn());
        if(l != null){ // il libro è in memoria
            return l;
        } else if(isFull){
            LibroDao libroDaoPers = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoLibro();
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

    public static void validateLibroBean(LibroBean bean) throws IllegalArgumentException {
        StringBuilder missingFields = new StringBuilder();

        if (bean.getTitolo() == null || bean.getTitolo().isBlank())
            missingFields.append("- Titolo\n");

        if (bean.getAutore() == null || bean.getAutore().isBlank())
            missingFields.append("- Autore\n");

        if (bean.getIsbn() == null || bean.getIsbn().isBlank())
            missingFields.append("- ISBN\n");

        if (bean.getEditore() == null || bean.getEditore().isBlank())
            missingFields.append("- Editore\n");

        if (bean.getGenere() == null)
            missingFields.append("- Genere\n");

        if (bean.getAnnoPubblicazione() <= 0)
            missingFields.append("- Anno pubblicazione\n");

        Integer[] copie = bean.getNumCopie();
        if (copie == null || copie.length < 2 || copie[0] == null || copie[0] < 0)
            missingFields.append("- Numero di copie\n");

        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException("I seguenti campi sono mancanti o invalidi:\n" + missingFields);
        }
    }


}