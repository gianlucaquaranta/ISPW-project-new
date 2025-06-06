package com.example.hellofx.bean;

public class RegistrazioneUtenteBean {
    private String username;
    private String email;
    private String password;

    public RegistrazioneUtenteBean(String username, String email, String password) {
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
