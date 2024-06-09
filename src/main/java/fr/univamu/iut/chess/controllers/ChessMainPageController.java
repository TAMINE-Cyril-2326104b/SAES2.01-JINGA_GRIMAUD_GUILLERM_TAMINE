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
import javafx.scene.control.ChoiceBox;
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

public class ChessMainPageController implements Initializable {

    @FXML
    private GridPane gridPaneJeu;

    private Chessboard chessboard;
    @FXML
    private VBox VBoxDroite;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearCSVFile();
        this.chessboard = new Chessboard();
        displayChessboard();
    }
    public void handleChangeSceneJoueurList(ActionEvent event) throws IOException { // apres avoir cliqué sur le bouton la partie droite change et affiche la liste des joueurs
        List<String> players = readPlayersFromFile("PlayerGame_joueurs.csv");
        List<String> playersVSBot = readPlayersFromFile("BotGame_joueur.csv");
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(players);
        ListView<String> listViewBot = new ListView<>();
        listViewBot.getItems().addAll(playersVSBot);
        VBoxDroite.getChildren().clear();
        VBoxDroite.getChildren().addAll(listView,listViewBot);
    }
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
    private void clearCSVFile() {   //supprime tout le contenue du fichier contenant les joueurs du tournois
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("TournamentGame.csv"))) {
            // Writing an empty string to clear the file
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayChessboard() {
        gridPaneJeu.getChildren().clear();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Rectangle rectangle = new Rectangle(80, 80);
                if ((row + column) % 2 == 0) {
                    rectangle.setFill(Color.rgb(235,236,208));
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

                } else {
                }

                gridPaneJeu.add(stackPane, column, row);
            }
        }
    }

    // Une fonction permettant de changer de scène et d'affronter l'ordinateur
    public void handleChangeSceneBot(ActionEvent event) throws IOException { // nous envoie vers le mode de jeu contre le bot
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessBotGameForm.fxml")); // On charge la page de formulaire FXML
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show(); // On fait apparaître le stage
    }

    public void handleNewGameButtonAction(ActionEvent event) throws IOException{ //action faite lorsque l'on clique sur le bouton nouvelle partie, nous remet sur la page de base
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessMainPage.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show();
    }

    // Une fonction permettant de changer de scène et d'affronter un autre joueur
    public void handleChangeScenePlayer(ActionEvent event) throws IOException{ // nous envoie vers playerGameForm afin de rentrer les noms des joueurs 1v1
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessPlayerGameForm.fxml")); // On charge la page de formulaire FXML
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show();
    }
    public void handleChangeSceneTournament(ActionEvent event) throws IOException{ // nous envoie vers le tournois
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessTournamentForm.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show(); // On fait apparaître le stage
    }
}
