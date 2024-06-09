package fr.univamu.iut.chess;

import static org.junit.Assert.*;

import fr.univamu.iut.chess.Piece.Chessboard;
import fr.univamu.iut.chess.Piece.Couleur;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ChessControllerTest {

    private ChessPlayerGameController controller;
    private GridPane gridPaneJeu;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        gridPaneJeu = new GridPane();
        Label tourMessage = new Label();
        Label echecLabel = new Label();
        Label mouvImpo = new Label();
        controller.gridPaneJeu = gridPaneJeu;
        controller.tourMessage = tourMessage;
        controller.echecLabel = echecLabel;
        controller.mouvImpo = mouvImpo;
        controller.plateau = new Chessboard();
        controller.currentTurn = Couleur.WHITE;
    }

    @Test
    public void testAfficherPlateau() {
        controller.afficherPlateau();
        assertEquals(64, gridPaneJeu.getChildren().size());
    }
}
