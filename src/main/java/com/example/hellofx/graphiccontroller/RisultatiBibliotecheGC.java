package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.LibroBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class RisultatiBibliotecheGC {
    private Stage stage;
    private Parent root;

    @FXML
    private TableView<BibliotecaBean> tableViewBibPL;
    @FXML
    private TableColumn<BibliotecaBean, String> nomeColumnPL;
    @FXML
    private TableColumn<BibliotecaBean, String> indirizzoColumnPL;
    @FXML
    private TableColumn<BibliotecaBean, String> civicoColumnPL;
    @FXML
    private TableColumn<BibliotecaBean, String> cittaColumnPL;
    @FXML
    private TableColumn<BibliotecaBean, String> capColumnPL;
    @FXML
    private TableColumn<BibliotecaBean, String> opzioniColumnPL;


    public void visualizzaBiblioteche(LibroBean libro){

    }

    @FXML
    void indietro(ActionEvent event) {

        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/schermateUtente"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
