package com.example.hellofx.session;
import com.example.hellofx.model.Utente;

public class UtenteSession extends Session {
    private Utente utente;

    private static UtenteSession instance = null;

    public static UtenteSession getInstance() {
        if (instance == null) {
            instance = new UtenteSession();
        }
        return instance;
    }

    private UtenteSession() { }

    public Utente getUtente() { return utente; }

    public void setUtente(Utente utente) { this.utente = utente; }

    public void close(){
        utente = null;
    }

}
