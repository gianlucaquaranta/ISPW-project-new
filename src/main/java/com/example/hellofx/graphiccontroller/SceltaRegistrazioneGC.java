package com.example.hellofx.graphiccontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceltaRegistrazioneGC {
    private Stage stage;
    private Parent root;


    @FXML
    void indietro(MouseEvent event) {
        try{
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/login.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registrazioneBibliotecario(ActionEvent event) {
        try{
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/registrazioneBibliotecario.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registrazioneUtente(ActionEvent event) {
        try{
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/registrazioneUtente.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
