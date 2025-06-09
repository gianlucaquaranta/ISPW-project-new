package com.example.hellofx.bean;

public class RegistrazioneBibliotecaBean {
    private BibliotecaBean biblioteca;
    private String password;

    public RegistrazioneBibliotecaBean(String nome, String password, String indirizzo, String cap, String numeroCivico, String citta, String provincia) {
        this.biblioteca.setNome(nome);
        this.password = password;
        this.biblioteca.setIndirizzo(indirizzo);
        this.biblioteca.setCap(cap);
        this.biblioteca.setNumeroCivico(numeroCivico);
        this.biblioteca.setCitta(citta);
        this.biblioteca.setProvincia(provincia);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BibliotecaBean getBiblioteca() {return this.biblioteca;}

    public void setBiblioteca(BibliotecaBean biblioteca) { this.biblioteca = biblioteca; }
}

