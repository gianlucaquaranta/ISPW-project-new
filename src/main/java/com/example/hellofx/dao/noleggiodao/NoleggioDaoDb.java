package com.example.hellofx.dao.noleggiodao;

import com.example.hellofx.converter.Converter;
import com.example.hellofx.dao.DbConnection;
import com.example.hellofx.model.Noleggio;
import com.example.hellofx.model.modelfactory.NoleggioFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoleggioDaoDb implements NoleggioDao {
    private NoleggioFactory noleggioFactory = NoleggioFactory.getInstance();

    @Override
    public Noleggio loadOne(String[] idNoleggio) {
        // idNoleggio: [0] = username, [1] = idbiblioteca, [2] = isbn
        Noleggio noleggio = null;
        String query = "SELECT n.username, n.idbiblioteca, n.isbn, n.datainizio, n.datascadenza, u.email " +
                "FROM noleggi n " +
                "JOIN utenti u ON n.username = u.username " +
                "WHERE n.username = ? AND n.idbiblioteca = ? AND n.isbn = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idNoleggio[0]);
            stmt.setString(2, idNoleggio[1]);
            stmt.setString(3, idNoleggio[2]);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                noleggio = mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return noleggio;
    }

    @Override
    public List<Noleggio> loadAllUtente(String username) {
        List<Noleggio> noleggi = new ArrayList<>();
        String query = "SELECT n.username, n.idbiblioteca, n.isbn, n.datainizio, n.datascadenza, u.email " +
                "FROM noleggi n " +
                "JOIN utenti u ON n.username = u.username " +
                "WHERE n.username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                noleggi.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return noleggi;
    }

    @Override
    public List<Noleggio> loadAllBiblioteca(String idBiblioteca) {
        List<Noleggio> noleggi = new ArrayList<>();
        String query = "SELECT n.username, n.idbiblioteca, n.isbn, n.datainizio, n.datascadenza, u.email " +
                "FROM noleggi n " +
                "JOIN utenti u ON n.username = u.username " +
                "WHERE n.idbiblioteca = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idBiblioteca);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                noleggi.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return noleggi;
    }

    @Override
    public void delete(String[] idNoleggio) {
        String query = "DELETE FROM noleggi WHERE username = ? AND idbiblioteca = ? AND isbn = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idNoleggio[0]);
            stmt.setString(2, idNoleggio[1]);
            stmt.setString(3, idNoleggio[2]);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void store(Noleggio noleggio) {
        String query = "INSERT INTO noleggi (username, idbiblioteca, isbn, datainizio, datascadenza) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Si assume che noleggio.getDatiUtente()[0] contenga l'username
            stmt.setString(1, noleggio.getDatiUtente()[0]);
            stmt.setString(2, noleggio.getIdBiblioteca());
            stmt.setString(3, noleggio.getIsbn());
            stmt.setTimestamp(4, Converter.stringToTimestamp(noleggio.getDataInizio()));
            stmt.setTimestamp(5, Converter.stringToTimestamp(noleggio.getDataScadenza()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Noleggio noleggio) {
        String query = "UPDATE noleggi " +
                "SET datainizio = ?, datascadenza = ? " +
                "WHERE username = ? AND idbiblioteca = ? AND isbn = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, Converter.stringToTimestamp(noleggio.getDataInizio()));
            stmt.setTimestamp(2, Converter.stringToTimestamp(noleggio.getDataScadenza()));
            stmt.setString(3, noleggio.getDatiUtente()[0]);
            stmt.setString(4, noleggio.getIdBiblioteca());
            stmt.setString(5, noleggio.getIsbn());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Noleggio mapResultSet(ResultSet rs) throws SQLException {
        Noleggio noleggio = noleggioFactory.createNoleggio();

        String username = rs.getString("username");
        noleggio.setIdBiblioteca(rs.getString("idbiblioteca"));
        noleggio.setIsbn(rs.getString("isbn"));
        noleggio.setDataInizio(Converter.timestampToString(rs.getTimestamp("datainizio")));
        noleggio.setDataScadenza(Converter.timestampToString(rs.getTimestamp("datascadenza")));
        String email = rs.getString("email");

        noleggio.setDatiUtente(new String[] {username, email});
        noleggio.setIdNoleggio();

        return noleggio;
    }

}
