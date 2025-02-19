package com.example.hellofx.dao.utentedao;

import com.example.hellofx.dao.DbConnection;
import com.example.hellofx.entity.Utente;
import com.example.hellofx.entity.entityfactory.UtenteFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDaoDb implements UtenteDao {
    private UtenteFactory utenteFactory = UtenteFactory.getInstance();

    @Override
    public Utente loadUtente(String username) {
        String query = "SELECT username, email, password FROM utenti WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Utente utente = utenteFactory.createUtente();
                utente.setUsername(rs.getString("username"));
                utente.setPassword(rs.getString("password"));
                utente.setEmail(rs.getString("email"));
                return utente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Utente> loadAllUtenti() {
        List<Utente> utenti = new ArrayList<>();
        String query = "SELECT username, email, password FROM utenti";
        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                utenti.add(new Utente(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utenti;
    }

    @Override
    public void storeUtente(Utente utente) {
        String query = "INSERT INTO utenti (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utente.getUsername());
            stmt.setString(2, utente.getEmail());
            stmt.setString(3, utente.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateUtente(Utente utente) {
        String query = "UPDATE utenti SET email = ?, password = ? WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, utente.getEmail());
            stmt.setString(2, utente.getPassword());
            stmt.setString(3, utente.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUtente(String username) {
        String query = "DELETE FROM utenti WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
