package com.example.hellofx.cli;

import com.example.hellofx.bean.LibroBean;

import java.util.List;

public class CliCatalogoTableConfigurator {
    private static final String SEPARATOR = "+----+--------------------------------+----------------------+----------------------+---------------+------+--------------+----------------+";

    private CliCatalogoTableConfigurator() {}

    public static void mostraTabellaCatalogo(List<LibroBean> catalogo) {
        // Intestazione della tabella
        System.out.println("\n=== CATALOGO LIBRI ===");
        System.out.println(SEPARATOR);
        System.out.println("| #  | Titolo                         | Autore               | Editore              | ISBN          | Anno | Copie totali | Copie dispon.  |");
        System.out.println(SEPARATOR);

        // Corpo della tabella
        for (int i = 0; i < catalogo.size(); i++) {
            LibroBean libro = catalogo.get(i);
            System.out.printf(
                    "| %-2d | %-30s | %-20s | %-20s | %-13s | %-4d | %-12d | %-14d |%n",
                    i + 1,
                    truncate(libro.getTitolo(), 30),
                    truncate(libro.getAutore(), 20),
                    truncate(libro.getEditore(), 20),
                    libro.getIsbn(),
                    libro.getAnnoPubblicazione(),
                    libro.getCopie(),
                    libro.getCopieDisponibili()
            );
        }

        // Chiusura della tabella
        System.out.println(SEPARATOR);
    }

    // Metodo di supporto per troncare stringhe troppo lunghe
    private static String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}
