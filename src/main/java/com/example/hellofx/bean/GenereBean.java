package com.example.hellofx.bean;

public enum GenereBean {
    ROMANCE("Romance"),
    FANTASY("Fantasy"),
    THRILLER_GIALLO("Thriller/Giallo"),
    DISTOPICO("Distopico"),
    STORICO("Storico"),
    POESIA("Poesia"),
    NARRATIVA("Narrativa"),
    FANTASCIENZA_SCI_FI("Fantascienza/Sci-Fi"),
    SAGGISTICA("Saggistica"),
    HORROR("Horror");

    private String nome;

    GenereBean(String nome) {
        this.nome = nome;
    }
    public String getNome() { return this.nome; }

    public static GenereBean fromString(String nome) {
        for (GenereBean g : GenereBean.values()) {
            if (g.getNome().equalsIgnoreCase(nome)) {
                return g;
            }
        }
        return null;
    }

}

