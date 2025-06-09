package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PrenotazioniUtenteController;
import com.example.hellofx.controllerfactory.PrenotazioniUtenteControllerFactory;
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

public class PrenotazioniUtenteGC {

    @FXML
    private TableColumn<PrenotazioneBean, String> titoloCol;

    @FXML
    private TableColumn<PrenotazioneBean, String> autoreCol;

    @FXML
    private TableColumn<PrenotazioneBean, String> bibliotecaCol;

    @FXML
    private TableColumn<PrenotazioneBean, String> editoreCol;

    @FXML
    private TableColumn<PrenotazioneBean, String> indirizzoCol;

    @FXML
    private TableColumn<PrenotazioneBean, String> isbnCol;

    @FXML
    private TableColumn<PrenotazioneBean, String> scadenzaCol;

    @FXML
    private TableColumn<PrenotazioneBean, Void> opzioneCol;

    @FXML
    private TableView<PrenotazioneBean> tableView;

    private PrenotazioniUtenteController controller = PrenotazioniUtenteControllerFactory.getInstance().createPrenotazioniUtenteController();

    @FXML
    public void initialize() {

        titoloCol.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        autoreCol.setCellValueFactory(new PropertyValueFactory<>("autore"));
        bibliotecaCol.setCellValueFactory(new PropertyValueFactory<>("biblioteca"));
        editoreCol.setCellValueFactory(new PropertyValueFactory<>("editore"));
        indirizzoCol.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        scadenzaCol.setCellValueFactory(new PropertyValueFactory<>("dataScadenza"));

        setupOptionColumn();

    }

    private void setupOptionColumn(){
        opzioneCol.setCellFactory(param -> new TableCell<>() {
            private Button button = new Button("Elimina");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        PrenotazioneBean bean = getTableView().getItems().get(getIndex());
                        gestisciEliminazione(bean);
                    });
                    button.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
                    setGraphic(button);
                }
            }
        });

    }

    @FXML
    void getPrenotazioni(){
        List<PrenotazioneBean> pblist = controller.retrievePrenotazioni();
        if(pblist.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null); // nessun header
            alert.setContentText("Non hai prenotazioni attive!");
            alert.showAndWait();
        } else {
            ObservableList<PrenotazioneBean> data = FXCollections.observableArrayList(pblist);
            tableView.setItems(data);
        }
    }

    @FXML
    void gestisciEliminazione(PrenotazioneBean bean) {
        controller.delete(bean);
        tableView.getItems().remove(bean);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null); // nessun header
        alert.setContentText("Eliminazione avvenuta!");
        alert.showAndWait();

    }

    @FXML
    public void indietro(ActionEvent event){
        Parent root;
        Stage stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/schermateUtente.fxml"));
            root = loader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

