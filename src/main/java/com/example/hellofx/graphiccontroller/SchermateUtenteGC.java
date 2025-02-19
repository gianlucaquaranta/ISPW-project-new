package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.FiltriBean;
import com.example.hellofx.bean.TrovaPrezziBean;
import com.example.hellofx.controller.TrovaPrezziController;
import com.example.hellofx.controllerfactory.TrovaPrezziControllerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SchermateUtenteGC {
    private Stage stage;
    private Parent root;
    @FXML
    private TextField titoloTextField;
    @FXML
    private TextField autoreTextField;
    @FXML
    private TextField isbnTextField;
    @FXML
    private SplitMenuButton genereSplitMenuButton;
    @FXML
    private TextField bibliotecaTextField;
    @FXML
    private TextField capTextField;
    @FXML
    private TextField raggioTextField;

    //Trova prezzi FXML
    @FXML
    private TextField ricercaField;
    @FXML
    private CheckBox mondadoriCheck;
    @FXML
    private CheckBox feltrinelliCheck;
    @FXML
    private CheckBox ibsCheck;
    @FXML
    private TableView<TrovaPrezziBean> tableViewTP;
    @FXML
    private TableColumn<TrovaPrezziBean, String> titleColumnTP;
    @FXML
    private TableColumn<TrovaPrezziBean, String> authorColumnTP;
    @FXML
    private TableColumn<TrovaPrezziBean, String> editorColumnTP;
    @FXML
    private TableColumn<TrovaPrezziBean, String> priceColumnTP;
    @FXML
    private TableColumn<TrovaPrezziBean, String> vendorColumnTP;
    @FXML
    private TableColumn<TrovaPrezziBean, Void> optionColumnTP;

    //Inizializzazioni table view
    @FXML
    public void initialize() {
        // Trova prezzi table view
        titleColumnTP.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        authorColumnTP.setCellValueFactory(new PropertyValueFactory<>("autore"));
        editorColumnTP.setCellValueFactory(new PropertyValueFactory<>("editore"));
        priceColumnTP.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        vendorColumnTP.setCellValueFactory(new PropertyValueFactory<>("vendor"));

        // Configurazione colonna opzioni per aggiungere bottoni a trova prezzi
        optionColumnTP.setCellFactory(param -> new TableCell<>() {
            private Button button = new Button("Vai al sito");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        TrovaPrezziBean bean = getTableView().getItems().get(getIndex());
                        String link = bean.getLink();
                        try {
                            Desktop.getDesktop().browse(new URI(link));
                        } catch (Exception e) {
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Impossibile aprire il link.").showAndWait();
                        }

                    });
                    button.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
                    setGraphic(button);
                }
            }
        });
    }


    //Prenota libro GC
    @FXML
    void cercaLibro(ActionEvent event) throws IOException {

        String titolo = titoloTextField.getText();
        String autore = autoreTextField.getText();
        String isbn = isbnTextField.getText();
        String genere = genereSplitMenuButton.getText();
        String biblioteca = bibliotecaTextField.getText();
        String cap = capTextField.getText();
        String raggio = raggioTextField.getText();

        FiltriBean filtriBean = new FiltriBean(titolo, autore, genere, isbn, biblioteca, cap, raggio);

        
    }
    
    
    //Trova prezzi GC
    @FXML
    void cercaPrezzi(ActionEvent event) throws IOException {

        tableViewTP.getItems().clear();
        String ricerca = ricercaField.getText();
        if (ricerca.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inserisci un titolo o un autore");
            alert.showAndWait();
        }

        TrovaPrezziBean trovaPrezziBean = new TrovaPrezziBean();
        trovaPrezziBean.setRicerca(ricerca);

        List<String> vendors = new ArrayList<>();

        // Controlla ogni checkbox e aggiunge il testo se è selezionata
        if (mondadoriCheck.isSelected()) vendors.add(mondadoriCheck.getText());
        if (feltrinelliCheck.isSelected()) vendors.add(feltrinelliCheck.getText());
        if (ibsCheck.isSelected()) vendors.add(ibsCheck.getText());

        if (vendors.isEmpty()) {
            vendors.add(mondadoriCheck.getText());
            vendors.add(feltrinelliCheck.getText());
            vendors.add(ibsCheck.getText());
        }

        trovaPrezziBean.setVendors(vendors);

        TrovaPrezziController trovaPrezziController = TrovaPrezziControllerFactory.getInstance().createTrovaPrezziController();
        List<TrovaPrezziBean> risultati = trovaPrezziController.trovaPrezzi(trovaPrezziBean);

        ObservableList<TrovaPrezziBean> data = FXCollections.observableArrayList(risultati);
        tableViewTP.setItems(data);

    }

    @FXML
    void visualizzaNoleggi(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/noleggiUtente.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void visualizzaPrenotazioni(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/prenotazioniUtente.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
