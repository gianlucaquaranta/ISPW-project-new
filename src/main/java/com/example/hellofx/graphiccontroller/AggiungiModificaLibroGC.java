package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.GenereBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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

    AggiornaCatController aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();

    @FXML
    void annulla(ActionEvent event) throws IOException {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/modificaCatalogo.fxml"));
        Parent root = loader.load();

        // Passaggio dati al controller
        ModificaCatalogoGC modificaCatalogoGC = loader.getController();
        modificaCatalogoGC.mostraCatalogoModificabile(aggiornaCatController.getCatalogo());

        // Mostra la nuova scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void conferma(ActionEvent event) throws IOException {
        //setup della bean
        LibroBean bean = new LibroBean();
        bean.setTitolo(titoloTextField.getText());
        bean.setAutore(autoreTextField.getText());
        bean.setIsbn(isbnTextField.getText());
        bean.setEditore(editoreTextField.getText());
        bean.setGenere(GenereBean.fromString(copieTotaliTextField.getText()));
        bean.setAnnoPubblicazione(Integer.parseInt(annoTextField.getText()));
        Integer[] copie = {Integer.parseInt(copieTotaliTextField.getText()), -1};
        bean.setNumCopie(copie);

        aggiornaCatController.update(bean);

        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/modificaCatalogo.fxml"));
        Parent root = loader.load();

        // Passaggio dati al controller
        ModificaCatalogoGC modificaCatalogoGC = loader.getController();
        modificaCatalogoGC.mostraCatalogoModificabile(aggiornaCatController.getCatalogo());

        // Mostra la nuova scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void setForm(LibroBean libroBean){
        titoloTextField.setText(libroBean.getTitolo());
        isbnTextField.setText(libroBean.getIsbn());
        autoreTextField.setText(libroBean.getAutore());
        editoreTextField.setText(libroBean.getEditore());
        annoTextField.setText(Integer.toString(libroBean.getAnnoPubblicazione()));
        copieTotaliTextField.setText(Integer.toString(libroBean.getNumCopie()[0]));
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


}
