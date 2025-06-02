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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ModificaCatalogoGC {
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
    private TableColumn<LibroBean, Void> opzioniCol;
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

        setUpOptions();
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

                        try {
                            // Carica il file FXML
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/hellofx/formAggiungiModificaLibro.fxml"));
                            Parent root = loader.load();

                            // Passaggio dati al controller
                            AggiungiModificaLibroGC aggiungiModificaLibroGC = loader.getController();
                            aggiungiModificaLibroGC.setForm(bean);

                            // Mostra la nuova scena
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
    void salva(ActionEvent event) throws IOException {
        aggiornaCatController.save();

        // Carica il file FXML di visualizzaCatalogo
        FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/hellofx/visualizzaCatalogo.fxml"));
        Parent root = loader.load();

        // Passaggio dati al controller di visualizzaCatalogo
        VisualizzaCatalogoGC visualizzaCatalogoGC = loader.getController();
        visualizzaCatalogoGC.mostraCatalogo(aggiornaCatController.getCatalogo());

        // Mostra la nuova scena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void aggiungiLibro(ActionEvent event) {
        Stage stage;
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/formAggiungiModificaLibro.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

