package com.example.chess.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class ChessMainPageController {

// Création du fonction permettant le changement de page, démarrant ainsi la partie.
    public void handleChangeScene(ActionEvent event) throws IOException{
        Parent secondSceneParent = FXMLLoader.load(getClass().getResource("/fxml/ChessBotGame.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.show();
    }





}
