package com.example.hellofx.bean;

public class BibliotecaBean {
    private String nome;
    private String indirizzo;
    private String cap;
    private String numeroCivico;
    private String citta;
    private String provincia;
    private String url;
    private String usernameBibliotecario;

    public BibliotecaBean(String nome, String indirizzo, String cap, String numeroCivico, String citta, String provincia, String url) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.numeroCivico = numeroCivico;
        this.citta = citta;
        this.provincia = provincia;
        this.url = url;
    }

    public BibliotecaBean() {

    }

    public String getUsernameBibliotecario() {
        return usernameBibliotecario;
    }

    public void setUsernameBibliotecario(String usernameBibliotecario) {
        this.usernameBibliotecario = usernameBibliotecario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
