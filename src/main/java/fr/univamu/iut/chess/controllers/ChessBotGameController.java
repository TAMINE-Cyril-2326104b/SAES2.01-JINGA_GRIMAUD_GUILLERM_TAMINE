package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.Couleur;
import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Chessboard;
import fr.univamu.iut.chess.Piece.Position;
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
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ChessBotGameController implements Initializable {
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
    private Label LabelNom;

    @FXML
    private GridPane gridPaneJeu;
    @FXML
    private Label tourMessage;
    @FXML
    private Label mouvImpo;
    @FXML
    private Label echecLabel;

    private Chessboard plateau;
    private Piece selectedPiece;
    private Position selectedPosition;
    private Couleur currentTurn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTimers();
        timeLabelWhite.setOnMouseClicked(event -> handleMove());
        this.plateau = new Chessboard();
        this.currentTurn = Couleur.BLANC;
        afficherPlateau();
        afficherTourMessage();
        String filePath = "BotGame_joueur.csv";
        File file = new File(filePath);
        if (file.exists()) {
            readLastLineFromCSV(file);
        } else {
            LabelNom.setText("File not found");
        }
        String movesFilePath = "chess_moves.csv";
        initializeCSV(movesFilePath);
        String player1 = LabelNom.getText();  // Nom du premier joueur
        String player2 = "BOT";        // Nom du deuxième joueur
        logNewGameToCSV(movesFilePath, player1, player2);

        startGame();
    }

    public void afficherPlateau() {
        gridPaneJeu.getChildren().clear();

        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Rectangle rectangle = new Rectangle(80, 80);
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
            if (piece.getColor().equals(currentTurn)) { // Il faut que cela soit le tour du joueur pour sélectionner une pièce.
                selectedPiece = piece;
                selectedPosition = position;
                System.out.println("Piece selected: " + piece.getClass().getSimpleName() + " at position " + position.getRow() + ", " + position.getCol()); // On affiche la pièce sélectionnée ainsi que sa position.
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

            // Enregistrer le mouvement dans le fichier CSV
            String filePath = "chess_moves.csv"; // Chemin vers le fichier CSV
            String turn = currentTurn == Couleur.BLANC ? "White" : "Black";
            String player = currentTurn == Couleur.BLANC ? LabelNom.getText() : "BOT";
            String piece = selectedPiece.getClass().getSimpleName();
            String from = selectedPosition.getRow() + "," + selectedPosition.getCol();
            String to = newPosition.getRow() + "," + newPosition.getCol();

            logMoveToCSV(filePath, turn, player, piece, from, to);

            System.out.println("Moving piece to " + newPosition.getRow() + ", " + newPosition.getCol());
            plateau.movePiece(
                    selectedPosition.getRow(), selectedPosition.getCol(),
                    newPosition.getRow(), newPosition.getCol(), plateau.getPieces());


            if (isKingInCheck(currentTurn)) {
                if (isCheckmate(currentTurn)) {
                    endGame(currentTurn == Couleur.BLANC ? Couleur.NOIR : Couleur.BLANC);
                } else {
                    echecLabel.setText((currentTurn == Couleur.BLANC ? "Les blancs" : "Les noirs") + " echec !");
                    if ( isKingInCheck(Couleur.NOIR) ||  isKingInCheck(Couleur.BLANC)){
                        plateau.movePiece(
                                newPosition.getRow(), newPosition.getCol(),
                                selectedPosition.getRow(), selectedPosition.getCol(),
                                plateau.getPieces());
                        mouvImpo.setText((currentTurn == Couleur.BLANC ? "Les blancs" : "Les noirs") + " deplacement impossible !");
                        switchTurn();
                    }
                }
            }else {
                echecLabel.setText("");
            }
            if(currentTurn.equals(Couleur.BLANC) && isKingInCheck(Couleur.NOIR) || currentTurn.equals(Couleur.NOIR) && isKingInCheck(Couleur.BLANC)){
                echecLabel.setText((currentTurn == Couleur.NOIR ? "Les blancs" : "Les noirs") + " echec !");

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
        currentTurn = (currentTurn == Couleur.BLANC) ? Couleur.NOIR : Couleur.BLANC;
        afficherTourMessage();
        if (currentTurn == Couleur.NOIR) {
            jouerTourBot();
        }
    }

    private void afficherTourMessage() {
        tourMessage.setText((currentTurn == Couleur.BLANC ? "Les blancs" : "Les noirs") + " jouent !");
    }
    private void jouerTourBot() {
        List<Move> validMoves = obtenirDeplacementsValides(Couleur.NOIR);
        if (!validMoves.isEmpty()) {
            Random rand = new Random();
            Move move = validMoves.get(rand.nextInt(validMoves.size()));
            selectedPiece = plateau.getPieces(move.from.getRow(), move.from.getCol());
            selectedPosition = new Position(move.from.getRow(),move.from.getCol());
            handleEmptySquareClick(new Position(move.to.getRow(),move.to.getCol()));
            movePiece(move.to);
        }
    }

    private List<Move> obtenirDeplacementsValides(Couleur couleur) {
        List<Move> validMoves = new ArrayList<>();
        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Piece piece = plateau.getPieces(ligne, colonne);
                if (piece != null && piece.getColor().equals(couleur)) {
                    for (int newLigne = 0; newLigne < 8; newLigne++) {
                        for (int newColonne = 0; newColonne < 8; newColonne++) {
                            if (piece.isMoveLegal(ligne, colonne, newLigne, newColonne, plateau.getPieces())) {
                                validMoves.add(new Move(new Position(ligne, colonne), new Position(newLigne, newColonne)));
                            }
                        }
                    }
                }
            }
        }
        return validMoves;
    }
    private class Move {
        Position from;
        Position to;

        Move(Position from, Position to) {
            this.from = from;
            this.to = to;
        }
    }
    private void setupTimers() {
        timerWhite = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeWhite--;
            updateTimeLabel(timeLabelWhite, timeWhite);
            if (timeWhite <= 0) {
                endGame(Couleur.NOIR);
            }
        }));
        timerWhite.setCycleCount(Timeline.INDEFINITE);

        updateTimeLabel(timeLabelWhite, timeWhite);
    }

    private void updateTimeLabel(Label label, int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        label.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void handleMove() {
        if (isWhiteTurn) {
            timerWhite.stop();
        } else {
            timerWhite.play();
        }
        isWhiteTurn = !isWhiteTurn;
    }
    private void startGame() {
        timerWhite.play();
    }

    public void endGame(Couleur winnerColor) {
        timerWhite.stop();

        // Enregistrer le gagnant dans le fichier CSV
        String filePath = "chess_moves.csv";
        String winner = winnerColor == Couleur.BLANC ? LabelNom.getText() : "BOT";
        logGameResultToCSV(filePath, winner);

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin de la partie");
            alert.setHeaderText("ECHEC ET MAT !");
            alert.setContentText("Les " + winnerColor + " gagnent la partie !");
            alert.showAndWait();
            try {
                TimeUnit.SECONDS.sleep(3); // Mettre en pause pendant 3 secondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Fermer l'application
            Platform.exit();
        });
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

    private void readLastLineFromCSV(File file) {
        String lastLine = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assuming CSV format: lastName, firstName
        if (!lastLine.isEmpty()) {
            String[] parts = lastLine.split(",");
            if (parts.length >= 2) {
                String lastName = parts[0].trim();
                String firstName = parts[1].trim();
                LabelNom.setText(lastName + " " + firstName);
            } else {
                LabelNom.setText("Invalid CSV format");
            }
        } else {
            LabelNom.setText("CSV is empty");
        }
    }

    public void logMoveToCSV(String filePath, String turn, String player, String piece, String from, String to) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(turn + "," + player + "," + piece + "," + from + "," + to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logNewGameToCSV(String filePath, String player1, String player2) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Nouvelle Partie: " + player1 + " vs " + player2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void logGameResultToCSV(String filePath, String winner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Le gagnant est: " + winner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initializeCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Turn,Player,Piece,From,To");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
