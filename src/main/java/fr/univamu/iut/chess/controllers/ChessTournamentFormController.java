package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChessTournamentFormController {

    @FXML
    private TextField player1Field_firstname;

    @FXML
    private TextField player1Field_name;

    @FXML
    private void handleSubmitButtonAction() throws IOException {
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessTournament.fxml"));
        Scene firstScene = new Scene(secondSceneParent);

        Stage stage = (Stage) player1Field_firstname.getScene().getWindow();
        stage.setScene(firstScene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    private void handleAddPlayer() throws IOException {
        String firstName = player1Field_firstname.getText().trim();
        String lastName = player1Field_name.getText().trim();
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            writeCsvFile(firstName,lastName);
            player1Field_firstname.clear();
            player1Field_name.clear();
        }
    }

    private void writeCsvFile(String player1_fn, String player1_n) throws IOException {
        String fileName = "TournamentGame.csv";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(player1_fn)
                    .append(',')
                    .append(player1_n)
                    .append(',')
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}