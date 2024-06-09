package fr.univamu.iut.chess.move;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe de test pour les mouvements du pion.
 */
public class PawnMoveTest {

    private ChessPlayerGameController controller;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.plateau = new Chessboard();
    }

    /**
     * Teste le déplacement légal du pion.
     */
    @Test
    public void testPawnMove() {
        // Placer un pion blanc
        Piece pawn = new Pawn(Couleur.BLANC, new Position(1, 0));
        controller.plateau.setPiece(1, 0, pawn);
        controller.plateau.movePiece(1, 0, 2, 0, controller.plateau.getPieces());

        // Vérifier que le pion a été déplacé
        assertSame(2, pawn.getPosition().getRow());
        assertSame(0, pawn.getPosition().getCol());
        assertNull(controller.plateau.getPieces()[1][0]); // Vérifier que la case précédente est vide
    }

    /**
     * Teste un déplacement illégal du pion.
     */
    @Test
    public void testIllegalPawnMove() {
        // Placer un pion blanc
        Piece pawn = new Pawn(Couleur.BLANC, new Position(1, 0));
        controller.plateau.setPiece(1, 0, pawn);

        // Essayer de déplacer le pion d'une manière illégale (en diagonale)
        boolean moveResult = pawn.isMoveLegal(1, 0, 2, 2, controller.plateau.getPieces());

        // Vérifier que le pion n'a pas été déplacé
        assertFalse(moveResult);
        assertSame(pawn, controller.plateau.getPieces()[1][0]);
    }
}
