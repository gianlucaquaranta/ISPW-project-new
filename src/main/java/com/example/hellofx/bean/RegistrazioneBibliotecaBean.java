package com.example.hellofx.bean;

public class RegistrazioneBibliotecaBean {
    private String nome;
    private String password;
    private String indirizzo;
    private String cap;
    private String numeroCivico;
    private String citta;
    private String provincia;

    public RegistrazioneBibliotecaBean(String nome, String password, String indirizzo, String cap, String numeroCivico, String citta, String provincia) {
        this.nome = nome;
        this.password = password;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.numeroCivico = numeroCivico;
        this.citta = citta;
        this.provincia = provincia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
