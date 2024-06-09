package fr.univamu.iut.chess.move;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KingMoveTest {

    private ChessPlayerGameController controller;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.plateau = new Chessboard();
    }

    @Test
    public void testKingMove() {
        // Placer un roi blanc
        Piece king = new King(Couleur.BLANC, new Position(0, 4));
        controller.plateau.setPiece(0, 4, king);
        controller.plateau.movePiece(0, 4, 1, 4, controller.plateau.getPieces());

        // Vérifier que le roi a été déplacé
        assertSame(1, king.getPosition().getRow());
        assertSame(4, king.getPosition().getCol());
        assertTrue(king.isMoveLegal(1, 4, 1, 5, controller.plateau.getPieces())); // On teste un déplacement légal
        assertNull(controller.plateau.getPieces()[0][4]); // Vérifier que la case précédente est vide
    }

    @Test
    public void testIllegalKingMove() {
        // Placer un roi blanc
        Piece king = new King(Couleur.BLANC, new Position(0, 4));
        controller.plateau.setPiece(0, 4, king);

        // Essayer de déplacer le roi d'une manière illégale
        boolean moveResult = king.isMoveLegal(0, 4, 2, 4, controller.plateau.getPieces());

        // Vérifier que le roi n'a pas été déplacé
        assertFalse(moveResult);
        assertSame(king, controller.plateau.getPieces()[0][4]);
    }
}
