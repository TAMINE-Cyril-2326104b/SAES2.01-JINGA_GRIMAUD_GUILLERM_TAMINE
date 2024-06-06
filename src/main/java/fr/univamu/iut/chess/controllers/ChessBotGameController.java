package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Plateau;
import fr.univamu.iut.chess.Piece.Position;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChessBotGameController implements Initializable {

    @FXML
    private GridPane gridPaneJeu;

    private Plateau plateau;
    private Piece selectedPiece;
    private Position selectedPosition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.plateau = new Plateau();
        afficherPlateau();
    }

    public void afficherPlateau() {
        gridPaneJeu.getChildren().clear(); // Clear the GridPane before adding pieces

        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                // Ajouter les cases du plateau
                Rectangle rectangle = new Rectangle(40, 40);
                if ((ligne + colonne) % 2 == 0) {
                    rectangle.setFill(Color.BEIGE);
                } else {
                    rectangle.setFill(Color.GREEN);
                }

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rectangle);

                // Ajouter les pièces du plateau
                Piece piece = plateau.getPieces(ligne, colonne);
                if (piece != null) {
                    Image image = new Image(getClass().getResourceAsStream(piece.getImagePath()));
                    ImageView imageView = new ImageView(image);
                    stackPane.getChildren().add(imageView);

                    // Ajouter l'événement de clic sur la pièce
                    int finalLigne = ligne;
                    int finalColonne = colonne;
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        handlePieceClick(piece, new Position(finalLigne, finalColonne));
                    });
                } else {
                    // Ajouter l'événement de clic sur la case vide
                    int finalLigne1 = ligne;
                    int finalColonne1 = colonne;
                    stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        handleEmptySquareClick(new Position(finalLigne1, finalColonne1));
                    });
                }

                gridPaneJeu.add(stackPane, colonne, ligne);
            }
        }
    }

    private void handlePieceClick(Piece piece, Position position) {
        if (selectedPiece == null) {
            selectedPiece = piece;
            selectedPosition = position;
            System.out.println("Piece selected: " + piece.getClass().getSimpleName() + " at position " + position.getRow() + ", " + position.getCol());
        } else {
            // Logique pour déplacer la pièce si une pièce est déjà sélectionnée
            movePiece(position);
        }
    }

    private void handleEmptySquareClick(Position position) {
        if (selectedPiece != null) {
            // Logique pour déplacer la pièce sélectionnée vers une case vide
            movePiece(position);
        }
    }

    private void movePiece(Position newPosition) {
        if (selectedPiece != null && selectedPiece.estDeplacementValide(
                selectedPosition.getRow(), selectedPosition.getCol(),
                newPosition.getRow(), newPosition.getCol())) {

            System.out.println("Moving piece to " + newPosition.getRow() + ", " + newPosition.getCol());
            plateau.deplacerPiece(
                    selectedPosition.getRow(), selectedPosition.getCol(),
                    newPosition.getRow(), newPosition.getCol());

            selectedPiece = null;
            selectedPosition = null;
            afficherPlateau(); // Rafraîchir le plateau après le déplacement
        }
    }
}
