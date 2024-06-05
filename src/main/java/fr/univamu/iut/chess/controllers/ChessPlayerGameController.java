/*package com.example.chess.controllers;

import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Pion;
import fr.univamu.iut.chess.Piece.Plateau;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChessPlayerGameController implements Initializable {
    @FXML
    private GridPane gridPaneJeu;

    private Plateau plateau;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        plateau = new Plateau();

        // Placer une ligne de pions blancs sur la deuxième rangée (ligne 1)
        for (int colonne = 0; colonne < 8; colonne++) {
            Pion pionBlanc = new Pion("Blanc", "https://camo.githubusercontent.com/06bfc1d20e1d241b0f2d991b16252e6f9f907a9320767ea7018d088eba0a6b03/68747470733a2f2f696d616765732e6368657373636f6d66696c65732e636f6d2f63686573732d7468656d65732f7069656365732f6e656f2f3235362f77702e706e67");
            plateau.placerPiece(pionBlanc, 1, colonne); // La deuxième rangée a l'index 1
        }

        afficherPlateau();
    }

    public void afficherPlateau() {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Piece piece = plateau.getPieces()[ligne][colonne];
                if (piece != null) {
                    ImageView imageView = getImageViewFromPosition(ligne, colonne);
                    if (imageView != null) {
                        imageView.setImage(piece.getImageView().getImage());
                    }
                }
            }
        }
    }

    public ImageView getImageViewFromPosition(int ligne, int colonne) {
        for (var node : gridPaneJeu.getChildren()) {
            if (node instanceof ImageView && GridPane.getRowIndex(node) == ligne && GridPane.getColumnIndex(node) == colonne) {
                return (ImageView) node;
            }
        }
        return null;
    }
}*/
