package fr.univamu.iut.chess.turn;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;
import javafx.scene.control.Label;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SwitchTurnTest {

    private ChessPlayerGameController controller;
    private Label tourMessage;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();

        // Initialisation manuelle des composants
        controller.tourMessage = new Label();
        controller.timeLabelWhite = new Label();
        controller.timeLabelBlack = new Label();

        controller.plateau = new Chessboard();
        controller.currentTurn = Couleur.BLANC;
    }

    @Test
    public void testSwitchTurn() {
        // Vérifier que la couleur initiale est blanche
        assertEquals(Couleur.BLANC, controller.currentTurn);

        // Changer le tour
        controller.switchTurn();

        // Vérifier que la couleur a changé en noir
        assertEquals(Couleur.NOIR, controller.currentTurn);

        // Changer le tour à nouveau
        controller.switchTurn();

        // Vérifier que la couleur est à nouveau blanche
        assertEquals(Couleur.BLANC, controller.currentTurn);
    }
}
