package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.controller.LoginController;
import com.example.hellofx.controller.LoginResult;
import com.example.hellofx.controllerfactory.LoginControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginGC {

    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextField;

    LoginController loginController = LoginControllerFactory.getInstance().createLoginController();

    @FXML
    void login(ActionEvent event) throws IOException {
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(usernameTextField.getText());
        loginBean.setPassword(passwordTextField.getText());
        LoginResult result = loginController.authenticate(loginBean);

        switch (result) {
            case LoginResult.UTENTE, LoginResult.NON_AUTENTICATO:
                //passa i valori alla schermata successiva per displayare i dati utente
                SceneChanger.changeScene("/com/example/hellofx/schermateUtente.fxml", event);

                break;
            case LoginResult.BIBLIOTECARIO:
                SceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
                break;
        }

    }

    @FXML
    void loginSenzaAccount(ActionEvent event) throws IOException {
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("");
        loginBean.setPassword("");

        loginController.authenticate(loginBean);
        SceneChanger.changeScene("/com/example/hellofx/schermateUtente.fxml", event);
    }

    @FXML
    void registerNow(ActionEvent event) throws IOException {
        SceneChanger.changeScene("/com/example/hellofx/sceltaRegistrazione.fxml", event);
    }

}