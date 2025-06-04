package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.bean.RegistrazioneBibliotecaBean;
import com.example.hellofx.controller.LoginController;
import com.example.hellofx.controller.RegistrazioneController;
import com.example.hellofx.controllerfactory.LoginControllerFactory;
import com.example.hellofx.controllerfactory.RegistrazioneControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrazioneBibGC {

    @FXML
    private TextField capTextField;
    @FXML
    private TextField cittaTextField;
    @FXML
    private TextField indirizzoTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField numCivicoTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField provinciaTextField;

    SceneChanger sceneChanger = new SceneChanger();
    RegistrazioneController registrazioneController = RegistrazioneControllerFactory.getInstance().creteRegistrazioneController();
    LoginController loginController = LoginControllerFactory.getInstance().createLoginController();

    @FXML
    void backToLogin(ActionEvent event) throws IOException {
        sceneChanger.changeScene("/com/example/hellofx/login.fxml", event);
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        String nome = nomeTextField.getText();
        String cap = capTextField.getText();
        String numCivico = numCivicoTextField.getText();
        String provincia = provinciaTextField.getText();
        String citta = cittaTextField.getText();
        String password = passwordTextField.getText();
        String indirizzo = indirizzoTextField.getText();

        RegistrazioneBibliotecaBean regBean = new RegistrazioneBibliotecaBean(nome, password, indirizzo, cap, numCivico, citta, provincia);
        boolean success = registrazioneController.registraBiblioteca(regBean);
        if(success) {
            LoginBean loginBean = new LoginBean();
            loginBean.setUsername(nome);
            loginBean.setPassword("b");
            loginController.authenticate(loginBean);
            sceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
        } else {

        }
    }
}
