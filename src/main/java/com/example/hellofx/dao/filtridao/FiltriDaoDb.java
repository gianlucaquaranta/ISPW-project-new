package com.example.hellofx.dao.filtridao;

import com.example.hellofx.dao.DbConnection;
import com.example.hellofx.entity.Filtri;
import com.example.hellofx.entity.entityfactory.FiltriFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FiltriDaoDb implements FiltriDao{
    FiltriFactory filtriFactory = FiltriFactory.getInstance();

    @Override
    public List<Filtri> loadAllUtente(String username) {
        List<Filtri> filtriList = new ArrayList<>();
        String query = "SELECT titolo, autore, genere, isbn, nomebiblioteca, cap, raggio FROM filtri WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                filtriList.add(filtriFactory.createFiltri(
                        rs.getString("titolo"),
                        rs.getString("autore"),
                        rs.getString("genere"),
                        rs.getString("nomebiblioteca"),
                        rs.getString("raggio"),
                        rs.getString("isbn"),
                        rs.getString("cap")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filtriList;
    }

    @Override
    public void storeOne(Filtri filtri, String username) {
        String query = "INSERT INTO filtri (username, titolo, autore, genere, isbn, nomebiblioteca, cap, raggio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, filtri.getTitolo());
            stmt.setString(3, filtri.getAutore());
            stmt.setString(4, filtri.getGenere());
            stmt.setString(5, filtri.getIsbn());
            stmt.setString(6, filtri.getBiblioteca());
            stmt.setString(7, filtri.getCap());
            stmt.setString(8, filtri.getRaggio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllUtente(String username) {
        String query = "DELETE FROM filtri WHERE username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOne(String username, Integer i) {
        String query = "DELETE FROM filtri WHERE username = ? LIMIT 1 OFFSET ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setInt(2, i);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
