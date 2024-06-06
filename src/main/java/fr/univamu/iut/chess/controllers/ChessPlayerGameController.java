package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Pion;
import fr.univamu.iut.chess.Piece.Plateau;
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
import javafx.scene.layout.GridPane;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gestion timers
        setupTimers();
        timeLabelWhite.setOnMouseClicked(event -> handleMove());

        // plateau = new Plateau();
        // Afficher les pions sur le plateau
        // afficherPlateau();
        // On affiche les pieces noires :
        Image pionNoir= new Image( ChessApplication.class.getResource("img/piecesNoir/pionNoir.png").toString());
        //ImageView imageview= new ImageView(image);
        for (int ligne=0; ligne<8; ligne++){
            gridPaneJeu.add(new ImageView(pionNoir),ligne,1);
        }

        Image tourNoire = new Image( ChessApplication.class.getResource("img/piecesNoir/tourNoire.png").toString());
        gridPaneJeu.add(new ImageView(tourNoire), 7,0);
        gridPaneJeu.add(new ImageView(tourNoire), 0,0);

        Image cavalierNoir = new Image( ChessApplication.class.getResource("img/piecesNoir/cavalierNoir.png").toString());
        gridPaneJeu.add(new ImageView(cavalierNoir), 6,0);
        gridPaneJeu.add(new ImageView(cavalierNoir), 1,0);


        Image fouNoir = new Image( ChessApplication.class.getResource("img/piecesNoir/fouNoir.png").toString());
        gridPaneJeu.add(new ImageView(fouNoir), 5,0);
        gridPaneJeu.add(new ImageView(fouNoir), 2,0);

        Image reineNoire = new Image( ChessApplication.class.getResource("img/piecesNoir/reineNoire.png").toString());
        gridPaneJeu.add(new ImageView(reineNoire), 4,0);


        Image roiNoire = new Image( ChessApplication.class.getResource("img/piecesNoir/roiNoir.png").toString());
        gridPaneJeu.add(new ImageView(roiNoire), 3,0);


        // On affiche les pieces blanches
        Image pionBlanc= new Image(ChessApplication.class.getResource("img/piecesBlanc/pionBlanc.png").toString());
        //ImageView imageview2= new ImageView(image2);
        for (int ligne=0; ligne<8; ligne++){
            gridPaneJeu.add(new ImageView(pionBlanc),ligne,6);
        }

        Image tourBlanche = new Image( ChessApplication.class.getResource("img/piecesBlanc/tourBlanche.png").toString());
        gridPaneJeu.add(new ImageView(tourBlanche), 7,7);
        gridPaneJeu.add(new ImageView(tourBlanche), 0,7);

        Image cavalierBlanc = new Image( ChessApplication.class.getResource("img/piecesBlanc/cavalierBlanc.png").toString());
        gridPaneJeu.add(new ImageView(cavalierBlanc), 6,7);
        gridPaneJeu.add(new ImageView(cavalierBlanc), 1,7);


        Image fouBlanc = new Image( ChessApplication.class.getResource("img/piecesBlanc/fouBlanc.png").toString());
        gridPaneJeu.add(new ImageView(fouBlanc), 5,7);
        gridPaneJeu.add(new ImageView(fouBlanc), 2,7);

        Image reineBlanche = new Image(ChessApplication.class.getResource("img/piecesBlanc/reineBlanche.png").toString());
        gridPaneJeu.add(new ImageView(reineBlanche), 3,7);


        Image roiBlanc = new Image( ChessApplication.class.getResource("img/piecesBlanc/roiBlanc.png").toString());
        gridPaneJeu.add(new ImageView(roiBlanc), 4,7);





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

    /*
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

     */
}
