package com.example.hellofx.graphiccontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class SceltaRegistrazioneGC {
    SceneChanger sceneChanger = new SceneChanger();

    @FXML
    void backToLogin(ActionEvent event) throws IOException {
        sceneChanger.changeScene("/com/example/hellofx/login.fxml", event);
    }

    @FXML
    void registrazioneBibliotecario(ActionEvent event) throws IOException {
        sceneChanger.changeScene("/com/example/hellofx/registrazioneBibliotecario.fxml", event);
    }

    @FXML
    void registrazioneUtente(ActionEvent event) throws IOException {
        sceneChanger.changeScene("/com/example/hellofx/registrazioneUtente.fxml", event);
    }

}
