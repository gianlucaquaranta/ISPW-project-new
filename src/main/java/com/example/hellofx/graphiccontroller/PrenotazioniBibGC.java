package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PrenotazioniBibController;
import com.example.hellofx.controllerfactory.PrenotazioniBibControllerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PrenotazioniBibGC {
    @FXML
    private TableColumn<PrenotazioneBean, Void> opzioniCol;
    @FXML
    private TableColumn<PrenotazioneBean, String> dataInizioCol;
    @FXML
    private TableColumn<PrenotazioneBean, String> emailCol;
    @FXML
    private SplitMenuButton filterSplit;
    @FXML
    private TableColumn<PrenotazioneBean, String> idCol;
    @FXML
    private TableColumn<PrenotazioneBean, String> isbnCol;
    @FXML
    private TableColumn<PrenotazioneBean, String> scadenzaCol;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<PrenotazioneBean> tableView;
    @FXML
    private TableColumn<PrenotazioneBean, String> titoloCol;
    @FXML
    private TableColumn<PrenotazioneBean, String> usernameCol;

    PrenotazioniBibController prenotazioniBibController = PrenotazioniBibControllerFactory.getInstance().createPrenotazioniBibController();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titoloCol.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        dataInizioCol.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
        scadenzaCol.setCellValueFactory(new PropertyValueFactory<>("scadenza"));

        setUpOptions();
    }

    private void setUpOptions() {
        opzioniCol.setCellFactory(param -> new TableCell<>() {

            private final Button noleggioBtn = new Button("Trasforma in noleggio");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    noleggioBtn.setOnAction(event -> {
                        // Popup di notifica
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Prenotazione trasformata");
                        alert.setHeaderText(null);
                        alert.setContentText("La prenotazione è stata trasformata con successo in un noleggio.");
                        alert.showAndWait();
                    });
                    noleggioBtn.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 15;");
                    setGraphic(noleggioBtn);
                }

            }
        });
    }

    @FXML
    void goHome(ActionEvent event) {
        SceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
    }

    @FXML
    void mostraPrenotazioni(List<PrenotazioneBean> prenotazioni){
        ObservableList<PrenotazioneBean> data = FXCollections.observableArrayList(prenotazioni);
        tableView.setItems(data);
    }

    @FXML
    void search(ActionEvent event){
        this.mostraPrenotazioni(prenotazioniBibController.searchByField(searchBar.getText(), filterSplit.getText()));
    }
}