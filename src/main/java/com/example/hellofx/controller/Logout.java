package com.example.hellofx.controller;

import com.example.hellofx.session.Session;
import com.example.hellofx.session.SessionManager;

public class Logout {
    public void logout() {
        SessionManager.logout();
    }
}
