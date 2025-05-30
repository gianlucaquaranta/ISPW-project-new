package com.example.hellofx.session;

import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Bibliotecario;
import com.example.hellofx.model.Utente;

public abstract class Session {
    private static boolean isFile;
    private static boolean isFull;

    public static void setFile(boolean file) { isFile = file; }
    public static void setFull(boolean full) { isFull = full; }
    public static boolean isFile() {return isFile;}
    public static boolean isFull() {return isFull;}

    public abstract void setUtente(Utente utente);
    public abstract Utente getUtente();
    public void setBiblioteca(Biblioteca biblioteca){}
    public abstract Biblioteca getBiblioteca();

    public void close(){}

}
