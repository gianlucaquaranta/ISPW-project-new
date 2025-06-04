package com.example.hellofx.controller;

import com.example.hellofx.bean.*;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.FactoryProducer;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.model.*;
import com.example.hellofx.model.modelfactory.FiltriFactory;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import com.example.hellofx.session.UtenteSession;

import java.time.LocalDate;
import java.util.*;

public class PLController {

        private Filtri filtriRicerca;
        private UtenteSession session = (UtenteSession) SessionManager.getSession();
        BibliotecaDao bibliotecaDao;
        private static final String MEM = "memory";
        private static final String FILE = "file";
        private static final String DB = "db";
        private List<Biblioteca> bibliotecheFiltrate;
        private List<Libro> libriFiltrati;
        private Map<Libro, List<Biblioteca>> risultatiFinali;
        private Libro libroSelezionato;
        private Biblioteca bibliotecaSelezionata;
        private PrenotazioneBean pb;


        public List<LibroBean> filtra(FiltriBean filtriBean) {

                filtriRicerca = FiltriFactory.getInstance().createFiltri(filtriBean.getTitolo(), filtriBean.getAutore(), filtriBean.getGenere(), filtriBean.getBiblioteca(), filtriBean.getIsbn(), filtriBean.getCap());
                String titolo = filtriRicerca.getTitolo();
                String autore = filtriRicerca.getAutore();
                String genere = filtriRicerca.getGenere();
                String isbn = filtriRicerca.getIsbn();
                String biblioteca = filtriRicerca.getBiblioteca();
                String cap = filtriRicerca.getCap();

                if(biblioteca==null && cap==null){ //L'utente deve inserire almeno un filtro che permetta di filtrare le biblioteche
                        return null; //handle exception ??
                } else if(titolo.isEmpty() && autore.isEmpty() && genere.isEmpty() && isbn.isEmpty()) return null; //handle exception ??


                if(Session.isFull()){
                        bibliotecaDao = FactoryProducer.getFactory(FILE).createDaoBiblioteca();
                } else {
                        bibliotecaDao = FactoryProducer.getFactory(MEM).createDaoBiblioteca();
                }

                List<Biblioteca> biblioteche = bibliotecaDao.loadAll();

                filtraBiblioteche(biblioteche);

                filtraLibri();

                List<LibroBean> libriBean = new ArrayList<>();
                for(Libro l: libriFiltrati){
                        libriBean.add(Converter.libroToBean(l));
                }

                return libriBean;

        }


        private void filtraBiblioteche(List<Biblioteca> biblioteche){

                boolean valida;

                bibliotecheFiltrate = new ArrayList<>();

                for (Biblioteca b : biblioteche) {
                        valida = true;

                        if (filtriRicerca.getBiblioteca() != null && !b.getNome().equalsIgnoreCase(filtriRicerca.getBiblioteca())) {
                                valida = false;
                        }

                        if (filtriRicerca.getCap() != null && !b.getPosizione().getCap().equals(filtriRicerca.getCap())) {
                                valida = false;
                        }
                        
                        if (valida) {
                                bibliotecheFiltrate.add(b);
                        }
                }
        }


        private void filtraLibri(){

                boolean almenoUno;

                libriFiltrati = new ArrayList<>();

                risultatiFinali = new HashMap<>();

                for (Biblioteca b : bibliotecheFiltrate) {
                        almenoUno = false;
                        for (Libro l : b.getCatalogo()) {
                                if(isBookValid(l,b)) {
                                        addToList(l);
                                        addToMap(l, b);
                                        almenoUno = true;
                                }

                        }
                        if(!almenoUno){
                                bibliotecheFiltrate.remove(b);
                        }
                }

        }


        public List<BibliotecaBean> getBiblioteche(LibroBean selezionato) {

                List<Biblioteca> bibliotecheRelative = new ArrayList<>();

                for(Libro l: risultatiFinali.keySet()){
                        if(l.getIsbn().equals(selezionato.getIsbn())){
                                bibliotecheRelative = risultatiFinali.get(l);
                                libroSelezionato = l;
                        }
                }

                List<BibliotecaBean> bibliotecheBean = new ArrayList<>();

                for(Biblioteca b: bibliotecheRelative){
                        bibliotecheBean.add(Converter.bibliotecaToBean(b));
                }

                return bibliotecheBean;
        }


        public PrenotazioneBean creaRiepilogo(BibliotecaBean selezionata) {

                //gestire utente loggato o meno

                LibroBean lb = Converter.libroToBean(libroSelezionato);

                for(Biblioteca b: risultatiFinali.get(libroSelezionato)){
                        if(b.getId().equals(selezionata.getIdBiblioteca())){
                            bibliotecaSelezionata = b;
                        }
                }

                LocalDate dataInizio = LocalDate.now();
                LocalDate dataScadenza = dataInizio.plusDays(15);

                pb = new PrenotazioneBean(null,dataInizio, dataScadenza, null, selezionata, lb);

                return pb;

        }


        public void registraPrenotazione(){

                Utente u = session.getUtente();
                UtenteBean ub = new UtenteBean(u.getUsername(), u.getEmail());
                pb.setUtente(ub);
                Prenotazione p = Converter.beanToPrenotazione(pb);

                //aggiungo la nuova prenotazione tra le prenotazioni attive dell'utente
                List<Prenotazione> pUtente = u.getPrenotazioniAttive();
                pUtente.add(p);
                u.setPrenotazioniAttive(pUtente);

                //aggiungo la nuova prenotazione tra le prenotazioni attive della biblioteca e decremento il numero di copie disponibili del libro prenotato
                bibliotecaSelezionata.getPrenotazioniAttive().add(p);
                (bibliotecaSelezionata.getCopie().get(libroSelezionato.getIsbn()))[1] --;

                PrenotazioneDao prenotazioneDao;
                UtenteDao utenteDao;


                if(Session.isFull()){
                        utenteDao = FactoryProducer.getFactory(DB).createDaoUtente();
                        if(Session.isFile()) {
                                prenotazioneDao = FactoryProducer.getFactory(FILE).createDaoPrenotazione();
                        }else{
                                prenotazioneDao = FactoryProducer.getFactory(DB).createDaoPrenotazione();
                        }
                } else {
                        utenteDao = FactoryProducer.getFactory(MEM).createDaoUtente();
                        prenotazioneDao = FactoryProducer.getFactory(MEM).createDaoPrenotazione();
                }


                prenotazioneDao.store(p);

                utenteDao.updateUtente(u);

                bibliotecaDao.update(bibliotecaSelezionata);
        }


        private void addToList(Libro libro) {

                for(Libro l : libriFiltrati){
                        if(l.getIsbn().equals(libro.getIsbn())){
                           return;
                        }
                }

                libriFiltrati.add(libro);


        }

        private void addToMap(Libro libro, Biblioteca biblioteca) {

                for(Libro l: risultatiFinali.keySet()){
                        if(l.getIsbn().equals(libro.getIsbn())){
                                risultatiFinali.get(l).add(biblioteca);
                                return;
                        }
                }

                List<Biblioteca> newEntry = new ArrayList<>();
                newEntry.add(biblioteca);
                risultatiFinali.put(libro, newEntry);
        }

        private boolean isLibroCorrispondente(Libro libro) {

                String titolo = filtriRicerca.getTitolo();
                String autore = filtriRicerca.getAutore();
                String genere = filtriRicerca.getGenere();
                String isbn = filtriRicerca.getIsbn();
                return (titolo == null || libro.getTitolo().toLowerCase().contains(titolo.toLowerCase())) &&
                        (autore == null || libro.getAutore().toLowerCase().contains(autore.toLowerCase())) &&
                        (genere == null || libro.getGenere().toLowerCase().contains(genere.toLowerCase())) &&
                        (isbn == null || libro.getIsbn().equalsIgnoreCase(isbn));
        }

        private boolean isBookValid(Libro l, Biblioteca b) {

                int disponibili = b.getCopie().get(l.getIsbn())[1];

                if(disponibili > 0){
                        return isLibroCorrispondente(l);
                }

                return false;
        }

}
