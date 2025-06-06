package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
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

public class VisualizzaCatalogoGC {
    @FXML
    private TableColumn<LibroBean, String> annoCol;
    @FXML
    private TableColumn<LibroBean, String> autoreCol;
    @FXML
    private TableColumn<LibroBean, String> copieCol;
    @FXML
    private TableColumn<LibroBean, String> copieDispCol;
    @FXML
    private TableColumn<LibroBean, String> editoreCol;
    @FXML
    private TableColumn<LibroBean, String> genereCol;
    @FXML
    private TableColumn<LibroBean, String> isbnCol;
    @FXML
    private TableColumn<LibroBean, String> titoloCol;
    @FXML
    private TableView<LibroBean> tableView;
    @FXML
    private SplitMenuButton orderSplit;
    @FXML
    private SplitMenuButton filterSplit;
    @FXML
    private TextField searchBar;

    AggiornaCatController aggiornaCatController = AggiornaCatControllerFactory.getInstance().createAggiornaCatController();

    @FXML
    public void initialize() {
        annoCol.setCellValueFactory(new PropertyValueFactory<>("Anno di pubblicazione"));
        autoreCol.setCellValueFactory(new PropertyValueFactory<>("Autore"));
        copieCol.setCellValueFactory(new PropertyValueFactory<>("Num. copie"));
        copieDispCol.setCellValueFactory(new PropertyValueFactory<>("Num. copie disponibili"));
        editoreCol.setCellValueFactory(new PropertyValueFactory<>("Editore"));
        genereCol.setCellValueFactory(new PropertyValueFactory<>("Genere"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titoloCol.setCellValueFactory(new PropertyValueFactory<>("Titolo"));
    }

    @FXML
    void mostraCatalogo(List<LibroBean> catalogo){
        ObservableList<LibroBean> data = FXCollections.observableArrayList(catalogo);
        aggiornaCatController.orderByIsbn(data, orderSplit.getText());
        tableView.setItems(data);
    }

    @FXML
    void search(ActionEvent event){
        this.mostraCatalogo(aggiornaCatController.searchByField(searchBar.getText(), filterSplit.getText()));
    }

    @FXML
    void modificaCatalogo(ActionEvent event) throws IOException {
        // Carica il file FXML di modificaCatalogo
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/modificaCatalogo.fxml"));
        Parent root = loader.load();

        // Passaggio dati al controller di modificaCatalogo
        ModificaCatalogoGC modificaCatalogoGC = loader.getController();
        modificaCatalogoGC.mostraCatalogoModificabile(aggiornaCatController.getCatalogo());

        // Mostra la nuova scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void home(ActionEvent event) {
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
}
