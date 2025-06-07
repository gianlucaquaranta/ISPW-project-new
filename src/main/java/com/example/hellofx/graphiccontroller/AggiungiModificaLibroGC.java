package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.GenereBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

public class AggiungiModificaLibroGC {
    @FXML
    private TextField annoTextField;
    @FXML
    private TextField autoreTextField;
    @FXML
    private TextField copieTotaliTextField;
    @FXML
    private TextField editoreTextField;
    @FXML
    private SplitMenuButton genereSplit;
    @FXML
    private TextField isbnTextField;
    @FXML
    private TextField titoloTextField;

    boolean isEditMode;

    AggiornaCatController aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();

    @FXML
    void annulla(ActionEvent event) {
        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/modificaCatalogo.fxml",
                event,
                (ModificaCatalogoGC controller) -> controller.mostraCatalogoModificabile(aggiornaCatController.getCatalogo())
        );
    }

    @FXML
    void conferma(ActionEvent event) {
        try {
            // Setup della bean
            LibroBean bean = new LibroBean();
            bean.setTitolo(titoloTextField.getText());
            bean.setAutore(autoreTextField.getText());
            bean.setIsbn(isbnTextField.getText());
            bean.setEditore(editoreTextField.getText());
            bean.setGenere(GenereBean.fromString(genereSplit.getText()));
            bean.setAnnoPubblicazione(Integer.parseInt(annoTextField.getText()));
            Integer[] copie = {Integer.parseInt(copieTotaliTextField.getText()), -1};
            bean.setNumCopie(copie);

            // Validazione
            AggiornaCatController.validateLibroBean(bean);

            // Azione
            if (this.isEditMode) {
                aggiornaCatController.update(bean);
            } else {
                aggiornaCatController.add(bean);
            }

            // Cambio scena
            SceneChanger.changeSceneWithController(
                    "/com/example/hellofx/modificaCatalogo.fxml",
                    event,
                    (ModificaCatalogoGC controller) -> controller.mostraCatalogoModificabile(aggiornaCatController.getCatalogo())
            );

        } catch (NumberFormatException _) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di input numerico");
            alert.setHeaderText("Controlla i campi numerici");
            alert.setContentText("Anno di pubblicazione o numero di copie non validi.");
            alert.showAndWait();
        } catch (IllegalArgumentException _) {
            // Notifica errore all’utente
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di validazione");
            alert.setHeaderText("Dati mancanti o non validi");
            alert.showAndWait();
        }
    }

    @FXML
    void setForm(LibroBean libroBean){
        titoloTextField.setText(libroBean.getTitolo());
        isbnTextField.setText(libroBean.getIsbn());
        autoreTextField.setText(libroBean.getAutore());
        editoreTextField.setText(libroBean.getEditore());
        annoTextField.setText(Integer.toString(libroBean.getAnnoPubblicazione()));
        copieTotaliTextField.setText(Integer.toString(libroBean.getCopie()));
        for (MenuItem item : genereSplit.getItems()) {
            if (item.getText().equalsIgnoreCase(libroBean.getGenere().getNome())) {
                genereSplit.setText(item.getText()); // Aggiorna il testo visibile del pulsante
                break;
            }
        }
    }

    @FXML
    void setText(ActionEvent event){
        MenuItem selectedItem = (MenuItem) event.getSource();
        genereSplit.setText(selectedItem.getText());
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }
}