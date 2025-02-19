package com.example.hellofx.cli;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliManager {

    private static final Map<String, Map<String, String>> cliMap = new HashMap<>();

    private static final Map<String, String> loginMap = new HashMap<>();
    private static final Map<String, String> regMap = new HashMap<>();
    private static final Map<String, String> homeBibMap = new HashMap<>();
    private static final Map<String, String> homeUtenteMap= new HashMap<>();
    private static final Map<String, String> catalogoMap = new HashMap<>();
    private static final Map<String, String> nolBibMap = new HashMap<>();
    private static final Map<String, String> prenBibMap = new HashMap<>();
    private static final Map<String, String> nolUtenteMap = new HashMap<>();
    private static final Map<String, String> prenUtenteMap = new HashMap<>();
    private static final Map<String, String> prenLibroMap = new HashMap<>();
    private static final Map<String, String> prezziMap = new HashMap<>();
    private static final Map<String, String> profUtenteMap = new HashMap<>();

    private CliLogin cliLogin;
    private CliRegistrazione cliRegistrazione;

    private CliHomeBibliotecario cliHomeBibliotecario;
    private CliCatalogo cliCatalogo;
    private CliNoleggiBibliotecario cliNoleggiBibliotecario;
    private CliPrenotazioniBibliotecario cliPrenotazioniBibliotecario;

    private CliHomeUtente cliHomeUtente;
    private CliPrenotaLibro cliPrenotaLibro;
    private CliNoleggiUtente cliNoleggiUtente;
    private CliPrenotazioniUtente cliPrenotazioniUtente;
    private CliTrovaPrezzi cliTrovaPrezzi;
    private CliProfiloUtente cliProfiloUtente;

    public CliManager() {
        this.cliLogin = new CliLogin();
        this.cliRegistrazione = new CliRegistrazione();

        this.cliHomeBibliotecario = new CliHomeBibliotecario();
        this.cliCatalogo = new CliCatalogo();
        this.cliNoleggiBibliotecario = new CliNoleggiBibliotecario();
        this.cliPrenotazioniBibliotecario = new CliPrenotazioniBibliotecario();

        this.cliHomeUtente = new CliHomeUtente();
        this.cliNoleggiUtente = new CliNoleggiUtente();
        this.cliPrenotazioniUtente = new CliPrenotazioniUtente();
        this.cliPrenotaLibro = new CliPrenotaLibro();
        this.cliTrovaPrezzi = new CliTrovaPrezzi();
        this.cliProfiloUtente = new CliProfiloUtente();

        //costruzione di loginMap
        loginMap.put("bibliotecario", this.cliHomeBibliotecario.getClass().getName());
        loginMap.put("utente", this.cliHomeUtente.getClass().getName());

        //costruzione di regMap
        regMap.put("UtenteBean", this.cliHomeUtente.getClass().getName());
        regMap.put("BibliotecarioBean", this.cliHomeBibliotecario.getClass().getName());
        regMap.put("2", this.cliLogin.getClass().getName());

        //BIBLIOTECARIO
        //costruzione di homeBibMap
        homeBibMap.put("0", this.cliCatalogo.getClass().getName());
        homeBibMap.put("1", this.cliNoleggiBibliotecario.getClass().getName());
        homeBibMap.put("2", this.cliPrenotazioniBibliotecario.getClass().getName());
        homeBibMap.put("3", this.cliLogin.getClass().getName()); //logout

        //costruzione catalogoMap
        catalogoMap.put("0", this.cliHomeUtente.getClass().getName());

        //costruzione prenBibMap
        prenBibMap.put("0", this.cliHomeBibliotecario.getClass().getName());

        //costruzione nolgBibMap
        nolBibMap.put("0", this.cliHomeBibliotecario.getClass().getName());

        //UTENTE
        //costruzione di homeUtenteMap
        homeUtenteMap.put("0", this.cliPrenotaLibro.getClass().getName());
        homeUtenteMap.put("1", this.cliTrovaPrezzi.getClass().getName());
        homeUtenteMap.put("2", this.cliProfiloUtente.getClass().getName());

        //costruzione profUtenteMap
        profUtenteMap.put("0", this.cliPrenotazioniUtente.getClass().getName());
        profUtenteMap.put("1", this.cliNoleggiUtente.getClass().getName());
        profUtenteMap.put("3", this.cliHomeUtente.getClass().getName());
        profUtenteMap.put("2", this.cliLogin.getClass().getName()); //logout

        //costruzione prenUtenteMap
        prenUtenteMap.put("1", this.cliProfiloUtente.getClass().getName());
        //costruzione nolUtenteMap
        nolUtenteMap.put("0", this.cliProfiloUtente.getClass().getName());

        //costruzione prenLibroMap
        prenLibroMap.put("0", this.cliHomeUtente.getClass().getName());

        //costruzione prezziMap
        prezziMap.put("0", this.cliHomeUtente.getClass().getName());

        //costruzione di cliMap
        cliMap.put (this.cliLogin.getClass().getSimpleName(), loginMap );
        cliMap.put (this.cliRegistrazione.getClass().getSimpleName(), regMap );
        cliMap.put (this.cliHomeBibliotecario.getClass().getSimpleName(), homeBibMap );
        cliMap.put (this.cliCatalogo.getClass().getSimpleName(), catalogoMap );
        cliMap.put (this.cliNoleggiBibliotecario.getClass().getSimpleName(), nolBibMap);
        cliMap.put (this.cliPrenotazioniBibliotecario.getClass().getSimpleName(), prenBibMap);
        cliMap.put (this.cliHomeUtente.getClass().getSimpleName(), homeUtenteMap );
        cliMap.put (this.cliPrenotaLibro.getClass().getSimpleName(), prenLibroMap );
        cliMap.put (this.cliTrovaPrezzi.getClass().getSimpleName(), prezziMap );
        cliMap.put (this.cliProfiloUtente.getClass().getSimpleName(), profUtenteMap );
        cliMap.put (this.cliNoleggiUtente.getClass().getSimpleName(), nolUtenteMap );
        cliMap.put (this.cliPrenotazioniUtente.getClass().getSimpleName(), prenUtenteMap );

    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        ShowInterface currCli = this.cliLogin;
        while (currCli != null) {
            String nextStep = currCli.show(scanner);
            currCli = this.callNextBoundary(currCli, nextStep);
        }
        scanner.close();
        System.out.println("\nChiusura dell'applicazione\n");
        System.exit(0);
    }

    private ShowInterface callNextBoundary(ShowInterface currCli, String nextStep) {
        //nome del package della prossima boundary
        if(nextStep.equalsIgnoreCase("CLOSE")) return null;
        String nextBoundaryPackage = cliMap.get(currCli.getClass().getSimpleName()).get(nextStep);

        //ricavo la classe e poi l'istanza dal package nextBoundaryPackage
        Class<?> nextBoundaryClass = null;
        try {
            nextBoundaryClass = Class.forName(nextBoundaryPackage);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            return nextBoundaryClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e || IllegalAccessException e || InvocationTargetException e || NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }



}