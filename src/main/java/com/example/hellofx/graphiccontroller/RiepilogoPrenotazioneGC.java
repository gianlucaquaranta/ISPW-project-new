package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.BibliotecaBean;
import com.example.hellofx.bean.FiltriBean;
import com.example.hellofx.bean.LibroBean;
import com.example.hellofx.bean.PrenotazioneBean;
import com.example.hellofx.controller.PLController;
import com.example.hellofx.exception.PrenotazioneGiaPresenteException;
import com.example.hellofx.exception.UserNotLoggedException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class RiepilogoPrenotazioneGC {

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
    private ObservableList<BibliotecaBean> bibliotecheTemp;
    private ObservableList<LibroBean> libriTemp;
    private FiltriBean filtriTemp;

    public void visualizzaRiepilogo(BibliotecaBean bb){

        PrenotazioneBean pb = plController.creaRiepilogo(bb);

        titoloLabelRiepilogo.setText("Titolo: "+pb.getLibro().getTitolo());
        autoreLabelRiepilogo.setText("Autore: "+pb.getLibro().getAutore());
        editoreLabelRiepilogo.setText("Editore: "+pb.getLibro().getEditore());
        isbnLabelRiepilogo.setText("ISBN: "+pb.getLibro().getIsbn());
        bibliotecaLabelRiepilogo.setText("Biblioteca: "+pb.getBibliotecaB().getNome());
        indirizzoLabelRiepilogo.setText("Indirizzo: "+pb.getBibliotecaB().getIndirizzoCompleto());
        inizioLabelRiepilogo.setText("Data di inizio: "+pb.getDataInizio());
        scadenzaLabelRiepilogo.setText("Data di scadenza: "+pb.getDataScadenza());

    }

    @FXML
    public void confermaPrenotazione(ActionEvent event){
        try {
            plController.registraPrenotazione();
        } catch (UserNotLoggedException e){
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
            SceneChanger.changeSceneWithController("/com/example/hellofx/login.fxml", event, (LoginGC lgc) -> lgc.setPlController(plController));
            return;
        } catch(PrenotazioneGiaPresenteException e){
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
            return;
        }
        SceneChanger.changeScene("/com/example/hellofx/schermateUtente.fxml",event);

    }

    @FXML
    public void annulla(ActionEvent event) {

        SceneChanger.changeSceneWithController(
                "/com/example/hellofx/risultatiBiblioteche.fxml",
                event,
                (RisultatiBibliotecheGC rbGC) -> {
                    rbGC.caricaDati(bibliotecheTemp);
                    rbGC.setPlController(plController);
                    rbGC.setLibriTemp(libriTemp);
                    rbGC.setFiltriTemp(filtriTemp);
                }
        );

    }

    public void setBibliotecheTemp(ObservableList<BibliotecaBean> bblist){
        bibliotecheTemp = bblist;
    }

    public void setLibriTemp(ObservableList<LibroBean> lblist){
        libriTemp = lblist;
    }

    @FXML
    public void setFiltriTemp(FiltriBean fb){filtriTemp = fb;}

    public void setPlController(PLController plcontroller){ this.plController = plcontroller; }

    public void showAlert(String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setHeaderText("Errore nell'aggiunta della prenotazione");
        alert.setContentText(message);
        alert.showAndWait();

    }
}
