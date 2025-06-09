package com.example.hellofx.graphiccontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneChanger {
    private static final Logger LOGGER = Logger.getLogger(SceneChanger.class.getName());

    private SceneChanger(){}

    public static void changeScene(String path, ActionEvent event) {
        try{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader;
        Parent root;

        loader = new FXMLLoader(SceneChanger.class.getResource(path));
        root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
        } catch (IOException _) {
        showAlert();
        }
    }

    public static <T> void changeSceneWithController(
            String path,
            ActionEvent event,
            Consumer<T> controllerHandler) {

        try {
            FXMLLoader loader = new FXMLLoader(SceneChanger.class.getResource(path));
            Parent root = loader.load();

            T controller = loader.getController();
            controllerHandler.accept(controller);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore I/O", e);
            showAlert();
        }
    }

    private static void showAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Errore");
        alert.setContentText("Errore di lettura del file.");
        alert.showAndWait();
    }

    public static void setUpCatalogoTableView(){

    }
}