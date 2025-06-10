package com.example.hellofx.controller;

import com.example.hellofx.bean.*;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.DaoFactory;
import com.example.hellofx.dao.PersistenceType;
import com.example.hellofx.dao.bibliotecadao.BibliotecaDao;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDao;
import com.example.hellofx.dao.utentedao.UtenteDao;
import com.example.hellofx.exception.EmptyFiltersException;
import com.example.hellofx.exception.PrenotazioneGiaPresenteException;
import com.example.hellofx.exception.UserNotLoggedException;
import com.example.hellofx.model.*;
import com.example.hellofx.model.modelfactory.FiltriFactory;
import com.example.hellofx.service.BibliotecaService;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;
import com.example.hellofx.session.UtenteSession;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class PLController {

        private Filtri filtriRicerca;
        private UtenteSession session = (UtenteSession) SessionManager.getSession();
        BibliotecaDao bibliotecaDao;
        private static final String REGEX = "[\\s,]+";
        private List<Biblioteca> bibliotecheFiltrate = new ArrayList<>();
        private List<Libro> libriFiltrati = new ArrayList<>();
        private Map<Libro, List<Biblioteca>> risultatiFinali = new HashMap<>();
        private Libro libroSelezionato;
        private Biblioteca bibliotecaSelezionata;
        private PrenotazioneBean pb;


        public List<LibroBean> filtra(FiltriBean filtriBean) throws EmptyFiltersException{

                filtriRicerca = FiltriFactory.getInstance().createFiltri(filtriBean.getTitolo(), filtriBean.getAutore(), filtriBean.getGenere(), filtriBean.getBiblioteca(), filtriBean.getIsbn(), filtriBean.getCap());
                String titolo = filtriRicerca.getTitolo();
                String autore = filtriRicerca.getAutore();
                String genere = filtriRicerca.getGenere();
                String isbn = filtriRicerca.getIsbn();
                String biblioteca = filtriRicerca.getBiblioteca();
                String cap = filtriRicerca.getCap();

                //Controlli sui filtri
                if(biblioteca.isBlank() && cap.isBlank() && titolo.isBlank() && autore.isBlank() && genere.isBlank() && isbn.isBlank()){
                        throw new EmptyFiltersException("Inserisci almeno un filtro relativo ai libri ed uno relativo alle biblioteche");
                }
                if(biblioteca.isBlank() && cap.isBlank()){ //L'utente deve inserire almeno un filtro che permetta di filtrare le biblioteche
                        throw new EmptyFiltersException("Inserisci almeno un filtro tra CAP e Biblioteca"); //handle exception ??
                } else if(titolo.isBlank() && autore.isBlank() && genere.isBlank() && isbn.isBlank()) {
                        throw new EmptyFiltersException("Inserisci almeno un filtro relativo ai libri");
                }

                List<Biblioteca> biblioteche;
                if(Session.isFull()){
                        biblioteche = BibliotecaService.loadAll();
                        bibliotecaDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoBiblioteca();
                } else {
                        bibliotecaDao = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoBiblioteca();
                        biblioteche = bibliotecaDao.loadAll();
                }

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

                for (Biblioteca b : biblioteche) {
                        valida = true;

                        if (!filtriRicerca.getBiblioteca().isEmpty() && !(b.getNome().equalsIgnoreCase(filtriRicerca.getBiblioteca()))) {
                                valida = false;
                        }

                        if (!filtriRicerca.getCap().isEmpty() && !(b.getPosizione().getCap().equals(filtriRicerca.getCap()))) {
                                valida = false;
                        }

                        if (valida) {
                                bibliotecheFiltrate.add(b);
                        }
                }
        }

        private void filtraLibri() {
                Iterator<Biblioteca> iterator = bibliotecheFiltrate.iterator();

                while (iterator.hasNext()) {
                        Biblioteca b = iterator.next();
                        boolean almenoUno = false;

                        for (Libro l : b.getCatalogo()) {
                                if (isBookValid(l, b)) {
                                        addToList(l);
                                        addToMap(l, b);
                                        almenoUno = true;
                                }
                        }

                        if (!almenoUno) {
                                iterator.remove(); // Rimozione sicura durante l'iterazione
                        }
                }
        }


        public List<BibliotecaBean> getBiblioteche(LibroBean selezionato) {

                if (selezionato == null) {
                        return new ArrayList<>();
                }

                List<Biblioteca> bibliotecheRelative = new ArrayList<>();

                if (risultatiFinali == null) {
                        return new ArrayList<>();
                }

                for (Map.Entry<Libro, List<Biblioteca>> entry : risultatiFinali.entrySet()) {
                        Libro l = entry.getKey();
                        if (l.getIsbn().equals(selezionato.getIsbn())) {
                                bibliotecheRelative = risultatiFinali.get(l);
                                libroSelezionato = l;

                                break;
                        }
                }

                List<BibliotecaBean> bibliotecheBean = new ArrayList<>();

                for (Biblioteca b : bibliotecheRelative) {
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

                LocalDateTime dataInizio = LocalDateTime.now();
                LocalDateTime dataScadenza = dataInizio.plusDays(15);

                pb = new PrenotazioneBean(null,dataInizio, dataScadenza, null, selezionata, lb);

                return pb;

        }


        public void registraPrenotazione() throws UserNotLoggedException, PrenotazioneGiaPresenteException{

                Utente u = session.getUtente();
                if(u==null){
                        throw new UserNotLoggedException();
                }
                UtenteBean ub = new UtenteBean(u.getUsername(), u.getEmail());
                pb.setUtente(ub);
                Prenotazione p = Converter.beanToPrenotazione(pb);

                for (Prenotazione prenotazione : u.getPrenotazioniAttive()) {
                        if (prenotazione.getIdPrenotazione().equals(p.getIdPrenotazione())) {
                                throw new PrenotazioneGiaPresenteException(pb.getIsbn(), pb.getBiblioteca());
                        }
                }

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
                        utenteDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoUtente();
                        prenotazioneDao = DaoFactory.getDaoFactory(PersistenceType.PERSISTENCE).createDaoPrenotazione();
                } else {
                        utenteDao = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoUtente();
                        prenotazioneDao = DaoFactory.getDaoFactory(PersistenceType.MEMORY).createDaoPrenotazione();
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

                if (!risultatiFinali.entrySet().isEmpty()) {
                        for (Map.Entry<Libro, List<Biblioteca>> entry : risultatiFinali.entrySet()) {
                                Libro l = entry.getKey();

                                if (l.getIsbn().equals(libro.getIsbn())) {
                                        risultatiFinali.get(l).add(biblioteca);
                                        return;
                                }
                        }
                }

                List<Biblioteca> newEntry = new ArrayList<>();
                newEntry.add(biblioteca);
                risultatiFinali.put(libro, newEntry);
        }

        private boolean isBookValid(Libro l, Biblioteca b) {

                int disponibili = b.getCopie().get(l.getIsbn())[1];
                if(disponibili > 0){
                        return isLibroCorrispondente(l);
                }

                return false;
        }

        private boolean isLibroCorrispondente(Libro libro) {
                String titolo = formatString(filtriRicerca.getTitolo());
                String autore = formatString(filtriRicerca.getAutore());
                String genere = formatString(filtriRicerca.getGenere());
                String isbn = formatString(filtriRicerca.getIsbn());

                String libroTitolo = formatString(libro.getTitolo());
                String libroAutore = formatString(libro.getAutore());
                String libroGenere = formatString(libro.getGenere());
                String libroIsbn = formatString(libro.getIsbn());

                boolean titoloMatch = stringMatch(titolo, libroTitolo);
                boolean autoreMatch = stringMatch(autore, libroAutore);
                boolean genereMatch = genere.isBlank() || libroGenere.equals(genere);
                boolean isbnMatch = isbn.isEmpty() || libroIsbn.equalsIgnoreCase(isbn);

                return titoloMatch && autoreMatch && genereMatch && isbnMatch;
        }

        private String formatString(String s) {
                return s.toLowerCase().replaceAll(REGEX, "");
        }

        public static boolean stringMatch(String s1, String s2) {
                if(s1.isEmpty() || s1.equals(s2)) return true;

                // Normalizzo (puoi adattare a caso o spazi, se vuoi)
                s1 = s1.toLowerCase();
                s2 = s2.toLowerCase();

                int maxLength = Math.max(s1.length(), s2.length());
                int requiredCommon = (int) Math.ceil((2.0 / 3) * maxLength);

                // Conta frequenze lettere per entrambe le stringhe
                int[] freq1 = new int[26];
                int[] freq2 = new int[26];

                for (char c : s1.toCharArray()) {
                        if (c >= 'a' && c <= 'z') {
                                freq1[c - 'a']++;
                        }
                }

                for (char c : s2.toCharArray()) {
                        if (c >= 'a' && c <= 'z') {
                                freq2[c - 'a']++;
                        }
                }

                // Calcola lettere comuni (min freq per ogni lettera)
                int commonLetters = 0;
                for (int i = 0; i < 26; i++) {
                        commonLetters += Math.min(freq1[i], freq2[i]);
                }

                return commonLetters >= requiredCommon;
        }

}
