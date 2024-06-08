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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChessMainPageController implements Initializable {

    @FXML
    private GridPane gridPaneJeu;

    private Chessboard chessboard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.chessboard = new Chessboard();
        displayChessboard();
    }

    public void displayChessboard() {
        gridPaneJeu.getChildren().clear();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Rectangle rectangle = new Rectangle(40, 40);
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
    public void handleChangeSceneBot(ActionEvent event) throws IOException {
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessBotGameForm.fxml")); // On charge la page de formulaire FXML
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show(); // On fait apparaître le stage
    }

    // Une fonction permettant de changer de scène et d'affronter un autre joueur
    public void handleChangeScenePlayer(ActionEvent event) throws IOException{
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessPlayerGameForm.fxml")); // On charge la page de formulaire FXML
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.centerOnScreen();
        stage.show(); // On fait apparaître le stage
    }
}
