package testing;

import com.example.hellofx.bean.GenereBean;
import com.example.hellofx.bean.LibroBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestLibroBean { // Beatrice Proietti

    // Util per creare una bean valida
    private LibroBean creaBeanValida() {
        LibroBean bean = new LibroBean();
        bean.setTitolo("Il nome della rosa");
        bean.setAutore("Umberto Eco");
        bean.setEditore("Bompiani");
        bean.setIsbn("1234567890123");
        bean.setGenere(GenereBean.STORICO);
        bean.setAnnoPubblicazione(2010);
        bean.setNumCopie(new Integer[]{10, 10});
        return bean;
    }

    @Test
    void testValidateValido() {
        LibroBean bean = creaBeanValida();
        assertDoesNotThrow(bean::validate);
    }

    @Test
    void testValidateTitoloMancante() {
        LibroBean bean = creaBeanValida();
        bean.setTitolo(" ");
        Exception e = assertThrows(IllegalArgumentException.class, bean::validate);
        assertTrue(e.getMessage().contains("- Titolo"));
    }

    @Test
    void testValidateAnnoNonValido() {
        LibroBean bean = creaBeanValida();
        bean.setAnnoPubblicazione(3000); // futuro
        Exception e = assertThrows(IllegalArgumentException.class, bean::validate);
        assertTrue(e.getMessage().contains("- Anno pubblicazione"));
    }

    @Test
    void testValidateCopieNull() {
        LibroBean bean = creaBeanValida();
        bean.setNumCopie(null);
        Exception e = assertThrows(IllegalArgumentException.class, bean::validate);
        assertTrue(e.getMessage().contains("- Numero di copie"));
    }

    @Test
    void testValidateTuttiCampiMancanti() {
        LibroBean bean = new LibroBean();
        Exception e = assertThrows(IllegalArgumentException.class, bean::validate);
        String msg = e.getMessage();
        assertTrue(msg.contains("- Titolo"));
        assertTrue(msg.contains("- Autore"));
        assertTrue(msg.contains("- ISBN"));
        assertTrue(msg.contains("- Editore"));
        assertTrue(msg.contains("- Genere"));
        assertTrue(msg.contains("- Anno pubblicazione"));
        assertTrue(msg.contains("- Numero di copie"));
    }
}
