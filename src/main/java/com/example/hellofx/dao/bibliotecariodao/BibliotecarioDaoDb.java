package com.example.hellofx.dao.bibliotecariodao;

import com.example.hellofx.dao.DbConnection;
import com.example.hellofx.entity.Bibliotecario;
import com.example.hellofx.entity.entityfactory.BibliotecarioFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BibliotecarioDaoDb implements BibliotecarioDao {
    BibliotecarioFactory bibliotecarioFactory = BibliotecarioFactory.getInstance();
    @Override
    public List<Bibliotecario> loadAll() {
        List<Bibliotecario> bibliotecari = new ArrayList<>();
        String query = "SELECT username, nome, cognome, password FROM bibliotecari";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bibliotecario bibliotecario = bibliotecarioFactory.createBibliotecario();
                bibliotecario.setUsername(rs.getString("username"));
                bibliotecario.setNome(rs.getString("nome"));
                bibliotecario.setCognome(rs.getString("cognome"));
                bibliotecario.setPassword(rs.getString("password"));

                bibliotecari.add(bibliotecario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bibliotecari;
    }

    @Override
    public Bibliotecario load(String username) {
        String query = "SELECT username, nome, cognome, password FROM bibliotecari WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bibliotecario bibliotecario = bibliotecarioFactory.createBibliotecario();
                    bibliotecario.setUsername(rs.getString("username"));
                    bibliotecario.setNome(rs.getString("nome"));
                    bibliotecario.setCognome(rs.getString("cognome"));
                    bibliotecario.setPassword(rs.getString("password"));

                    return bibliotecario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void store(Bibliotecario bibliotecario) {
        String query = "INSERT INTO bibliotecari (username, nome, cognome, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bibliotecario.getUsername());
            stmt.setString(2, bibliotecario.getNome());
            stmt.setString(3, bibliotecario.getCognome());
            stmt.setString(4, bibliotecario.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Bibliotecario bibliotecario) {
        String query = "UPDATE bibliotecari SET nome = ?, cognome = ?, password = ? WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bibliotecario.getNome());
            stmt.setString(2, bibliotecario.getCognome());
            stmt.setString(3, bibliotecario.getPassword());
            stmt.setString(4, bibliotecario.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String username) {
        String query = "DELETE FROM bibliotecari WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
