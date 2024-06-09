package fr.univamu.iut.chess.move;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de test pour les mouvements de la tour.
 */
public class RookMoveTest {

    private ChessPlayerGameController controller;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.plateau = new Chessboard();
    }

    /**
     * Teste le déplacement légal de la tour.
     */
    @Test
    public void testRookMove() {
        // Placer une tour blanche
        Piece rook = new Rook(Couleur.BLANC, new Position(0, 0));
        controller.plateau.setPiece(0, 0, rook);
        controller.plateau.movePiece(0, 0, 0, 5, controller.plateau.getPieces());

        // Vérifier que la tour a été déplacée
        assertSame(0, rook.getPosition().getRow());
        assertSame(5, rook.getPosition().getCol());
        assertTrue(rook.isMoveLegal(0, 5, 5, 5, controller.plateau.getPieces())); // On teste un déplacement légal
        assertNull(controller.plateau.getPieces()[0][0]); // Vérifier que la case précédente est vide
    }

    /**
     * Teste un déplacement illégal de la tour.
     */
    @Test
    public void testIllegalRookMove() {
        // Placer une tour blanche
        Piece rook = new Rook(Couleur.BLANC, new Position(0, 0));
        controller.plateau.setPiece(0, 0, rook);

        // Essayer de déplacer la tour d'une manière illégale
        boolean moveResult = rook.isMoveLegal(0, 0, 1, 1, controller.plateau.getPieces());

        // Vérifier que la tour n'a pas été déplacée
        assertFalse(moveResult);
        assertSame(rook, controller.plateau.getPieces()[0][0]);
    }
}
