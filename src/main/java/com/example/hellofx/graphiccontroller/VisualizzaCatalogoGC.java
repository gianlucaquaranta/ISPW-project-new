package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import com.example.hellofx.controllerfactory.AggiornaCatControllerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
        annoCol.setCellValueFactory(new PropertyValueFactory<>("annoPubblicazione"));
        autoreCol.setCellValueFactory(new PropertyValueFactory<>("autore"));
        copieCol.setCellValueFactory(new PropertyValueFactory<>("copie"));
        copieDispCol.setCellValueFactory(new PropertyValueFactory<>("copieDisponibili"));
        editoreCol.setCellValueFactory(new PropertyValueFactory<>("editore"));
        genereCol.setCellValueFactory(new PropertyValueFactory<>("genereString"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titoloCol.setCellValueFactory(new PropertyValueFactory<>("titolo"));
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
    void modificaCatalogo(ActionEvent event) {

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/modificaCatalogo.fxml",
                event,
                (ModificaCatalogoGC controller) -> controller.mostraCatalogoModificabile(aggiornaCatController.getCatalogo())
        );
    }

    @FXML
    void home(ActionEvent event){
        SceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
    }

    @FXML
    void setFilterSplitText(ActionEvent event){
        MenuItem selectedItem = (MenuItem) event.getSource();
        filterSplit.setText(selectedItem.getText());
    }

    @FXML
    void setOrderSplitText(ActionEvent event){
        MenuItem selectedItem = (MenuItem) event.getSource();
        orderSplit.setText(selectedItem.getText());
    }
}
