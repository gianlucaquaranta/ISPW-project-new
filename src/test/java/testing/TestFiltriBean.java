package testing;

import com.example.hellofx.bean.FiltriBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestFiltriBean { //Gianluca Quaranta

    private final FiltriBean fb = new FiltriBean();

    @Test
    void testIsbnPulitoValido() {
        assertEquals("9781234567890", fb.validaIsbn("9781234567890"));
    }

    @Test
    void testIsbnConTrattiniValido() {
        assertEquals("9781234567890", fb.validaIsbn("978-1-2345-6789-0"));
    }

    @Test
    void testIsbnConSpaziValido() {
        assertEquals("9781234567890", fb.validaIsbn("978 1 2345 6789 0"));
    }

    @Test
    void testIsbnConSpaziETrattiniMistiValido() {
        assertEquals("9781234567890", fb.validaIsbn(" 978 - 1 - 2345 - 6789 - 0 "));
    }

    @Test
    void testIsbnVuoto() {
        assertEquals("", fb.validaIsbn(""));
    }

    @Test
    void testIsbnTroppoCortoInvalido() {
        String input = "97812345678";
        assertThrows(IllegalArgumentException.class, () -> fb.validaIsbn(input));
    }

    @Test
    void testIsbnConLettereESimboliInvalido() {
        String input = "978A23$45678&";
        assertThrows(IllegalArgumentException.class, () -> fb.validaIsbn(input));
    }

}