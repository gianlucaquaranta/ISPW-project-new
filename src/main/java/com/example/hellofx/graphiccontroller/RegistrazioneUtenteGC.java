package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.RegistrazioneUtenteBean;
import com.example.hellofx.controller.RegistrazioneController;
import com.example.hellofx.controllerfactory.RegistrazioneControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrazioneUtenteGC {

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextField;

    RegistrazioneController registrazioneController = RegistrazioneControllerFactory.getInstance().creteRegistrazioneController();

    @FXML
    void backToLogin(ActionEvent event){
        SceneChanger.changeScene("/com/example/hellofx/login.fxml", event);
    }

    @FXML
    void register(ActionEvent event){
        RegistrazioneUtenteBean regBean = new RegistrazioneUtenteBean(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
        boolean success = registrazioneController.registraUtente(regBean);
        if(success) {
            SceneChanger.changeScene("/com/example/hellofx/login.fxml", event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Username già esistente!");
            alert.showAndWait();
        }
    }

}
