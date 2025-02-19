package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.UtenteBean;
import com.example.hellofx.controllerfactory.LoginControllerFactory;
import com.example.hellofx.controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginGC {

    private Stage stage;
    private Parent root;
    @FXML
    private Button accediButton;
    @FXML
    private Button accediGoogleButton;
    @FXML
    private Label noAccessoLabel;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label registratiOraLabel;
    @FXML
    private TextField usernameTextField;


    LoginController loginController = LoginControllerFactory.getInstance().createLoginController();
    UtenteBean utenteBean = new UtenteBean();

    @FXML
    void login(ActionEvent event) {

        if(usernameTextField.getText().equals("lettore")) {
            try{
                root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/schermateUtente.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene (root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(usernameTextField.getText().equals("bibliotecario")) {
            try{
                root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/homeBibliotecario.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene (root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
/*

        utenteBean.setUsername(usernameTextField.getText());
        utenteBean.setPassword(passwordTextField.getText());
        utenteBean = loginController.authenticate(utenteBean);

        if(utenteBean.getType().equals("utente")) {
            try{
                root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/schermateUtente.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene (root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(utenteBean.getType().equals("bibliotecario")) {
            try {
                root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/homeBibliotecario.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di autenticazione");
            alert.setHeaderText("Credenziali errate");
            alert.setContentText("Riprova");
            passwordTextField.setText(null);
            usernameTextField.setText(null);
        }
    }

*/
    @FXML
    void loginGoogle(ActionEvent event) {
        System.out.println("Login with Google");
    }

    @FXML
    void noAccesso(MouseEvent event) {
            UtenteBean utenteBean = new UtenteBean("","");
        try{
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/schermateUtente.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene (root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registratiOra(MouseEvent event){
        try{
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/sceltaRegistrazione.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene (root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
