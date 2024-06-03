package com.example.chess.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChessController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}