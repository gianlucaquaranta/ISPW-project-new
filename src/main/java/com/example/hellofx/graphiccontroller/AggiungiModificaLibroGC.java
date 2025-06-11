package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.GenereBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.exception.CopieDisponibiliException;
import com.example.hellofx.exception.LibroGiaPresenteException;
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

    AggiornaCatController aggiornaCatController;

    @FXML
    void annulla(ActionEvent event) {
        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/modificaCatalogo.fxml",
                event,
                (ModificaCatalogoGC controller) -> {
                    controller.setAggiornaCatController(aggiornaCatController);
                    controller.mostraCatalogoModificabile(aggiornaCatController.getCatalogo());
                }
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
            bean.validate();

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
                    (ModificaCatalogoGC controller) -> {
                        controller.setAggiornaCatController(aggiornaCatController);
                        controller.mostraCatalogoModificabile(aggiornaCatController.getCatalogo());
                    }
            );

        } catch (NumberFormatException _) {
            this.showAlert("Errore - Input numerico", "Controlla i campi numerici", "Anno di pubblicazione o numero di copie non validi.");
        } catch (IllegalArgumentException e) {
            this.showAlert("Errore di validazione", "Dati mancanti o non validi", e.getMessage());
        } catch (LibroGiaPresenteException e){
            this.showAlert("Errore - Libro già esistente", "Il libro inserito è già in catalogo", e.getMessage());
        } catch (CopieDisponibiliException e){
            this.showAlert("Errore - Numero di copie", "Numero di copie non valido", e.getMessage());
        }
    }

    @FXML
    void setForm(LibroBean libroBean){
        if(isEditMode) lockIsbnField();

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

    private void showAlert(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void setAggiornaCatController(AggiornaCatController c) {
        this.aggiornaCatController = c;
    }

    private void lockIsbnField() {
        isbnTextField.setEditable(false);
        isbnTextField.setDisable(true);
        isbnTextField.setStyle("-fx-opacity: 1; -fx-background-color: #f0f0f0;");
    }
}