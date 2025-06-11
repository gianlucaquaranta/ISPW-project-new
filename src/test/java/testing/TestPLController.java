package testing;

import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.PLControllerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestPLController { //Gianluca Quaranta

    PLController controller = PLControllerFactory.getInstance().createPLController();

    @Test
    void testStringheUgualiTrue() {
        assertTrue(controller.stringMatch("ciao", "ciao"));
    }

    @Test
    void testStringaVuotaTrue() {
        assertTrue(controller.stringMatch("", "qualcosa"));
        assertTrue(controller.stringMatch("qualcosa", ""));
    }

    @Test
    void testContainsTrue() {
        assertTrue(controller.stringMatch("biblioteca", "biblio"));
        assertTrue(controller.stringMatch("biblio", "biblioteca"));
    }

    @Test
    void testStringhSimiliTrue() {
        assertTrue(controller.stringMatch("uno nessuno centomila", "unonessunocentomila"));
    }

    @Test
    void testStringheDifferentiFalse() {
        assertFalse(controller.stringMatch("matematica", "fisica"));
    }

    @Test
    void testMaiuscoleTrue() {
        assertTrue(controller.stringMatch("Titolo", "titolo"));
    }

    @Test
    void testParoleSimiliTrue() {
        assertTrue(controller.stringMatch("intelligenza", "intelligente")); // condividono molte lettere
    }

    @Test
    void testParoleDiverseFalse() {
        assertFalse(controller.stringMatch("java", "python")); // condividono poche lettere
    }
}
