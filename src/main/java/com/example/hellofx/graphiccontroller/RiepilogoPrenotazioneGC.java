package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.controllerfactory.PLControllerFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class RiepilogoPrenotazioneGC {

    private Stage stage;
    private Parent root;

    @FXML
    private Label titoloLabelRiepilogo;
    @FXML
    private Label autoreLabelRiepilogo;
    @FXML
    private Label editoreLabelRiepilogo;
    @FXML
    private Label isbnLabelRiepilogo;
    @FXML
    private Label bibliotecaLabelRiepilogo;
    @FXML
    private Label indirizzoLabelRiepilogo;
    @FXML
    private Label inizioLabelRiepilogo;
    @FXML
    private Label scadenzaLabelRiepilogo;

    private PLController plController;
    private ObservableList<BibliotecaBean> datiTemp;

    public void visualizzaRiepilogo(BibliotecaBean bb){

        plController = PLControllerFactory.getInstance().createPLController();
        PrenotazioneBean pb = plController.creaRiepilogo(bb);

        titoloLabelRiepilogo.setText("Titolo: "+pb.getLibro().getTitolo());
        autoreLabelRiepilogo.setText("Autore: "+pb.getLibro().getAutore());
        editoreLabelRiepilogo.setText("Editore: "+pb.getLibro().getEditore());
        isbnLabelRiepilogo.setText("ISBN: "+pb.getLibro().getIsbn());
        bibliotecaLabelRiepilogo.setText("Biblioteca: "+pb.getBibliotecaB().getNome());
        indirizzoLabelRiepilogo.setText("Indirizzo: "+pb.getBibliotecaB().getIndirizzoCompleto());
        inizioLabelRiepilogo.setText("Data di inizio: "+pb.getDataInizio());
        scadenzaLabelRiepilogo.setText("Data di scadenza+ "+pb.getDataScadenza());

    }

    @FXML
    public void confermaPrenotazione(ActionEvent event) {

        plController.registraPrenotazione();

        try {
            root = FXMLLoader.load(getClass().getResource("/com/example/hellofx/schermateUtente.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void annulla(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hellofx/risultatiBiblioteche.fxml"));
            root = loader.load();
            RisultatiBibliotecheGC rbGC = loader.getController();
            rbGC.caricaDati(datiTemp);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setDatiTemp(ObservableList<BibliotecaBean> bblist){
        datiTemp = bblist;
    }

}
