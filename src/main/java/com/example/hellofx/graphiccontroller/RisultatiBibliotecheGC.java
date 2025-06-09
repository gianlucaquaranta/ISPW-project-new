package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.FiltriBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.controller.PLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RisultatiBibliotecheGC {

    @FXML
    private TableView<BibliotecaBean> tableViewBib;
    @FXML
    private TableColumn<BibliotecaBean, String> idColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> nomeColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> indirizzoColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> civicoColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> cittaColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> capColumn;
    @FXML
    private TableColumn<BibliotecaBean, String> provinciaColumn;
    @FXML
    private TableColumn<BibliotecaBean, Void> opzioniColumn;

    private PLController plController;
    private ObservableList<LibroBean> libriTemp;
    private FiltriBean filtriTemp;

    //Inizializzazione table view
    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBiblioteca"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        indirizzoColumn.setCellValueFactory(new PropertyValueFactory<>("Indirizzo"));
        civicoColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCivico"));
        cittaColumn.setCellValueFactory(new PropertyValueFactory<>("citta"));
        capColumn.setCellValueFactory(new PropertyValueFactory<>("cap"));
        provinciaColumn.setCellValueFactory(new PropertyValueFactory<>("provincia"));

        setupOptionColumn();
    }

    private void setupOptionColumn(){

        opzioniColumn.setCellFactory(param -> new TableCell<>() {
            private Button button = new Button("Prenota Libro");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        BibliotecaBean bb = getTableView().getItems().get(getIndex());

                        SceneChanger.changeSceneWithController(
                                "/com/example/hellofx/riepilogoPrenotazione.fxml",
                                event,
                                (RiepilogoPrenotazioneGC rpGC) -> {
                                    rpGC.setPlController(plController);
                                    rpGC.setBibliotecheTemp(getTableView().getItems());
                                    rpGC.setLibriTemp(libriTemp);
                                    rpGC.setFiltriTemp(filtriTemp);
                                    rpGC.visualizzaRiepilogo(bb);
                                }
                        );
                    });
                    button.setStyle("-fx-background-color: #0b6b75; -fx-background-radius: 8; -fx-text-fill: #ffffff; -fx-font-weight: bold;");
                    setGraphic(button);
                }
            }
        });

    }

    @FXML
    public void visualizzaBiblioteche(LibroBean lb){

        List<BibliotecaBean> bblist = plController.getBiblioteche(lb);

        ObservableList<BibliotecaBean> data = FXCollections.observableArrayList(bblist);
        tableViewBib.setItems(data);

    }

    @FXML
    public void indietro(ActionEvent event){

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/schermateUtente.fxml",
                event,
                (SchermateUtenteGC suGC) -> {
                    suGC.caricaDati(libriTemp, filtriTemp);
                    suGC.setPlController(plController);
                }
        );

    }

    public void setPlController(PLController plcontroller){ this.plController = plcontroller; }
    @FXML
    public void setLibriTemp(ObservableList<LibroBean> lblist){
        libriTemp = lblist;
    }

    @FXML
    public void setFiltriTemp(FiltriBean fb){filtriTemp = fb;}

    @FXML
    public void caricaDati(ObservableList<BibliotecaBean> bblist){ tableViewBib.setItems(bblist); }

}
