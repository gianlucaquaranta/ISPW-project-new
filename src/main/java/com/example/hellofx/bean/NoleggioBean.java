package com.example.hellofx.bean;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NoleggioBean {
    private String username;
    private String email;
    private String idBiblioteca;
    private String isbn;
    private String idNoleggio;
    private Timestamp dataInizio;
    private Timestamp dataScadenza;

    public NoleggioBean() {}
    public NoleggioBean(String username, String idBiblioteca, String ISBN) {
        this.username = username;
        this.idBiblioteca = idBiblioteca;
        this.isbn = ISBN;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getIdBiblioteca() { return idBiblioteca; }
    public void setIdBiblioteca(String idBiblioteca) { this.idBiblioteca = idBiblioteca; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getIdNoleggio() { return idNoleggio; }
    public void setIdNoleggio(String id) { this.idNoleggio = id; }
    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }
    public String getDataInizio() { return dataInizio.format(dataInizio); }
    public void setDataInizio(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Definisce il formato
        LocalDate date = LocalDate.parse(dateString, formatter);
    } //date = "gg/MM/yyy"
    public String getDataScadenza() { return dataScadenza.format(dataScadenza); }
    public void setDataScadenza(String date){this.dataScadenza = new SimpleDateFormat(date)} //date = "gg/MM/yyy"

    }

}
