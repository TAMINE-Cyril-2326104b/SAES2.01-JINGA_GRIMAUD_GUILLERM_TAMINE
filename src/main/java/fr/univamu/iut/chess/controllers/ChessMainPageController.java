package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Chessboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Contrôleur pour la page principale du jeu d'échecs.
 * Gère l'affichage du plateau d'échecs et la navigation entre les différentes vues.
 */
public class ChessMainPageController implements Initializable {

    @FXML
    private GridPane gridPaneJeu;

    private Chessboard chessboard;
    @FXML
    private VBox VBoxDroite;


    /**
     * Initialise le contrôleur.
     *
     * @param url            L'emplacement utilisé pour résoudre les chemins relatifs des objets racine.
     * @param resourceBundle Les ressources locales utilisées par l'application.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearCSVFile();
        this.chessboard = new Chessboard();
        displayChessboard();
    }

    /**
     * Change la scène pour afficher la liste des joueurs.
     *
     */
    public void handleChangeSceneJoueurList() {
        List<String> players = readPlayersFromFile("PlayerGame_joueurs.csv");
        List<String> playersVSBot = readPlayersFromFile("BotGame_joueur.csv");
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(players);
        ListView<String> listViewBot = new ListView<>();
        listViewBot.getItems().addAll(playersVSBot);
        VBoxDroite.getChildren().clear();
        VBoxDroite.getChildren().addAll(listView, listViewBot);
    }

    /**
     * Lit les noms des joueurs à partir d'un fichier CSV.
     *
     * @param filename Le nom du fichier CSV.
     * @return Une liste des noms des joueurs.
     */
    private List<String> readPlayersFromFile(String filename) {
        List<String> players = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("Nom")) { // Skip the header
                    players.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    /**
     * Efface le contenu du fichier CSV.
     */
    private void clearCSVFile() {   //supprime tout le contenu du fichier contenant les joueurs du tournoi
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("TournamentGame.csv"))) {
            // Writing an empty string to clear the file
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche le plateau d'échecs.
     */
    public void displayChessboard() {
        gridPaneJeu.getChildren().clear();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Rectangle rectangle = new Rectangle(80, 80);
                if ((row + column) % 2 == 0) {
                    rectangle.setFill(Color.rgb(235, 236, 208));
                } else {
                    rectangle.setFill(Color.rgb(119, 149, 86));
                }

                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(rectangle);

                Piece piece = chessboard.getPieces(row, column);
                if (piece != null) {
                    Image image = new Image(getClass().getResourceAsStream(piece.getImagePath()));
                    ImageView imageView = new ImageView(image);
                    stackPane.getChildren().add(imageView);

                } else {
                }

                gridPaneJeu.add(stackPane, column, row);
            }
        }
    }

    /**
     * Change de scène pour affronter l'ordinateur.
     *
     * @param event L'événement de clic sur le bouton.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de la scène.
     */
    public void handleChangeSceneBot(ActionEvent event) throws IOException { // nous envoie vers le mode de jeu contre le bot
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessBotGameForm.fxml")); // On charge la page de formulaire FXML
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show(); // On fait apparaître le stage
    }

    /**
     * Change de scène pour revenir à la page principale.
     *
     * @param event L'événement de clic sur le bouton.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de la scène.
     */
    public void handleNewGameButtonAction(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessMainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(parent, 1520, 800)); // Taille réduite de la fenêtre
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Change de scène pour affronter un autre joueur.
     *
     * @param event L'événement de clic sur le bouton.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de la scène.
     */
    public void handleChangeScenePlayer(ActionEvent event) throws IOException { // nous envoie vers playerGameForm afin de rentrer les noms des joueurs 1v1
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessPlayerGameForm.fxml")); // On charge la page de formulaire FXML
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Change de scène pour accéder au tournoi.
     *
     * @param event L'événement de clic sur le bouton.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de la scène.
     */
    public void handleChangeSceneTournament(ActionEvent event) throws IOException { // nous envoie vers le tournoi
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessTournamentForm.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show(); // On fait apparaître le stage
    }

    public void handleChangeSceneGames() {
        List<String> players = readPlayersFromFile("chess_moves.csv");
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(players);
        VBoxDroite.getChildren().clear();
        VBoxDroite.getChildren().addAll(listView);
    }
}
