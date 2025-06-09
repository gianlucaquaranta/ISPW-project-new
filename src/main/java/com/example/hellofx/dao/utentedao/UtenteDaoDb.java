package com.example.hellofx.dao.utentedao;

import com.example.hellofx.DbConnection;
import com.example.hellofx.dao.prenotazionedao.PrenotazioneDaoDb;
import com.example.hellofx.model.Utente;
import com.example.hellofx.model.modelfactory.UtenteFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteDaoDb implements UtenteDao {
    private UtenteFactory utenteFactory = UtenteFactory.getInstance();

    @Override
    public Utente loadUtente(String username) {
        String query = "SELECT username, email FROM utenti WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Utente utente = utenteFactory.createUtente();
                utente.setUsername(rs.getString("username"));
                utente.setEmail(rs.getString("email"));
                return utente;
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(UtenteDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal DB degli utenti");

        }
        return null;
    }


    @Override
    public List<Utente> loadAllUtenti() {
        List<Utente> utenti = new ArrayList<>();
        String query = "SELECT username, email FROM utenti";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                utenti.add(new Utente(
                        rs.getString("username"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(UtenteDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal DB degli utenti");

        }
        return utenti;
    }

    @Override
    public void storeUtente(Utente utente) {
        String query = "INSERT INTO utenti (username, email) VALUES (?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utente.getUsername());
            stmt.setString(2, utente.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(UtenteDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il salvataggip nel DB degli utenti");

        }
    }


    @Override
    public void updateUtente(Utente utente) {
        String query = "UPDATE utenti SET email = ? WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utente.getEmail());
            stmt.setString(2, utente.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(UtenteDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante l'update nel DB degli utenti");

        }
    }

    @Override
    public void deleteUtente(String username) {
        String query = "DELETE FROM utenti WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(UtenteDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante l'eliminazione nel DB degli utenti");

        }
    }
}
