package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controller.PrenotazioniBibController;
import com.example.hellofx.controller.Logout;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import com.example.hellofx.controllerfactory.PrenotazioniBibControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomeBibGC {

    @FXML
    void visualizzaCatalogo(ActionEvent event) throws IOException {
        // Recupera il catalogo
        AggiornaCatController aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();
        List<LibroBean> catalogo = aggiornaCatController.getCatalogo();

        // Carica il file FXML di visualizzaCatalogo
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/visualizzaCatalogo.fxml"));
        Parent root = loader.load();

        // Passaggio dati al controller di visualizzaCatalogo
        VisualizzaCatalogoGC catalogoController = loader.getController();
        catalogoController.mostraCatalogo(catalogo);

        // Mostra la nuova scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void visualizzaRegistroNoleggi(ActionEvent event) {
        Stage stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/visualizzaNoleggi.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void visualizzaPrenotazioni(ActionEvent event) throws IOException {
        // Recupera le prenotazioni
        PrenotazioniBibController prenotazioniBibController = PrenotazioniBibControllerFactory.getInstance().createPrenotazioniBibController();
        List<PrenotazioneBean> prenotazioni = prenotazioniBibController.getPrenotazioni();

        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/visualizzaPrenotazioni.fxml"));
        Parent root = loader.load();

        // Passaggio dati al controller
        PrenotazioniBibGC prenotazioniBibGC = loader.getController();
        prenotazioniBibGC.mostraPrenotazioni(prenotazioni);

        // Mostra la nuova scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) {
        Stage stage;
        Parent root;
        new Logout().logout();
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}