package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LoginBean;
import com.example.hellofx.controller.LoginController;
import com.example.hellofx.controller.LoginResult;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.LoginControllerFactory;
import com.example.hellofx.exception.PrenotazioneGiaPresenteException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginGC {

    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextField;

    LoginController loginController = LoginControllerFactory.getInstance().createLoginController();
    PLController plController = null;

    @FXML
    void login(ActionEvent event){
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(usernameTextField.getText());
        loginBean.setPassword(passwordTextField.getText());
        LoginResult result = loginController.authenticate(loginBean);

        switch (result) {
            case LoginResult.UTENTE:
                if(plController != null){ //Significa che gliel'ha passato RiepilogoPrenotazioneGC per gestire la UserNotLoggedException, quindi è necessario cambiare flusso
                    try {
                        plController.registraPrenotazione();
                    } catch(PrenotazioneGiaPresenteException e){
                        showAlert(e.getMessage(), Alert.AlertType.ERROR, "Errore nell'aggiunta della prenotazione");
                    }
                }
                SceneChanger.changeScene("/com/example/hellofx/schermateUtente.fxml", event);
                break;
            case LoginResult.NON_AUTENTICATO:
                showAlert("Credenziali non valide", Alert.AlertType.ERROR, null);
                break;
            case LoginResult.BIBLIOTECARIO:
                SceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
                break;
        }

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

    public void showAlert(String message, Alert.AlertType alertType, String header){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();

    }
}