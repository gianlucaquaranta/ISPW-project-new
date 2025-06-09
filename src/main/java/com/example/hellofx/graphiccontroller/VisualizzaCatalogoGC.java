package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.AggiornaCatController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class VisualizzaCatalogoGC {
    @FXML
    private TableColumn<LibroBean, Integer> annoCol;
    @FXML
    private TableColumn<LibroBean, String> autoreCol;
    @FXML
    private TableColumn<LibroBean, Integer> copieCol;
    @FXML
    private TableColumn<LibroBean, Integer> copieDispCol;
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
    @FXML
    private MenuItem crescenteItem;
    @FXML
    private MenuItem decrescenteItem;

    AggiornaCatController aggiornaCatController;

    @FXML
    public void initialize() {
        CatalogoTableConfigurator.initializeIntegerColumns(annoCol, copieCol, copieDispCol);
        CatalogoTableConfigurator.initializeStringColumns(autoreCol, editoreCol, genereCol, isbnCol, titoloCol);

        crescenteItem.setOnAction(event -> {
            orderSplit.setText("Crescente");
            aggiornaCatController.orderByIsbn(tableView.getItems(), "Crescente");
        });

        decrescenteItem.setOnAction(event -> {
            orderSplit.setText("Decrescente");
            aggiornaCatController.orderByIsbn(tableView.getItems(), "Decrescente");
        });

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
                (ModificaCatalogoGC controller) -> {
                    controller.setAggiornaCatController(aggiornaCatController);
                    controller.mostraCatalogoModificabile(aggiornaCatController.getCatalogo());
                }
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

    public void setAggiornaCatController(AggiornaCatController c) {
        this.aggiornaCatController = c;
    }
}
