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
        // Afficher les pions sur le plateau
        // afficherPlateau();
        Image image= new Image("/fr/univ-amu/iut/chess/img/piecesBlanc/pionBlanc.png");
        ImageView imageview= new ImageView(image);
        gridPaneJeu.add(imageview,1,1);

        Image image2= new Image("/fr/univ-amu/iut/chess/img/piecesBlanc/pionBlanc.png");
        ImageView imageview2= new ImageView(image2);
        gridPaneJeu.add(imageview2,8,8);
    }

    private void afficherPlateau() {
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Piece piece = plateau.getPieces(ligne, colonne);
                if (piece != null) {
                    ImageView imageView = new ImageView(piece.getImageView());
                    gridPaneJeu.add(imageView, colonne, ligne);
                }
            }
        }
    }
}
