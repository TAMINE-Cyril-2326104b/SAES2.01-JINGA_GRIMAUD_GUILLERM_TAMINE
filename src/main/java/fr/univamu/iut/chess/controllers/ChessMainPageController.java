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
