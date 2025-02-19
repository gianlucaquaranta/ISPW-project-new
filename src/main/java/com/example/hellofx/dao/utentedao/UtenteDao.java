package com.example.hellofx.dao.utentedao;
import com.example.hellofx.entity.Utente;
import java.util.List;

public interface UtenteDao {
    Utente loadUtente(String username);
    //HashMap<String, String> il dao converte la lista di user in una tabella di username - password per facilitare il controller
    List<Utente> loadAllUtenti();
    void storeUtente(Utente utente);
    void updateUtente(Utente utente);
    void deleteUtente(String username);
}
