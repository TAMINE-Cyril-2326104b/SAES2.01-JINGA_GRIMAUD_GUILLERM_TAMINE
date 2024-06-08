package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.Piece.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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

    @FXML
    private Label tourMessage;
    @FXML
    private Label echecLabel;
    @FXML
    private Label mouvImpo;

    private Chessboard chessboard;
    private Piece selectedPiece;
    private Position selectedPosition;
    private Couleur currentTurn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTimers();
        timeLabelWhite.setOnMouseClicked(event -> handleMove());
        this.chessboard = new Chessboard();
        this.currentTurn = Couleur.WHITE;
        displayChessboard();
        afficherTourMessage();
        startGame();
    }

    public void displayChessboard() {
        gridPaneJeu.getChildren().clear();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Rectangle rectangle = new Rectangle(40, 40); // On génère un rectangle de 40x40
                if ((row + column) % 2 == 0) {
                    rectangle.setFill(Color.rgb(235,236,208)); // On colorie ce rectnagle en
                } else {
                    rectangle.setFill(Color.rgb(119,149,86));
                }

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rectangle);

                Piece piece = chessboard.getPieces(row, column);
                if (piece != null) {
                    Image image = new Image(getClass().getResourceAsStream(piece.getImagePath()));
                    ImageView imageView = new ImageView(image);
                    stackPane.getChildren().add(imageView);

                    int finalLigne = row;
                    int finalColonne = column;
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handlePieceClick(piece, new Position(finalLigne, finalColonne)));
                } else {
                    int finalLigne1 = row;
                    int finalColonne1 = column;
                    stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleEmptySquareClick(new Position(finalLigne1, finalColonne1)));
                }

                gridPaneJeu.add(stackPane, column, row);
            }
        }
    }

    private void handlePieceClick(Piece piece, Position position) {
        if (selectedPiece == null) {
            if (piece.getColor().equals(currentTurn)) {
                selectedPiece = piece;
                selectedPosition = position;
                System.out.println("Piece selected: " + piece.getClass().getSimpleName() + " at position " + position.getRow() + ", " + position.getCol());
            }
        } else {
            movePiece(position);
        }
    }

    private void handleEmptySquareClick(Position position) {
        if (selectedPiece != null) {
            movePiece(position);
        }
    }

    private void movePiece(Position newPosition) {
        if (selectedPiece != null && selectedPiece.isMoveLegal(
                selectedPosition.getRow(), selectedPosition.getCol(),
                newPosition.getRow(), newPosition.getCol(), chessboard.getPieces())) {

            System.out.println("Moving piece to " + newPosition.getRow() + ", " + newPosition.getCol());
            chessboard.movePiece(
                    selectedPosition.getRow(), selectedPosition.getCol(),
                    newPosition.getRow(), newPosition.getCol(), chessboard.getPieces());


            if (isKingInCheck(currentTurn)) {
                if (isCheckmate(currentTurn)) {
                    endGame(currentTurn == Couleur.WHITE ? Couleur.BLACK : Couleur.WHITE);
                } else {
                    echecLabel.setText((currentTurn == Couleur.WHITE ? "Les blancs" : "Les noirs") + " echec !");
                    if ( isKingInCheck(Couleur.BLACK) ||  isKingInCheck(Couleur.WHITE)){
                        chessboard.movePiece(
                                newPosition.getRow(), newPosition.getCol(),
                                selectedPosition.getRow(), selectedPosition.getCol(),
                                chessboard.getPieces());
                        mouvImpo.setText((currentTurn == Couleur.WHITE ? "Les blancs" : "Les noirs") + " deplacement impossible !");
                        switchTurn();
                    }
                }
            }else {
                echecLabel.setText("");
            }
            if(currentTurn.equals(Couleur.WHITE) && isKingInCheck(Couleur.BLACK) || currentTurn.equals(Couleur.BLACK) && isKingInCheck(Couleur.WHITE)){
                echecLabel.setText((currentTurn == Couleur.BLACK ? "Les blancs" : "Les noirs") + " echec !");

            }else {
                echecLabel.setText("");
            }
            selectedPiece = null;
            selectedPosition = null;
            switchTurn();
            displayChessboard();

        }
        else {
            selectedPiece = null;
            selectedPosition = null;
        }
    }

    private void switchTurn() {
        currentTurn = (currentTurn == Couleur.WHITE) ? Couleur.BLACK : Couleur.WHITE;
        afficherTourMessage();
        handleMove();
    }

    private void afficherTourMessage() {
        tourMessage.setText((currentTurn == Couleur.WHITE ? "Les blancs" : "Les noirs") + " jouent !");
    }


    private void setupTimers() {
        timerWhite = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeWhite--;
            updateTimeLabel(timeLabelWhite, timeWhite);
            if (timeWhite <= 0) {
                endGame(Couleur.BLACK);
            }
        }));
        timerWhite.setCycleCount(Timeline.INDEFINITE);

        timerBlack = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeBlack--;
            updateTimeLabel(timeLabelBlack, timeBlack);
            if (timeBlack <= 0) {
                endGame(Couleur.WHITE);
            }
        }));
        timerBlack.setCycleCount(Timeline.INDEFINITE);
        updateTimeLabel(timeLabelWhite, timeWhite);
        updateTimeLabel(timeLabelBlack, timeBlack);
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
    private void startGame() {
        timerWhite.play();
    }
    private void endGame(Couleur winnerColor) {
        timerWhite.stop();
        timerBlack.stop();

        String winner = (winnerColor == Couleur.WHITE) ? "Les blancs" : "Les noirs";
        System.out.println(winner+" ont gagnés");
        Platform.exit(); // fermer l'application
    }
    private boolean isKingInCheck(Couleur kingColor) {
        Position kingPosition = chessboard.findKingPosition(kingColor);
        for (Piece[] row : chessboard.getPieces()) {
            for (Piece piece : row) {
                if (piece != null && piece.getColor() != kingColor) {
                    if (piece.isMoveLegal(piece.getPosition().getRow(), piece.getPosition().getCol(), kingPosition.getRow(), kingPosition.getCol(), chessboard.getPieces())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean isCheckmate(Couleur kingColor) {
        if (!isKingInCheck(kingColor)) {
            return false;
        }

        Position kingPosition = chessboard.findKingPosition(kingColor);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position newPosition = new Position(row, col);
                if (chessboard.getPieces()[row][col] == null || chessboard.getPieces()[row][col].getColor() != kingColor) {
                    if (canKingMove(kingPosition, newPosition)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean canKingMove(Position from, Position to) {
        Piece king = chessboard.getPieces()[from.getRow()][from.getCol()];
        if (king.isMoveLegal(from.getRow(), from.getCol(), to.getRow(), to.getCol(), chessboard.getPieces())) {
            Piece temp = chessboard.getPieces()[to.getRow()][to.getCol()];
            chessboard.getPieces()[to.getRow()][to.getCol()] = king;
            chessboard.getPieces()[from.getRow()][from.getCol()] = null;
            boolean isInCheck = isKingInCheck(king.getColor());
            chessboard.getPieces()[from.getRow()][from.getCol()] = king;
            chessboard.getPieces()[to.getRow()][to.getCol()] = temp;
            return !isInCheck;
        }
        return false;
    }

}
