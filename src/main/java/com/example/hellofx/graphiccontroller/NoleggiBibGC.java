package com.example.hellofx.graphiccontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class NoleggiBibGC {

    @FXML
    void home(ActionEvent event) {
        SceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
    }
}
