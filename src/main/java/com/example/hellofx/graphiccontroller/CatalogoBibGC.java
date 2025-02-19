package com.example.hellofx.graphiccontroller;

//import com.example.hellofx.bean.LibroBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class CatalogoBibGC {
    /*
    @FXML
    private TableColumn<LibroBean, String> annoCol;
    @FXML
    private TableColumn<LibroBean, String> autoreCol;
    @FXML
    private TableColumn<LibroBean, String> copeCol;
    @FXML
    private TableColumn<LibroBean, String> copertinaCol;
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
    */
    @FXML
    private TableView tableView;
    @FXML
    private Button modificaCatalogo;
    @FXML
    private Button home;

    //private ObservableList<LibroBean> observableList = FXCollections.observableArrayList();

    @FXML
    void modificaCatalogo(ActionEvent event) { //cambio scena
        Stage stage;
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/modificaCatalogo.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
