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
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Optional;

public class ModificaCatalogoGC {
    @FXML
    private TableColumn<LibroBean, String> annoCol;
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
    private TableColumn<LibroBean, Void> opzioniCol;
    @FXML
    private TableView<LibroBean> tableView;
    @FXML
    private SplitMenuButton orderSplit;
    @FXML
    private SplitMenuButton filterSplit;
    @FXML
    private TextField searchBar;
    @FXML
    private MenuItem crescenteModificaItem;
    @FXML
    private MenuItem decrescenteModificaItem;

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

        setUpOptions();

        crescenteModificaItem.setOnAction(event -> {
            orderSplit.setText("Crescente");
            aggiornaCatController.orderByIsbn(tableView.getItems(), "Crescente");
        });

        decrescenteModificaItem.setOnAction(event -> {
            orderSplit.setText("Decrescente");
            aggiornaCatController.orderByIsbn(tableView.getItems(), "Decrescente");
        });
    }

    private void setUpOptions() {
        opzioniCol.setCellFactory(param -> new TableCell<>() {

            private final Button modificaBtn = new Button("Modifica");
            private final Button eliminaBtn = new Button("Elimina");
            private final HBox buttonBox = new HBox(10); // Spaziatura tra i bottoni

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    //Modifica
                    modificaBtn.setOnAction(event -> {
                        LibroBean bean = getTableView().getItems().get(getIndex());

                        SceneChanger.changeSceneWithController(
                                "/com/example/hellofx/formAggiungiModificaLibro.fxml",
                                event,
                                (AggiungiModificaLibroGC controller) -> {
                                    controller.setForm(bean);
                                    controller.setEditMode(true);
                                }
                        );
                    });

                    // Elimina
                    eliminaBtn.setOnAction(event -> {
                        LibroBean bean = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Conferma eliminazione");
                        alert.setHeaderText("Sei sicuro di voler eliminare questo libro?");
                        alert.setContentText("Titolo: " + bean.getTitolo());

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            getTableView().getItems().remove(bean);
                            aggiornaCatController.delete(bean);
                        }
                    });
                    modificaBtn.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold;-fx-font-size: 15;");
                    eliminaBtn.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 15;");
                    buttonBox.getChildren().setAll(modificaBtn, eliminaBtn);
                    setGraphic(buttonBox);
                }

            }
        });
    }

    @FXML
    void mostraCatalogoModificabile(List<LibroBean> catalogo){
        ObservableList<LibroBean> data = FXCollections.observableArrayList(catalogo);
        aggiornaCatController.orderByIsbn(data, orderSplit.getText());
        tableView.setItems(data);
    }

    @FXML
    void search(ActionEvent event){
        this.mostraCatalogoModificabile(aggiornaCatController.searchByField(searchBar.getText(), filterSplit.getText()));

    }

    @FXML
    void goHome(ActionEvent event) {
        SceneChanger.changeScene("/com/example/hellofx/homeBibliotecario.fxml", event);
    }

    @FXML
    void salva(ActionEvent event) {
        aggiornaCatController.save();

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/visualizzaCatalogo.fxml",
                event,
                (VisualizzaCatalogoGC controller) -> controller.mostraCatalogo(aggiornaCatController.getCatalogo())
        );

    }

    @FXML
    void aggiungiLibro(ActionEvent event) {

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/formAggiungiModificaLibro.fxml",
                event,
                (AggiungiModificaLibroGC controller) -> controller.setEditMode(false)
        );
    }

    @FXML
    void setFilterSplitText(ActionEvent event){
        MenuItem selectedItem = (MenuItem) event.getSource();
        filterSplit.setText(selectedItem.getText());
    }

}