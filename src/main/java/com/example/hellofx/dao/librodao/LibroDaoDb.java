package com.example.hellofx.dao.librodao;

import com.example.hellofx.DbConnection;
import com.example.hellofx.model.Libro;
import com.example.hellofx.model.modelfactory.LibroFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibroDaoDb implements LibroDao {
    private final LibroFactory libroFactory = LibroFactory.getInstance();
    private static final String ISBN = "isbn";
    private static final String TITOLO = "titolo";
    private static final String AUTORE = "autore";
    private static final String EDITORE = "editore";
    private static final String GENERE = "genere";

    @Override
    public Libro load(String isbn, String idBiblioteca) {
        String query = "SELECT isbn, titolo, autore, genere, editore, anno FROM libri WHERE isbn = ? AND idBiblioteca = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, isbn);
            stmt.setString(2, idBiblioteca);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString(ISBN) == null && rs.getString(TITOLO) == null
                        && rs.getString(AUTORE) == null) {
                    return null;
                }
                return createLibroFromResultSet(rs);
            }
        } catch (SQLException e) {
            logError("Errore durante il caricamento dal DB dei libri", e);
        }
        return null;
    }

    @Override
    public List<Libro> loadAll(String idBiblioteca) {
        String query = "SELECT isbn, titolo, autore, genere, editore, anno FROM libri WHERE idBiblioteca = ?";
        List<Libro> libri = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idBiblioteca);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    libri.add(createLibroFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            logError("Errore durante il caricamento dal DB dei libri", e);
        }
        return libri;
    }

    @Override
    public void store(Libro libro, String idBiblioteca) {
        String query = "INSERT INTO libri (isbn, titolo, autore, genere, editore, anno, idBiblioteca) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitolo());
            stmt.setString(3, libro.getAutore());
            stmt.setString(4, libro.getGenere());
            stmt.setString(5, libro.getEditore());
            stmt.setInt(6, libro.getAnnoPubblicazione());
            stmt.setString(7, idBiblioteca);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logError("Errore durante il salvataggio nel DB dei libri", e);
        }
    }

    // Metodo helper per creare un Libro dal ResultSet
    private Libro createLibroFromResultSet(ResultSet rs) throws SQLException {
        Libro libro = libroFactory.createLibro();
        libro.setIsbn(rs.getString(ISBN));
        libro.setTitolo(rs.getString(TITOLO));
        libro.setAutore(rs.getString(AUTORE));
        libro.setEditore(rs.getString(EDITORE));
        libro.setGenere(rs.getString(GENERE));
        libro.setAnnoPubblicazione(rs.getInt("anno"));
        return libro;
    }

    // Metodo helper per la gestione degli errori
    private void logError(String message, SQLException e) {
        Logger logger = Logger.getLogger(LibroDaoDb.class.getName());
        logger.log(Level.SEVERE, e, () -> message);
    }
}
