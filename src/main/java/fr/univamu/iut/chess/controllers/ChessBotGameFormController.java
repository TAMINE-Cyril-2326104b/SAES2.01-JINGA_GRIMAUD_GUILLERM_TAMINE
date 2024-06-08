package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class ChessBotGameFormController {

    @FXML
    private TextField player1Field_firstname;

    @FXML
    private TextField player1Field_name;

    @FXML
    private void handleSubmitButtonAction() throws IOException {
        String player1_firstname = player1Field_firstname.getText();
        String player1_name = player1Field_name.getText();
        writeCsvFile(player1_firstname, player1_name);
    }

    private void writeCsvFile(String player1_fn, String player1_n) throws IOException {
        String fileName = "BotGame_joueur.csv";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(player1_fn)
                    .append(',')
                    .append(player1_n)
                    .append(',')
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessBotGame.fxml")); // On charge la page permettant de jouer contre l'ordinateur
        Scene firstScene = new Scene(secondSceneParent);

        Stage stage = (Stage) player1Field_firstname.getScene().getWindow();
        stage.setScene(firstScene);
        stage.centerOnScreen();
        stage.show();
    }
}