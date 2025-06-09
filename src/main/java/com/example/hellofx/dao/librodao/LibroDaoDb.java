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
    private LibroFactory libroFactory = LibroFactory.getInstance();
    private static final String ISBN = "isbn";
    private static final String TITOLO = "titolo";
    private static final String AUTORE = "autore";
    private static final String EDITORE = "editore";
    private static final String GENERE = "genere";

    @Override
    public Libro load(String isbn) {
        Libro libro = libroFactory.createLibro();
        String query = "SELECT isbn, titolo, autore, genere, editore, anno FROM libri WHERE isbn = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                libro.setIsbn(rs.getString(ISBN));
                libro.setTitolo(rs.getString(TITOLO));
                libro.setAutore(rs.getString(AUTORE));
                libro.setEditore(rs.getString(EDITORE));
                libro.setGenere(rs.getString(GENERE));
                libro.setAnnoPubblicazione(rs.getInt("anno"));
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(LibroDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal DB dei libri");
        }

        return libro;
    }

    @Override
    public List<Libro> loadAll() {
        String query = "SELECT isbn, titolo, autore, genere, editore, anno FROM libri";
        List<Libro> libri = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = libroFactory.createLibro();
                libro.setTitolo(rs.getString(TITOLO));
                libro.setAutore(rs.getString(AUTORE));
                libro.setGenere(rs.getString(GENERE));
                libro.setEditore(rs.getString(EDITORE));
                libro.setAnnoPubblicazione(rs.getInt("anno"));
                libro.setIsbn(rs.getString("isbn"));
                libri.add(libro);
            }
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(LibroDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il caricamento dal DB dei libri");
        }
        return libri;
    }

    @Override
    public void store(Libro libro) {
        String query = "INSERT INTO libri (isbn, titolo, autore, genere, editore, anno) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitolo());
            stmt.setString(3, libro.getAutore());
            stmt.setString(4, libro.getGenere());
            stmt.setString(5, libro.getEditore());
            stmt.setInt(6, libro.getAnnoPubblicazione());

            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(LibroDaoDb.class.getName());
            logger.log(Level.SEVERE, e, () -> "Errore durante il salvataggio nel DB dei libri");
        }
    }
}
