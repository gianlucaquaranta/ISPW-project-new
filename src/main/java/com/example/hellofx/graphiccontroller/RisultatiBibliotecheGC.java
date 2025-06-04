package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.PLControllerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RisultatiBibliotecheGC {

    @FXML
    private TableView<BibliotecaBean> tableViewBib;
    @FXML
    private TableColumn<BibliotecaBean, String> nomeColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> indirizzoColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> civicoColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> cittaColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> capColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> provinciaColumn;
    @FXML
    private TableColumn<BibliotecaBean, Void> opzioniColumn;

    PLController controller;
    private ObservableList<LibroBean> datiTemp;

    //Inizializzazione table view
    @FXML
    public void initialize() {

        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("Indirizzo"));
        civicoColumn.setCellValueFactory(new PropertyValueFactory<>("Numero civico"));
        cittaColumn.setCellValueFactory(new PropertyValueFactory<>("Città"));
        capColumn.setCellValueFactory(new PropertyValueFactory<>("CAP"));
        provinciaColumn.setCellValueFactory(new PropertyValueFactory<>("Provincia"));
        opzioniColumn.setCellValueFactory(new PropertyValueFactory<>("Opzioni"));

        setupOptionColumn();
    }

    private void setupOptionColumn(){

        opzioniColumn.setCellFactory(param -> new TableCell<>() {
            private Button button = new Button("Prenota Libro");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        BibliotecaBean bb = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/riepilogoPrenotazione.fxml"));
                            Parent root = loader.load(); // Carica la scena e istanzia il controller
                            // Ottiene l'istanza del controller legata alla scena
                            RiepilogoPrenotazioneGC rpGC = loader.getController();
                            rpGC.setDatiTemp(getTableView().getItems());
                            // Chiama il metodo sul controller effettivo
                            rpGC.visualizzaRiepilogo(bb);
                            // Cambia scena
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    button.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
                    setGraphic(button);
                }
            }
        });

    }

    @FXML
    public void visualizzaBiblioteche(LibroBean lb){

        PLController plController = PLControllerFactory.getInstance().createPLController();
        List<BibliotecaBean> bblist = plController.getBiblioteche(lb);

        ObservableList<BibliotecaBean> data = FXCollections.observableArrayList(bblist);
        tableViewBib.setItems(data);

    }

    @FXML
    public void indietro(ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/schermateUtente.fxml"));
            Parent root = loader.load();
            SchermateUtenteGC suGC = loader.getController();
            suGC.caricaDati(datiTemp);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void setDatiTemp(ObservableList<LibroBean> lblist){
        datiTemp = lblist;
    }

    @FXML
    public void caricaDati(ObservableList<BibliotecaBean> bblist){ tableViewBib.setItems(bblist); }

}
