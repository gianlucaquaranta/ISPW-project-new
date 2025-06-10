package com.example.hellofx.graphiccontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SceltaRegistrazioneGC {

    @FXML
    void backToLogin(ActionEvent event) {
        SceneChanger.changeScene("/com/example/hellofx/login.fxml", event);
    }

    @FXML
    void registrazioneBibliotecario(ActionEvent event) {
        SceneChanger.changeScene("/com/example/hellofx/registrazioneBiblioteca.fxml", event);
    }

    @FXML
    void registrazioneUtente(ActionEvent event) {
        SceneChanger.changeScene("/com/example/hellofx/registrazioneUtente.fxml", event);
    }

}
