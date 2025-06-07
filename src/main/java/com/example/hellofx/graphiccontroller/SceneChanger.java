package com.example.hellofx.graphiccontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class SceneChanger {

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
        } catch (IOException e) {
        e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    /*
    Nei GC:
    SceneChanger.changeSceneWithController(
    "/com/example/hellofx/visualizzaCatalogo.fxml",
    event,
    (VisualizzaCatalogoGC controller) -> controller.mostraCatalogo(aggiornaCatController.getCatalogo())
);
     */
}
