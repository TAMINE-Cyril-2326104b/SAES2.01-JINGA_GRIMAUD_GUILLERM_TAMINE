package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Plateau;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChessBotGameController implements Initializable {
    @FXML
    private GridPane gridPaneJeu;

    private Plateau plateau;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        plateau = new Plateau();
        afficherPlateau();
    }

    private void afficherPlateau() {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Piece piece = plateau.getPieces(ligne, colonne);
                if (piece != null) {
                    ImageView imageView = new ImageView(piece.getImagePath());
                    gridPaneJeu.add(imageView, colonne, ligne);
                }
            }
        }
    }
}
