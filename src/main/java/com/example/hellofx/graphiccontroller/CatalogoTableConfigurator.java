package com.example.hellofx.graphiccontroller;

import com.example.hellofx.bean.LibroBean;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CatalogoTableConfigurator {

    private CatalogoTableConfigurator(){}

    public static void initializeIntegerColumns(
            TableColumn<LibroBean, Integer> annoCol,
            TableColumn<LibroBean, Integer> copieCol,
            TableColumn<LibroBean, Integer> copieDispCol
    ){
        annoCol.setCellValueFactory(new PropertyValueFactory<>("annoPubblicazione"));
        copieCol.setCellValueFactory(new PropertyValueFactory<>("copie"));
        copieDispCol.setCellValueFactory(new PropertyValueFactory<>("copieDisponibili"));

    }

    public static void initializeStringColumns(
            TableColumn<LibroBean, String> autoreCol,
            TableColumn<LibroBean, String> editoreCol,
            TableColumn<LibroBean, String> genereCol,
            TableColumn<LibroBean, String> isbnCol,
            TableColumn<LibroBean, String> titoloCol
    ) {
        autoreCol.setCellValueFactory(new PropertyValueFactory<>("autore"));
        editoreCol.setCellValueFactory(new PropertyValueFactory<>("editore"));
        genereCol.setCellValueFactory(new PropertyValueFactory<>("genereString"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titoloCol.setCellValueFactory(new PropertyValueFactory<>("titolo"));
    }
}
