package com.example.hellofx.bean;

public class UtenteBean {
    private String username;
    private String password;
    private String email;

    public UtenteBean(){
        this.username = null;
        this.password = null;
        this.email = null;
    }

    public UtenteBean(String username, String password, String email, String type) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
}
