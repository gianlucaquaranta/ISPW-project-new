package com.example.hellofx.graphiccontroller;

import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controller.PrenotazioniBibController;
import com.example.hellofx.controller.Logout;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import com.example.hellofx.controllerfactory.PrenotazioniBibControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeBibGC {

    @FXML
    void visualizzaCatalogo(ActionEvent event) {
        AggiornaCatController aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/visualizzaCatalogo.fxml",
                event,
                (VisualizzaCatalogoGC controller) -> {
                    controller.setAggiornaCatController(aggiornaCatController);
                    controller.mostraCatalogo(aggiornaCatController.getCatalogo());
                }
        );
    }

    @FXML
    void visualizzaRegistroNoleggi(ActionEvent event){
        SceneChanger.changeScene("/com/example/hellofx/visualizzaNoleggi.fxml", event);
    }

    @FXML
    void visualizzaPrenotazioni(ActionEvent event) {

        PrenotazioniBibController prenotazioniBibController = PrenotazioniBibControllerFactory.getInstance().createPrenotazioniBibController();

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/visualizzaPrenotazioni.fxml",
                event,
                (PrenotazioniBibGC controller) -> controller.mostraPrenotazioni(prenotazioniBibController.getPrenotazioni())
        );
    }

    @FXML
    void logout(ActionEvent event) {
        new Logout().logout();
        SceneChanger.changeScene("/com/example/hellofx/login.fxml", event);
    }
}