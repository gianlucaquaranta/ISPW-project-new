package com.example.hellofx.bean;

public class LoginBean {
    private String username;
    private String password;

    public LoginBean() {
        this.username = null;
        this.password = null;
    }

    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
