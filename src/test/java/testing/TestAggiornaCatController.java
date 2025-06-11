package testing;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import com.example.hellofx.model.Biblioteca;
import com.example.hellofx.model.Libro;
import com.example.hellofx.model.modelfactory.BibliotecaFactory;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.SessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class TestAggiornaCatController { // Beatrice Proietti

    private AggiornaCatController controller;
    private Biblioteca biblioteca;

    @BeforeEach
    void setUp() {
        biblioteca = BibliotecaFactory.getInstance().createBiblioteca();
        biblioteca.setNome("b1");
        biblioteca.setId("b1");

        Libro libro1 = new Libro("Il Gattopardo", "Tomasi di Lampedusa", "Feltrinelli", "1234567890123", "Narrativa", 1958);
        Libro libro2 = new Libro("Il barone rampante", "Italo Calvino", "Mondadori", "9876543210987", "Narrativa", 1957);

        biblioteca.getCatalogo().add(libro1);
        biblioteca.getCatalogo().add(libro2);

        biblioteca.getCopie().put("1234567890123", new Integer[]{5, 3});
        biblioteca.getCopie().put("9876543210987", new Integer[]{10, 8});

        BibliotecarioSession session = BibliotecarioSession.getInstance();
        session.setBiblioteca(biblioteca);
        SessionManager.setSession(session);

        controller = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();
    }

    @Test
    void testGetCatalogoRestituisceDueLibri() {
        List<LibroBean> catalogo = controller.getCatalogo();
        assertEquals(2, catalogo.size());

        LibroBean lb1 = catalogo.get(0);
        assertEquals("Il Gattopardo", lb1.getTitolo());
        assertArrayEquals(new Integer[]{5, 3}, lb1.getNumCopie());

        LibroBean lb2 = catalogo.get(1);
        assertEquals("Il barone rampante", lb2.getTitolo());
        assertArrayEquals(new Integer[]{10, 8}, lb2.getNumCopie());
    }

    @Test
    void testGetCatalogoVuoto() {
        Biblioteca bibVuota = BibliotecaFactory.getInstance().createBiblioteca();
        BibliotecarioSession session = BibliotecarioSession.getInstance();
        session.setBiblioteca(bibVuota);
        SessionManager.setSession(session);

        controller = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();

        List<LibroBean> catalogo = controller.getCatalogo();
        assertTrue(catalogo.isEmpty());
    }
}
