package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.FiltriBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.Logout;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.PLControllerFactory;
import com.example.hellofx.exception.EmptyFiltersException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SchermateUtenteGC {

    //Prenota libro FXML
    @FXML
    private TabPane tabPane;
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

    private PLController plController;
    private FiltriBean filtriTemp;
    private static final String ACTIVE_TAB = "active-tab";

    //Inizializzazioni table view
    @FXML
    public void initialize() {
        // Prenota libro tables view
        titleColumnPL.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        authorColumnPL.setCellValueFactory(new PropertyValueFactory<>("autore"));
        editorColumnPL.setCellValueFactory(new PropertyValueFactory<>("editore"));
        isbnColumnPL.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        setupOptionColumnPL();

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (oldTab != null) {
                oldTab.getStyleClass().remove(ACTIVE_TAB);
            }
            if (newTab != null && !newTab.getStyleClass().contains(ACTIVE_TAB)) {
                newTab.getStyleClass().add(ACTIVE_TAB);
            }
        });

        // Aggiunge la classe alla tab iniziale
        Tab initialTab = tabPane.getSelectionModel().getSelectedItem();
        if (initialTab != null) {
            initialTab.getStyleClass().add(ACTIVE_TAB);
        }
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

                        SceneChanger.changeSceneWithController(
                                "/com/example/hellofx/risultatiBiblioteche.fxml",
                                event,
                                (RisultatiBibliotecheGC rbGC) -> {
                                    rbGC.setPlController(plController);
                                    rbGC.setLibriTemp(getTableView().getItems());
                                    rbGC.setFiltriTemp(filtriTemp);
                                    rbGC.visualizzaBiblioteche(bean);
                                    if(filtriTemp == null){ setFiltri(); }
                                }
                        );
                    });
                    button.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
                    setGraphic(button);
                }
            }
        });

    }

    //Prenota libro
    @FXML
    void cercaLibri(ActionEvent event){

        setFiltri();

        plController = PLControllerFactory.getInstance().createPLController();
        List<LibroBean> libriFiltrati;
        try {
            libriFiltrati = plController.filtra(filtriTemp);
        } catch(EmptyFiltersException e){
            mostraAlert(e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        if(libriFiltrati.isEmpty()){
            mostraAlert("Nessun risultato disponibile", Alert.AlertType.INFORMATION);
        }
        ObservableList<LibroBean> data = FXCollections.observableArrayList(libriFiltrati);
        tableViewBooksPL.setItems(data);

        
    }

    @FXML
    private void setFiltri(){
        String titolo = titoloTextField.getText();
        String autore = autoreTextField.getText();
        String isbn = isbnTextField.getText();
        String genere = genereSplitMenuButton.getText();
        if(genere.equals("Scegli un genere")) genere = ""; //il genere non è stato specificato
        String biblioteca = bibliotecaTextField.getText();
        String cap = capTextField.getText();
        filtriTemp = new FiltriBean(titolo, autore, genere, isbn, biblioteca, cap);
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

    @FXML
    void visualizzaNoleggi(ActionEvent event) {
        SceneChanger.changeScene("/com/example/hellofx/noleggiUtente.fxml", event);
    }

    @FXML
    void visualizzaPrenotazioni(ActionEvent event) {

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/prenotazioniUtente.fxml",
                event,
                PrenotazioniUtenteGC::getPrenotazioni
        );
    }

    @FXML
    void logout(ActionEvent event){
        new Logout().logout();
        SceneChanger.changeScene("/com/example/hellofx/login.fxml", event);
    }

    @FXML
    void setText(ActionEvent event){
        MenuItem selectedItem = (MenuItem) event.getSource();
        genereSplitMenuButton.setText(selectedItem.getText());
    }

    @FXML
    void caricaDati(FiltriBean fb){
        titoloTextField.setText(fb.getTitolo());
        autoreTextField.setText(fb.getAutore());
        isbnTextField.setText(fb.getIsbn());
        genereSplitMenuButton.setText(fb.getGenere());
        bibliotecaTextField.setText(fb.getBiblioteca());
        capTextField.setText(fb.getCap());
    }

    void setPlController(PLController plController){ this.plController = plController; }

    void mostraAlert(String messaggio, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
