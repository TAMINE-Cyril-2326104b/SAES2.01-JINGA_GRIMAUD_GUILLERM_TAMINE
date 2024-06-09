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

import java.io.*;
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
        String movesFilePath = "chess_moves.csv";
        initializeCSV(movesFilePath);
        String player1 = NomChoisiLabel.getText();  // Nom du premier joueur
        String player2 = AdvLabel.getText();        // Nom du deuxième joueur
        logNewGameToCSV(movesFilePath, player1, player2);

        startGame();
    }

    /**
     * Fonction permettant d'afficher l'échiquier.
     */
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

    /**
     * Détecte le clic sur une pièce du plateau.
     * @param piece
     * @param position
     */
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

    /**
     * Détecte le clic sur une case vide, permettant le déplacement d'une pièce
     * @param position
     */
    public void handleEmptySquareClick(Position position) { // si on choisi une case vide et qu"on a aupréalablement choisi une piece on la déplace
        if (selectedPiece != null) {
            movePiece(position);
        }
    }

    /**
     * Permet le déplacement d'une pièce selon les fonctions mises en place dans leur classe respective.
     * @param newPosition
     */
    public void movePiece(Position newPosition) {
        if (selectedPiece != null && selectedPiece.isMoveLegal(
                selectedPosition.getRow(), selectedPosition.getCol(),
                newPosition.getRow(), newPosition.getCol(), plateau.getPieces())) {
            // Enregistrer le mouvement dans le fichier CSV
            String filePath = "chess_moves.csv"; // Chemin vers le fichier CSV
            String turn = currentTurn == Couleur.BLANC ? "White" : "Black";
            String player = currentTurn == Couleur.BLANC ? NomChoisiLabel.getText() : AdvLabel.getText();
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
                    echecLabel.setText((currentTurn == Couleur.BLANC ? "Les blancs " : "Les noirs") + " sont en échec !");
                    if ( isKingInCheck(Couleur.NOIR) ||  isKingInCheck(Couleur.BLANC)){
                        plateau.movePiece(
                                newPosition.getRow(), newPosition.getCol(),
                                selectedPosition.getRow(), selectedPosition.getCol(),
                                plateau.getPieces());
                        mouvImpo.setText((currentTurn == Couleur.BLANC ? "Les blancs : " : "Les noirs :") + " Déplacement impossible !");
                        switchTurn();
                    }
                }
            }else {
                echecLabel.setText("");
            }
            if(currentTurn.equals(Couleur.BLANC) && isKingInCheck(Couleur.NOIR) || currentTurn.equals(Couleur.NOIR) && isKingInCheck(Couleur.BLANC)){
                echecLabel.setText((currentTurn == Couleur.NOIR ? "Les blancs" : "Les noirs") + " sont en échec !");

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

    /**
     * Change le tour du joueur.
     */
    public void switchTurn() {
        currentTurn = (currentTurn == Couleur.BLANC) ? Couleur.NOIR : Couleur.BLANC;
        afficherTourMessage();
        handleMove();
    }

    /**
     * Affiche un message indiquant quel joueur joue.
     */
    public void afficherTourMessage() {
        // Vérifier si le tourMessage n'est pas null avant de le mettre à jour
        if (tourMessage != null) {
            tourMessage.setText((currentTurn == Couleur.BLANC ? "Les blancs" : "Les noirs") + " jouent !");
        }
    }


    /**
     * Mise en place du timer, avec décrémentation.
     */
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

    /**
     * Mise à jour du temps
     * @param label
     * @param time
     */
    public void updateTimeLabel(Label label, int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        label.setText(String.format("%02d:%02d", minutes, seconds));
    }

    /**
     * Gestion des mouvements
     */
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
    /**
     * Démarrage de la partie, avec le début du timer des blancs.
     */
    public void startGame() {
        timerWhite.play();
    }

    /**
     * Fin de la partie, avec la fin du timer, l'apparition d'une alerte, ainsi que l'enregistrement des coups et la fermeture du programme.
     * @param winnerColor
     */
    public void endGame(Couleur winnerColor) {
        timerWhite.stop();
        timerBlack.stop();

        // Enregistrer le gagnant dans le fichier CSV
        String filePath = "chess_moves.csv";
        String winner = winnerColor == Couleur.BLANC ? NomChoisiLabel.getText() : AdvLabel.getText();
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

    /**
     * Vérifie si le roi est en position d'échec.
     * @param kingColor
     * @return
     */
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

    /**
     * Vérifie si le joueur est en position d'échec et mat.
     * @param kingColor
     * @return
     */
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

    /**
     * Vérifie si le roi est en capacité de se déplacer dans une direction, en cas d'échec.
     * @param from
     * @param to
     * @return
     */
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

    /**
     * Permet de relancer une nouvelle partie.
     * @param event
     * @throws IOException
     */
    public void handleNewGameButtonAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessMainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(parent, 1520, 800)); // Taille réduite de la fenêtre
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Prend les deux derniers noms rentrés dans le fichier csv
     * @param file
     */
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
    public void initializeCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Turn,Player,Piece,From,To");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Introduit les mouvements dans le fichier csv
     * @param filePath
     * @param turn
     * @param player
     * @param piece
     * @param from
     * @param to
     */
    public void logMoveToCSV(String filePath, String turn, String player, String piece, String from, String to) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(turn + "," + player + "," + piece + "," + from + "," + to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Annonce chaque nouvelle parties dans le fichier csv afin de différencier les parties facilement
     * @param filePath
     * @param player1
     * @param player2
     */
    public void logNewGameToCSV(String filePath, String player1, String player2) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Nouvelle Partie: " + player1 + " vs " + player2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Met les résultat de la parties dans le csv a la fin de la partie
     * @param filePath
     * @param winner
     */
    public void logGameResultToCSV(String filePath, String winner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println("Le gagnant est: " + winner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}