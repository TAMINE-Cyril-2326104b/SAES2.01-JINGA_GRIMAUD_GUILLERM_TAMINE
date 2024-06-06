package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import javafx.scene.paint.Color;
import fr.univamu.iut.chess.Piece.Piece;
import fr.univamu.iut.chess.Piece.Plateau;
import fr.univamu.iut.chess.Piece.Position;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChessMainPageController implements Initializable {

    @FXML
    private GridPane gridPaneJeu;

    private Plateau plateau;
    private Piece selectedPiece;
    private Position selectedPosition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.plateau = new Plateau();
        afficherPlateau();
    }

    public void afficherPlateau() {
        gridPaneJeu.getChildren().clear();

        for (int ligne = 0; ligne < 8; ligne++) {
            for (int colonne = 0; colonne < 8; colonne++) {
                Rectangle rectangle = new Rectangle(40, 40);
                if ((ligne + colonne) % 2 == 0) {
                    rectangle.setFill(Color.GREEN);
                } else {
                    rectangle.setFill(Color.BEIGE);
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
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        handlePieceClick(piece, new Position(finalLigne, finalColonne));
                    });
                } else {
                    int finalLigne1 = ligne;
                    int finalColonne1 = colonne;
                    stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        handleEmptySquareClick(new Position(finalLigne1, finalColonne1));
                    });
                }

                gridPaneJeu.add(stackPane, colonne, ligne);
            }
        }
    }

    private void handlePieceClick(Piece piece, Position position) {
        if (selectedPiece == null) {
            selectedPiece = piece;
            selectedPosition = position;
            System.out.println("Piece selected: " + piece.getClass().getSimpleName() + " at position " + position.getRow() + ", " + position.getCol());
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
        if (selectedPiece != null && selectedPiece.estDeplacementValide(
                selectedPosition.getRow(), selectedPosition.getCol(),
                newPosition.getRow(), newPosition.getCol(), plateau.getPieces())) {
            System.out.println("Moving piece to " + newPosition.getRow() + ", " + newPosition.getCol());
            plateau.deplacerPiece(
                    selectedPosition.getRow(), selectedPosition.getCol(),
                    newPosition.getRow(), newPosition.getCol(), new Piece[selectedPosition.getRow()][selectedPosition.getCol()]);
            selectedPiece = null;
            selectedPosition = null;
            afficherPlateau();
        }
    }

    public void handleChangeSceneBot(ActionEvent event) throws IOException {
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessBotGameForm.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.show();
    }

    public void handleChangeScenePlayer(ActionEvent event) throws IOException {
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessPlayerGameForm.fxml"));
        Scene secondScene = new Scene(secondSceneParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(secondScene);
        stage.show();
    }
}
