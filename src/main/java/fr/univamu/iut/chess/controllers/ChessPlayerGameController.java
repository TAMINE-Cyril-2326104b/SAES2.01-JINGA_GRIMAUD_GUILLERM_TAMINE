package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChessPlayerGameController {

    @FXML
    public Label timeLabelWhite;

    @FXML
    public Label timeLabelBlack;

    private Timeline timerWhite;
    private Timeline timerBlack;
    private int timeWhite = 600; // 10 minutes in seconds
    private int timeBlack = 600; // 10 minutes in seconds
    private boolean isWhiteTurn = true;

    @FXML
    public GridPane gridPaneJeu;
    @FXML
    private Label NomChoisiLabel;
    @FXML
    private Label AdvLabel;

    @FXML
    public Label tourMessage;
    @FXML
    public Label echecLabel;
    @FXML
    public Label mouvImpo;

    public Chessboard plateau;
    private Piece selectedPiece;
    private Position selectedPosition;
    public Couleur currentTurn;


    public void initialize() { //initialise le nom des joueurs, le plateau etc...
        setupTimers();
        timeLabelWhite.setOnMouseClicked(event -> handleMove());
        this.plateau = new Chessboard();
        this.currentTurn = Couleur.BLANC;
        afficherPlateau();
        afficherTourMessage();
        String filePath = "PlayerGame_joueurs.csv";
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
                Rectangle rectangle = new Rectangle(80, 80); // On initie notre grille avec des rectangles 80x80
                if ((ligne + colonne) % 2 == 0) {
                    rectangle.setFill(Color.rgb(235,236,208));
                } else {
                    rectangle.setFill(Color.rgb(119,149,86));
                }

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rectangle); // On ajoute ces rectangles à notre gridPane

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

    public void handlePieceClick(Piece piece, Position position) { //permet de choisir la piece a déplacer ou de choisir la case vers laquelle la déplacer
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

    public void handleEmptySquareClick(Position position) { // si on choisi une case vide et qu"on a aupréalablement choisi une piece on la déplace
        if (selectedPiece != null) {
            movePiece(position);
        }
    }

    public void movePiece(Position newPosition) {
        if (selectedPiece != null && selectedPiece.isMoveLegal(
                selectedPosition.getRow(), selectedPosition.getCol(),
                newPosition.getRow(), newPosition.getCol(), plateau.getPieces())) {

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
                        mouvImpo.setText((currentTurn == Couleur.BLANC ? "Les blancs" : "Les noirs") + " déplacement impossible !");
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

    public void switchTurn() {
        currentTurn = (currentTurn == Couleur.BLANC) ? Couleur.NOIR : Couleur.BLANC;
        afficherTourMessage();
        handleMove();
    }

    public void afficherTourMessage() {
        // Vérifier si le tourMessage n'est pas null avant de le mettre à jour
        if (tourMessage != null) {
            tourMessage.setText((currentTurn == Couleur.BLANC ? "Les blancs" : "Les noirs") + " jouent !");
        }
    }



    public void setupTimers() {
        timerWhite = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeWhite--;
            updateTimeLabel(timeLabelWhite, timeWhite);
            if (timeWhite <= 0) {
                endGame(Couleur.NOIR);
            }
        }));
        timerWhite.setCycleCount(Timeline.INDEFINITE);

        timerBlack = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeBlack--;
            updateTimeLabel(timeLabelBlack, timeBlack);
            if (timeBlack <= 0) {
                endGame(Couleur.BLANC);
            }
        }));
        timerBlack.setCycleCount(Timeline.INDEFINITE);
        updateTimeLabel(timeLabelWhite, timeWhite);
        updateTimeLabel(timeLabelBlack, timeBlack);
    }

    public void updateTimeLabel(Label label, int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        label.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public void handleMove() {
        if (isWhiteTurn) {
            timerWhite.stop();
            timerBlack.play();
        } else {
            timerBlack.stop();
            timerWhite.play();
        }
        isWhiteTurn = !isWhiteTurn;
    }
    public void startGame() {
        timerWhite.play();
    }


    public void endGame(Couleur winnerColor) {
        timerWhite.stop();
        timerBlack.stop();

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
    public boolean isKingInCheck(Couleur kingColor) {
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
    public boolean isCheckmate(Couleur kingColor) {
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
    public boolean canKingMove(Position from, Position to) {
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
    public void readLastTwoLinesFromCSV(File file) { // prend les deux dernières ligne du csv afin de prendre les deux derniers joueurs inscrit
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

        //  CSV format: lastName, firstName
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

}