package com.example.hellofx.graphiccontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {

    public void changeScene(String path, ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader;
        Parent root;

        loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
