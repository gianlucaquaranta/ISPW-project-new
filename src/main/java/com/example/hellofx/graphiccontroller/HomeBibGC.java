package com.example.hellofx.graphiccontroller;

import com.example.hellofx.controller.Logout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeBibGC {
    private Stage stage;
    private Parent root;

    @FXML
    void visualizzaCatalogo(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/visualizzaCatalogo.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void visualizzaRegistroNoleggi(ActionEvent event) {

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
    void visualizzaPrenotazioni(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/visualizzaPrenotazioni.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event) {
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




