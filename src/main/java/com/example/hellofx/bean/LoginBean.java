package com.example.hellofx.bean;

public class LoginBean {
    private String username;
    private String password;
    private String type; // "utente" || "biblitoecario"

    public LoginBean() {
        this.username = null;
        this.password = null;
        this.type = null;
    }

    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getType() { return this.type; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setType(String type) { this.type = type.toLowerCase(); }

}
