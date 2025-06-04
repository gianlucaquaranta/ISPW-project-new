package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.FiltriBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.TrovaPrezziBean;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controller.TrovaPrezziController;
import com.example.hellofx.controllerfactory.PLControllerFactory;
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

    //Prenota libro FXML
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
    @FXML
    private TableView<LibroBean> tableViewBooksPL;
    @FXML
    private TableColumn<LibroBean, String> titleColumnPL;
    @FXML
    private TableColumn<LibroBean, String> authorColumnPL;
    @FXML
    private TableColumn<LibroBean, String> editorColumnPL;
    @FXML
    private TableColumn<LibroBean, String> isbnColumnPL;
    @FXML
    private TableColumn<LibroBean, Void> optionColumnPL;


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
    private TableColumn<TrovaPrezziBean, String> annoPubblicazioneTP;
    @FXML
    private TableColumn<TrovaPrezziBean, String> priceColumnTP;
    @FXML
    private TableColumn<TrovaPrezziBean, String> vendorColumnTP;
    @FXML
    private TableColumn<TrovaPrezziBean, Void> optionColumnTP;

    //Inizializzazioni table view
    @FXML
    public void initialize() {
        // Prenota libro tables view
        titleColumnPL.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        authorColumnPL.setCellValueFactory(new PropertyValueFactory<>("autore"));
        editorColumnPL.setCellValueFactory(new PropertyValueFactory<>("editore"));
        isbnColumnPL.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        setupOptionColumnPL();

        // Trova prezzi table view
        titleColumnTP.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        authorColumnTP.setCellValueFactory(new PropertyValueFactory<>("autore"));
        editorColumnTP.setCellValueFactory(new PropertyValueFactory<>("editore"));
        annoPubblicazioneTP.setCellValueFactory(new PropertyValueFactory<>("annoPubblicazione"));
        priceColumnTP.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        vendorColumnTP.setCellValueFactory(new PropertyValueFactory<>("vendor"));

        setupOptionColumnTP();
    }

    private void setupOptionColumnPL(){
        optionColumnPL.setCellFactory(param -> new TableCell<>() {
            private Button button = new Button("Visualizza biblioteche");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        LibroBean bean = getTableView().getItems().get(getIndex());
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/risultatiBiblioteche.fxml"));
                            root = loader.load(); // Carica la scena e istanzia il controller
                            // Ottiene l'istanza del controller legata alla scena
                            RisultatiBibliotecheGC rbGC = loader.getController();
                            rbGC.setDatiTemp(getTableView().getItems());
                            // Chiama il metodo sul controller effettivo
                            rbGC.visualizzaBiblioteche(bean);
                            // Cambia scena
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    private void setupOptionColumnTP(){
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



    //Prenota libro
    @FXML
    void cercaLibri(ActionEvent event) throws IOException {

        String titolo = titoloTextField.getText();
        String autore = autoreTextField.getText();
        String isbn = isbnTextField.getText();
        String genere = genereSplitMenuButton.getText();
        String biblioteca = bibliotecaTextField.getText();
        String cap = capTextField.getText();

        FiltriBean filtriBean = new FiltriBean(titolo, autore, genere, isbn, biblioteca, cap);
        PLController plController = PLControllerFactory.getInstance().createPLController();
        List<LibroBean> libriFiltrati = plController.filtra(filtriBean);

        if(libriFiltrati.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null); // nessun header
            alert.setContentText("Nessun risultato disponibile");
            alert.showAndWait();
        }
        ObservableList<LibroBean> data = FXCollections.observableArrayList(libriFiltrati);
        tableViewBooksPL.setItems(data);

        
    }


    @FXML
    void salvaRicerca(ActionEvent event) throws IOException {
        //TODO
    }

    @FXML
    void rimuoviFiltri(ActionEvent event) {

        titoloTextField.clear();
        autoreTextField.clear();
        isbnTextField.clear();
        genereSplitMenuButton.setText("Scegli un genere");
        bibliotecaTextField.clear();
        capTextField.clear();

    }

    //Trova prezzi
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/prenotazioniUtente.fxml"));
            root = loader.load();
            PrenotazioniUtenteGC puGC = loader.getController();
            puGC.getPrenotazioni();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void caricaDati(ObservableList<LibroBean> lblist){ tableViewBooksPL.setItems(lblist); }

}
