package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PrenotazioniBibController;
import com.example.hellofx.controllerfactory.PrenotazioniBibControllerFactory;
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
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titoloCol.setCellValueFactory(new PropertyValueFactory<>("Titolo"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        dataInizioCol.setCellValueFactory(new PropertyValueFactory<>("Data di inizio"));
        scadenzaCol.setCellValueFactory(new PropertyValueFactory<>("Scadenza"));

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
                    noleggioBtn.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
                    setGraphic(noleggioBtn);
                }

            }
        });
    }

    @FXML
    void goHome(ActionEvent event) {
        Stage stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/homeBibliotecario.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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