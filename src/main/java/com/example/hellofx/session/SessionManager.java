package com.example.hellofx.session;

public class SessionManager {
    private static Session session;

    private SessionManager() {}

    public static void setSession(Session s) {
        session = s;
    }

    public static Session getSession() {
        return session;
    }

    public static boolean isSessionActive() {
        return session != null;
    }

    public static void logout() {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public static void clearSession() {
        session = null;
    }
}
