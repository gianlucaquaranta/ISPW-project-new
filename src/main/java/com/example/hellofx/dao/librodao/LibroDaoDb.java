package com.example.hellofx.dao.librodao;

import com.example.hellofx.dao.DbConnection;
import com.example.hellofx.entity.Filtri;
import com.example.hellofx.entity.Libro;
import com.example.hellofx.entity.entityfactory.LibroFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LibroDaoDb implements LibroDao {
    private LibroFactory libroFactory = LibroFactory.getInstance();

    @Override
    public Libro load(String isbn) {
        Libro libro = libroFactory.createLibro();
        String query = "SELECT isbn, titolo, autore, genere, editore, anno FROM libri WHERE isbn = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                libro.setTitolo(rs.getString("titolo"));
                libro.setAutore(rs.getString("autore"));
                libro.setEditore(rs.getString("editore"));
                libro.setGenere(rs.getString("genere"));
                libro.setAnnoPubblicazione(rs.getInt("anno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libro;
    }

    @Override
    public List<Libro> loadFilteredLibro(Filtri filtri) {
        List<Libro> libri = new ArrayList<>();
        String baseQuery = "SELECT isbn, titolo, autore, genere, editore, anno FROM libri WHERE 1=1";

        // Costruzione della query dinamica e dei parametri
        QueryParams queryParams = buildQueryParams(filtri, baseQuery);

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = prepareStatement(conn, queryParams)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                libri.add(mapResultSetToLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libri;
    }

    /**
     * Costruisce la query dinamicamente e raccoglie i parametri.
     */
    private QueryParams buildQueryParams(Filtri filtri, String baseQuery) {
        StringBuilder query = new StringBuilder(baseQuery);
        List<Object> parametri = new ArrayList<>();

        addFilter(query, parametri, "titolo", filtri.getTitolo(), true);
        addFilter(query, parametri, "autore", filtri.getAutore(), true);
        addFilter(query, parametri, "genere", filtri.getGenere(), false);
        addFilter(query, parametri, "isbn", filtri.getIsbn(), false);

        return new QueryParams(query.toString(), parametri);
    }

    /**
     * Aggiunge un filtro alla query se il valore non è nullo o vuoto.
     */
    private void addFilter(StringBuilder query, List<Object> parametri, String column, String value, boolean like) {
        if (value != null && !value.isEmpty()) {
            query.append(" AND ").append(column);
            query.append(like ? " LIKE ?" : " = ?");
            parametri.add(like ? "%" + value + "%" : value);
        }
    }

    /**
     * Prepara il PreparedStatement con i parametri raccolti.
     */
    private PreparedStatement prepareStatement(Connection conn, QueryParams queryParams) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(queryParams.query());
        for (int i = 0; i < queryParams.params().size(); i++) {
            stmt.setObject(i + 1, queryParams.params().get(i));
        }
        return stmt;
    }

    /**
     * Mappa un ResultSet in un oggetto Libro.
     */
    private Libro mapResultSetToLibro(ResultSet rs) throws SQLException {
        return new Libro(
                rs.getString("titolo"),
                rs.getString("autore"),
                rs.getString("editore"),
                rs.getString("isbn"),
                Genere.valueOf(rs.getString("genere")),
                rs.getInt("anno")
        );
    }

    /**
     * Classe per incapsulare la query e i parametri.
     */
    private record QueryParams(String query, List<Object> params) {}

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
            e.printStackTrace();
        }
    }
}
