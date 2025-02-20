package com.example.hellofx.session;

import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Bibliotecario;
import com.example.hellofx.entity.Utente;

public abstract class Session {
    private static boolean isFile;
    private static boolean isFull;

    public static void setFile(boolean isFile) { isFile = isFile; }
    public static void setFull(boolean isFull) { isFull = isFull; }
    public static boolean isFile() {return isFile;}
    public static boolean isFull() {return isFull;}

    public abstract void setUtente(Utente utente);
    public abstract Utente getUtente();
    public void setBiblioteca(Biblioteca biblioteca){}
    public abstract Biblioteca getBiblioteca();
    public void setBibliotecario(Bibliotecario bibliotecario){}
    public abstract Bibliotecario getBibliotecario();

    public void close(){}

}
