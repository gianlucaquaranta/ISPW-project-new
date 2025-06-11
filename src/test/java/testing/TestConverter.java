package testing;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.bean.UtenteBean;
import com.example.hellofx.converter.Converter;
import com.example.hellofx.model.Prenotazione;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestConverter { //Gianluca Quaranta

    @Test
    void testBeanToPrenotazione() {

        LocalDateTime dataInizio = LocalDateTime.of(2025, 6, 5, 15, 20);
        LocalDateTime dataScadenza = LocalDateTime.of(2025, 6, 20, 15, 20);

        UtenteBean utente = new UtenteBean();
        utente.setUsername("mario");
        utente.setEmail("mario@example.com");

        LibroBean libro = new LibroBean();
        libro.setIsbn("9781234567890");

        BibliotecaBean biblioteca = new BibliotecaBean();
        biblioteca.setIdBiblioteca("ABCD");

        PrenotazioneBean pb = new PrenotazioneBean(null, dataInizio, dataScadenza, utente, biblioteca, libro);


        Prenotazione p = Converter.beanToPrenotazione(pb);

        assertEquals(pb.getDataInizioT(), p.getDataInizio());
        assertEquals(pb.getDataScadenzaT(), p.getDataScadenza());

        assertNotNull(p.getDatiUtente());
        assertEquals("mario", p.getDatiUtente()[0]);
        assertEquals("mario@example.com", p.getDatiUtente()[1]);

        assertEquals("9781234567890", p.getIsbn());
        assertEquals("ABCD", p.getIdBiblioteca());

        assertNotNull(p.getIdPrenotazione());
    }
}
