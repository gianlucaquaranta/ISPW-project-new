package testing;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PrenotazioniBibController;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Libro;
import com.example.hellofx.model.Posizione;
import com.example.hellofx.model.Prenotazione;
import com.example.hellofx.model.modelfactory.BibliotecaFactory;
import com.example.hellofx.model.modelfactory.LibroFactory;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.SessionManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.*;

class TestPrenotazioniBibController { // Beatrice Proietti

    @Test
    void testGetPrenotazioni() {
        // Setup biblioteca e prenotazione
        Biblioteca biblioteca = BibliotecaFactory.getInstance().createBiblioteca();
        biblioteca.setPosizione(new Posizione("00036", "indirizzo", "numeroCivico", "citta", "provincia"));
        List<Libro> catalogo = new ArrayList<>();
        Libro l = LibroFactory.getInstance().createLibro();
        l.setIsbn("1234567890123");
        l.setAnnoPubblicazione(1234);
        catalogo.add(l);
        biblioteca.setCatalogo(catalogo);
        String[] datiUtente = {"nomeUtente", "emailUtente"};
        Timestamp ts1 = Timestamp.valueOf("2025-06-11 10:45:30");
        Timestamp ts2 = Timestamp.valueOf("2024-12-25 18:22:00");
        Prenotazione prenotazione = new Prenotazione(ts1, ts2, datiUtente, "idBiblioteca", "1234567890123");
        List<Prenotazione> attive = new ArrayList<>();
        attive.add(prenotazione);
        biblioteca.setPrenotazioniAttive(attive);

        // Setup sessione del bibliotecario
        BibliotecarioSession session = BibliotecarioSession.getInstance();
        session.setBiblioteca(biblioteca);
        SessionManager.setSession(session);

        // Setup controller e assegnazione sessione
        PrenotazioniBibController controller = new PrenotazioniBibController();

        // Esegui test
        List<PrenotazioneBean> result = controller.getPrenotazioni();

        // Verifica risultato
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
