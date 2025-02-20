package com.example.hellofx;

//import com.example.hellofx.cli.CliManager;
import com.example.hellofx.cli.CliManager;
import com.example.hellofx.entity.Biblioteca;
import com.example.hellofx.entity.Bibliotecario;
import com.example.hellofx.entity.Utente;
import com.example.hellofx.session.BibliotecarioSession;
import com.example.hellofx.session.Session;
import com.example.hellofx.session.UtenteSession;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("BOOKISH - Biblioteche a portata di click!");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\gianl\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        launch(args);

        CliManager manager = new CliManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Quale interfaccia desideri?\n1. CLI\n2. GUI\nInserisci 0 o 1 per effettuare la scelta e premi ENTER: ");
        boolean isGui = scanner.nextInt() != 0;
        scanner.nextLine();

        System.out.println("Quale versione desideri?\n1. Demo\n2. Full mode\nInserisci 0 o 1 per effettuare la scelta e premi ENTER: ");
        boolean isFull = scanner.nextInt() != 0;
        scanner.nextLine();

        System.out.println("Quale tipo di persistenza desideri per gli utenti e le prenotazioni?\n1. DB\n2. File system \nInserisci 0 o 1 per effettuare la scelta e premi ENTER: ");
        boolean isFile = scanner.nextInt() != 0;
        scanner.nextLine();

        Session.setFile(isFile);
        Session.setFull(isFull);

        if(isGui) {
            launch(args);
        } else {
            manager.start();
        }
    }

}