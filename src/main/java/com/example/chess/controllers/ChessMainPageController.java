package com.example.chess.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class ChessMainPageController {

    @FXML
    private ImageView plateauImageView;
    @FXML
    private GridPane gridPane;

    // Création du fonction permettant le changement de page, démarrant ainsi la partie.
    public void handleChangeSceneBot(ActionEvent event) throws IOException{
        Parent secondSceneParent = FXMLLoader.load(getClass().getResource("/fxml/ChessBotGame.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.show();
    }

    public void handleChangeScenePlayer(ActionEvent event) throws IOException{
        Parent secondSceneParent = FXMLLoader.load(getClass().getResource("/fxml/ChessPlayerGame.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = new Stage();
        stage.setScene(secondScene);
        stage.show();
    }





}
