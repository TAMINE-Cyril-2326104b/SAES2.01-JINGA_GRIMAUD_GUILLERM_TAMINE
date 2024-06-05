package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Plateau;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChessMainPageController implements Initializable {

    @FXML
    private GridPane gridPaneJeu;

    private Plateau plateau;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // plateau = new Plateau();
        // Afficher les pions sur le plateau
        // afficherPlateau();
        Image image= new Image( ChessApplication.class.getResource("img/piecesBlanc/pionBlanc.png").toString());
        ImageView imageview= new ImageView(image);
        gridPaneJeu.add(imageview,0,0);
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

/*

    @FXML
    public void handleJouerPlayerButtonClicked(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChessPlayerGame.fxml"));
            Parent jeuRoot = loader.load();

            Scene jeuScene = new Scene(jeuRoot);
            Stage primaryStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(jeuScene);
            primaryStage.setTitle("Partie Lancée : Contre un ami.");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleJouerBotButtonClicked(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChessBotGame.fxml"));
            Parent jeuRoot = loader.load();

            Scene jeuScene = new Scene(jeuRoot);
            Stage primaryStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(jeuScene);
            primaryStage.setTitle("Partie Lancée : Contre l'ordinateur.");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

}
