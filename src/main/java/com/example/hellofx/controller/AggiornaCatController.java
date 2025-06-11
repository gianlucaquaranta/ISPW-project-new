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

import java.time.Year;
import java.util.*;
import java.util.function.Function;

public class AggiornaCatController {
    private final Session session = SessionManager.getSession();
    private final LibroDao libroDaoMemory = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoLibro();

    private final Biblioteca b = ((BibliotecarioSession)session).getBiblioteca();

    public void delete(LibroBean libroBean) {
        b.getCatalogo().removeIf(l -> l.getIsbn().equals(libroBean.getIsbn()));
        b.getCopie().remove(libroBean.getIsbn());

        if (Session.isFull()) {
            BibliotecaDao bibliotecaDaoPers = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoBiblioteca();
            bibliotecaDaoPers.update(b);
        }
    }

    public void update(LibroBean libroBean) throws CopieDisponibiliException {
        for (Libro l : b.getCatalogo()) {
            if (l.getIsbn().equals(libroBean.getIsbn())) {
                int copiePrenotate = b.getCopie().get(l.getIsbn())[0] - b.getCopie().get(l.getIsbn())[1];

                if (libroBean.getNumCopie()[0] >= copiePrenotate) { //copie disponibili >= copie già prenotate
                    b.getCatalogo().removeIf(libro -> libro.getIsbn().equals(libroBean.getIsbn()));
                    Libro libro = Converter.beanToLibro(libroBean);
                    b.getCatalogo().add(libro);
                    Integer[] copie = {libroBean.getNumCopie()[0], libroBean.getNumCopie()[0]-copiePrenotate}; //set nuovo numero di copie totali e disponibili
                    b.getCopie().replace(libro.getIsbn(), copie);

                    if (Session.isFull()) {
                        BibliotecaDao bibliotecaDaoPers = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoBiblioteca();
                        bibliotecaDaoPers.update(b);
                    }

                    break;
                } else {
                    throw new CopieDisponibiliException(copiePrenotate);
                }
            }
        }
    }

    public List<LibroBean> getCatalogo() {
        List<LibroBean> list = new ArrayList<>();
        for (Libro l : b.getCatalogo()) {
            LibroBean bean = Converter.libroToBean(l);
            Integer[] copie = b.getCopie().get(l.getIsbn());
            bean.setNumCopie(copie);
            list.add(bean);
        }
        return list;
    }

    public void add(LibroBean libroBean) throws LibroGiaPresenteException {
        // Controlla se il libro è già nel catalogo della biblioteca
        if (b.getLibroByIsbn(libroBean.getIsbn()) != null) {
            throw new LibroGiaPresenteException(libroBean.getIsbn());
        }

        Libro l = this.retrieveLibro(libroBean, Session.isFull());
        b.getCatalogo().add(l);
        Integer[] copie = {libroBean.getNumCopie()[0], libroBean.getNumCopie()[0]};
        b.getCopie().put(l.getIsbn(), copie);

        if (Session.isFull()) {
            BibliotecaDao bibliotecaDaoPers = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoBiblioteca();
            bibliotecaDaoPers.update(b);
        }
    }

    public List<LibroBean> searchByField(String field, String fieldType) {
        List<Libro> list = b.getCatalogo();
        List<LibroBean> results = new ArrayList<>();

        Map<String, Function<Libro, String>> fieldExtractors = Map.of(
                "isbn", Libro::getIsbn,
                "autore", Libro::getAutore,
                "titolo", Libro::getTitolo,
                "genere", Libro::getGenere,
                "editore", Libro::getEditore
        );

        Function<Libro, String> extractor = fieldExtractors.get(fieldType.toLowerCase());

        if (extractor == null || field.isBlank()) return this.getCatalogo(); // mostra tutto

        // rimuove spazi e virgole e mette tutto in minuscolo
        String cleanedField = formatString(field);

        for (Libro l : list) {
            String fieldValue = extractor.apply(l);
            if (fieldValue != null) {
                String cleanedValue = formatString(fieldValue);
                if (cleanedValue.contains(cleanedField)) {
                    LibroBean lb = Converter.libroToBean(l);
                    lb.setNumCopie(b.getCopie().get(l.getIsbn()));
                    results.add(lb);
                }
            }
        }

        return results;
    }


    public void orderByIsbn(List<LibroBean> catalogo, String order) {
        if (order.equalsIgnoreCase("decrescente")) {
            catalogo.sort(Comparator.comparing(LibroBean::getIsbn).reversed());
        } else { //default: ordine crescente
            catalogo.sort(Comparator.comparing(LibroBean::getIsbn));
        }
    }

    private Libro retrieveLibro(LibroBean libroBean, boolean isFull) {
        Libro l = libroDaoMemory.load(libroBean.getIsbn(), b.getId());

        if (l != null) { // il libro è in memoria
            return l;
        } else if (isFull) {
            LibroDao libroDaoPers = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoLibro();
            l = libroDaoPers.load(libroBean.getIsbn(), b.getId());

            if (l == null) { // il libro non è in memoria né in persistenza
                l = Converter.beanToLibro(libroBean);
                libroDaoMemory.store(l, b.getId());
                libroDaoPers.store(l, b.getId());
                return l;
            } else return l; // il libro è in persistenza

        } else { // il libro non è presente nel sistema (demo version)
            l = Converter.beanToLibro(libroBean);
            libroDaoMemory.store(l, b.getId());
        }
        return l;
    }

    public void validateLibroBean(LibroBean bean) throws IllegalArgumentException {

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

        if (bean.getAnnoPubblicazione() <= 0 || bean.getAnnoPubblicazione() >= Year.now().getValue())
            missingFields.append("- Anno pubblicazione\n");

        Integer[] copie = bean.getNumCopie();
        if (copie == null || copie.length < 2 || copie[0] == null || copie[0] < 0)
            missingFields.append("- Numero di copie\n");

        if (!missingFields.isEmpty()) {
            throw new IllegalArgumentException("I seguenti campi sono mancanti o invalidi:\n" + missingFields);
        }
    }

    private String formatString(String s){
        return s.toLowerCase().replaceAll("[\\s,]+", "");
    }

}