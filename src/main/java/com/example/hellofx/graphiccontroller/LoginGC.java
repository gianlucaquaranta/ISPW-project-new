package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.controller.LoginController;
import com.example.hellofx.controller.LoginResult;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.LoginControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginGC {

    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextField;

    LoginController loginController = LoginControllerFactory.getInstance().createLoginController();
    PLController plController;

    @FXML
    void login(ActionEvent event){
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(usernameTextField.getText());
        loginBean.setPassword(passwordTextField.getText());
        LoginResult result = loginController.authenticate(loginBean);

        switch (result) {
            case LoginResult.UTENTE:
                if(plController != null){ //Significa che gliel'ha passato RiepilogoPrenotazioneGC per gestire la UserNotLoggedException, quindi è necessario cambiare flusso
                    plController.registraPrenotazione();
                }
                break;
            case LoginResult.NON_AUTENTICATO:
                break;
            case LoginResult.BIBLIOTECARIO:
                SceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
                return;
        }

        SceneChanger.changeScene("/com/example/hellofx/schermateUtente.fxml", event);

    }

    @FXML
    void loginSenzaAccount(ActionEvent event){
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername("");
        loginBean.setPassword("");

        loginController.authenticate(loginBean);
        SceneChanger.changeScene("/com/example/hellofx/schermateUtente.fxml", event);
    }

    @FXML
    void registerNow(ActionEvent event){
        SceneChanger.changeScene("/com/example/hellofx/sceltaRegistrazione.fxml", event);
    }

    void setPlController(PLController plController) { this.plController = plController; }

}