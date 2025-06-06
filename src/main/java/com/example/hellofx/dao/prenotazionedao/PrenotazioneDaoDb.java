package com.example.hellofx.dao.prenotazionedao;

import com.example.hellofx.dao.DbConnection;
import com.example.hellofx.model.Prenotazione;
import com.example.hellofx.model.modelfactory.PrenotazioneFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDaoDb implements PrenotazioneDao {
    PrenotazioneFactory prenotazioneFactory = PrenotazioneFactory.getInstance();

    @Override
    public Prenotazione loadOne(String idPrenotazione) {
        // idPrenotazione: [0] = username, [1] = idbiblioteca, [2] = isbn
        Prenotazione prenotazione = null;
        String query = "SELECT p.username, p.idbiblioteca, p.isbn, p.datainizio, p.datascadenza, u.email " +
                "FROM prenotazioni p " +
                "JOIN utenti u ON p.username = u.username " +
                "WHERE p.username = ? AND p.idbiblioteca = ? AND p.isbn = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String[] id = idPrenotazione.split("/");

            stmt.setString(1, id[0]);
            stmt.setString(2, id[1]);
            stmt.setString(3, id[2]);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                prenotazione = mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prenotazione;
    }

    @Override
    public List<Prenotazione> loadAllUtente(String username) {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        String query = "SELECT p.username, p.idbiblioteca, p.isbn, p.datainizio, p.datascadenza, u.email " +
                "FROM prenotazioni p " +
                "JOIN utenti u ON p.username = u.username " +
                "WHERE p.username = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prenotazioni.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prenotazioni;
    }

    @Override
    public List<Prenotazione> loadAllBiblioteca(String idBiblioteca) {
        List<Prenotazione> prenotazioni = new ArrayList<>();
        String query = "SELECT p.username, p.idbiblioteca, p.isbn, p.datainizio, p.datascadenza, u.email " +
                "FROM prenotazioni p " +
                "JOIN utenti u ON p.username = u.username " +
                "WHERE p.idbiblioteca = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idBiblioteca);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                prenotazioni.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prenotazioni;
    }

    @Override
    public void store(Prenotazione p) {
        String query = "INSERT INTO prenotazioni (username, idbiblioteca, isbn, datainizio, datascadenza) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // p.getDatiUtente()[0] contiene l'username
            stmt.setString(1, p.getDatiUtente()[0]);
            stmt.setString(2, p.getIdBiblioteca());
            stmt.setString(3, p.getIsbn());
            stmt.setTimestamp(4, p.getDataInizio());
            stmt.setTimestamp(5, p.getDataScadenza());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String idPrenotazione) {
        String query = "DELETE FROM prenotazioni WHERE username = ? AND idbiblioteca = ? AND isbn = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String[] id = idPrenotazione.split("/");

            stmt.setString(1, id[0]);
            stmt.setString(2, id[1]);
            stmt.setString(3, id[2]);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Prenotazione mapResultSet(ResultSet rs) throws SQLException {
        Prenotazione prenotazione = prenotazioneFactory.createPrenotazione();

        String username = rs.getString("username");
        prenotazione.setIdBiblioteca(rs.getString("idbiblioteca"));
        prenotazione.setIsbn(rs.getString("isbn"));
        prenotazione.setDataInizio(rs.getTimestamp("datainizio"));
        prenotazione.setDataScadenza(rs.getTimestamp("dataScadenza"));
        String email = rs.getString("email");

        prenotazione.setDatiUtente(new String[] {username, email});
        prenotazione.setIdPrenotazione();

        return prenotazione;
    }

}
