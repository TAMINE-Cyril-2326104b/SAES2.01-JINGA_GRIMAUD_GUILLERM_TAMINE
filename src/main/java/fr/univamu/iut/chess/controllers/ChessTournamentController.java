package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

public class ChessTournamentController implements Initializable {

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
    private Label NomChoisiLabel;
    @FXML
    private Label AdvLabel;

    @FXML
    private Label tourMessage;
    @FXML
    private Label echecLabel;
    @FXML
    private Label mouvImpo;

    private Chessboard plateau;
    private Piece selectedPiece;
    private Position selectedPosition;
    private Couleur currentTurn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTimers();
        timeLabelWhite.setOnMouseClicked(event -> handleMove());
        this.plateau = new Chessboard();
        this.currentTurn = Couleur.WHITE;
        afficherPlateau();
        afficherTourMessage();
        String filePath = "TournamentGame.csv";
        File file = new File(filePath);
        if (file.exists()) {
            readLastTwoLinesFromCSV(file);
        } else {
            AdvLabel.setText("File not found");
            NomChoisiLabel.setText("File not found");
        }
        startGame();
    }


    public void afficherPlateau() {
        gridPaneJeu.getChildren().clear();

        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Rectangle rectangle = new Rectangle(40, 40);
                if ((ligne + colonne) % 2 == 0) {
                    rectangle.setFill(Color.rgb(235,236,208));
                } else {
                    rectangle.setFill(Color.rgb(119,149,86));
                }

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rectangle);

                Piece piece = plateau.getPieces(ligne, colonne);
                if (piece != null) {
                    Image image = new Image(getClass().getResourceAsStream(piece.getImagePath()));
                    ImageView imageView = new ImageView(image);
                    stackPane.getChildren().add(imageView);

                    int finalLigne = ligne;
                    int finalColonne = colonne;
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handlePieceClick(piece, new Position(finalLigne, finalColonne)));
                } else {
                    int finalLigne1 = ligne;
                    int finalColonne1 = colonne;
                    stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleEmptySquareClick(new Position(finalLigne1, finalColonne1)));
                }

                gridPaneJeu.add(stackPane, colonne, ligne);
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
                newPosition.getRow(), newPosition.getCol(), plateau.getPieces())) {

            System.out.println("Moving piece to " + newPosition.getRow() + ", " + newPosition.getCol());
            plateau.movePiece(
                    selectedPosition.getRow(), selectedPosition.getCol(),
                    newPosition.getRow(), newPosition.getCol(), plateau.getPieces());


            if (isKingInCheck(currentTurn)) {
                if (isCheckmate(currentTurn)) {
                    endGame(currentTurn == Couleur.WHITE ? Couleur.BLACK : Couleur.WHITE);
                } else {
                    echecLabel.setText((currentTurn == Couleur.WHITE ? "Les blancs" : "Les noirs") + " echec !");
                    if ( isKingInCheck(Couleur.BLACK) ||  isKingInCheck(Couleur.WHITE)){
                        plateau.movePiece(
                                newPosition.getRow(), newPosition.getCol(),
                                selectedPosition.getRow(), selectedPosition.getCol(),
                                plateau.getPieces());
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
            afficherPlateau();

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
        tourMessage.setText((currentTurn == Couleur.WHITE ? NomChoisiLabel : AdvLabel) + " jouent !");
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

        String winner = (winnerColor == Couleur.WHITE) ? "Les blancs "+NomChoisiLabel : "Les noirs "+AdvLabel;
        System.out.println(winner+" ont gagnés");
    }
    private boolean isKingInCheck(Couleur kingColor) {
        Position kingPosition = plateau.findKingPosition(kingColor);
        for (Piece[] row : plateau.getPieces()) {
            for (Piece piece : row) {
                if (piece != null && piece.getColor() != kingColor) {
                    if (piece.isMoveLegal(piece.getPosition().getRow(), piece.getPosition().getCol(), kingPosition.getRow(), kingPosition.getCol(), plateau.getPieces())) {
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

        Position kingPosition = plateau.findKingPosition(kingColor);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position newPosition = new Position(row, col);
                if (plateau.getPieces()[row][col] == null || plateau.getPieces()[row][col].getColor() != kingColor) {
                    if (canKingMove(kingPosition, newPosition)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean canKingMove(Position from, Position to) {
        Piece king = plateau.getPieces()[from.getRow()][from.getCol()];
        if (king.isMoveLegal(from.getRow(), from.getCol(), to.getRow(), to.getCol(), plateau.getPieces())) {
            Piece temp = plateau.getPieces()[to.getRow()][to.getCol()];
            plateau.getPieces()[to.getRow()][to.getCol()] = king;
            plateau.getPieces()[from.getRow()][from.getCol()] = null;
            boolean isInCheck = isKingInCheck(king.getColor());
            plateau.getPieces()[from.getRow()][from.getCol()] = king;
            plateau.getPieces()[to.getRow()][to.getCol()] = temp;
            return !isInCheck;
        }
        return false;
    }

    public void handleNewGameButtonAction(ActionEvent event) throws IOException{
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessMainPage.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show();
    }
    private void readLastTwoLinesFromCSV(File file) {
        String secondLastLine = "";
        String lastLine = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    secondLastLine = lastLine;
                    lastLine = line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assuming CSV format: lastName, firstName
        if (!lastLine.isEmpty() && !secondLastLine.isEmpty()) {
            String[] lastParts = lastLine.split(",");
            String[] secondLastParts = secondLastLine.split(",");
            if (lastParts.length >= 2 && secondLastParts.length >= 2) {
                String lastNameLast = lastParts[0].trim();
                String firstNameLast = lastParts[1].trim();
                String lastNameSecondLast = secondLastParts[0].trim();
                String firstNameSecondLast = secondLastParts[1].trim();
                if (!lastNameLast.isEmpty() && !firstNameLast.isEmpty() && !lastNameSecondLast.isEmpty() && !firstNameSecondLast.isEmpty()) {
                    AdvLabel.setText(lastNameLast + " " + firstNameLast);
                    NomChoisiLabel.setText( lastNameSecondLast + " " + firstNameSecondLast);
                } else {
                    AdvLabel.setText("Invalid CSV format: empty values");
                    NomChoisiLabel.setText("Invalid CSV format: empty values");
                }
            } else {
                AdvLabel.setText("Invalid CSV format: not enough values");
                NomChoisiLabel.setText("Invalid CSV format: not enough values");
            }
        } else {
            AdvLabel.setText("CSV is empty or does not contain enough lines");
            NomChoisiLabel.setText("CSV is empty or does not contain enough lines");
        }
    }
    public static void deleteLineContainingPhrase(String phrase) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("TournamentGame.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(phrase)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("TournamentGame.csv"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
