package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Pion;
import fr.univamu.iut.chess.Piece.Plateau;
import fr.univamu.iut.chess.Piece.Position;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChessPlayerGameController implements Initializable {

    @FXML
    private Label timeLabelWhite;

    @FXML
    private Label timeLabelBlack;

    private Timeline timerWhite;
    private Timeline timerBlack;
    private int timeWhite = 600; // 10 minutes in seconds
    private int timeBlack = 600; // 10 minutes in seconds
    private boolean isWhiteTurn = true;

    @FXML
    private GridPane gridPaneJeu;

    private Plateau plateau;
    private Piece selectedPiece;
    private Position selectedPosition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTimers();
        timeLabelWhite.setOnMouseClicked(event -> handleMove());

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

    private void setupTimers() {
        timerWhite = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeWhite--;
            updateTimeLabel(timeLabelWhite, timeWhite);
        }));
        timerWhite.setCycleCount(Timeline.INDEFINITE);

        timerBlack = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeBlack--;
            updateTimeLabel(timeLabelBlack, timeBlack);
        }));
        timerBlack.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateTimeLabel(Label label, int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        label.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void handleMove() {
        if (isWhiteTurn) {
            timerWhite.stop();
            timerBlack.play();
        } else {
            timerBlack.stop();
            timerWhite.play();
        }
        isWhiteTurn = !isWhiteTurn;
    }
}
