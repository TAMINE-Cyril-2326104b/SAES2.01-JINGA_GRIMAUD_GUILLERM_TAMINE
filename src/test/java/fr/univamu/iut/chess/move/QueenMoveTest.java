package fr.univamu.iut.chess.move;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class QueenMoveTest {

    private ChessPlayerGameController controller;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.plateau = new Chessboard();
    }

    @Test
    public void testQueenMove() {
        // Déplacer une reine blanche d'une case en diagonale
        Piece queen = new Queen(Couleur.BLANC, new Position(0, 3));
        controller.plateau.setPiece(0, 3, queen);
        controller.plateau.movePiece(0, 3, 1, 4, controller.plateau.getPieces());

        // Vérifier que la reine a été déplacée
        assertSame(1, queen.getPosition().getRow());
        assertSame(4, queen.getPosition().getCol());
        assertTrue(queen.isMoveLegal(1, 4, 3, 6, controller.plateau.getPieces())); // On teste un déplacement légal (diagonale).
        assertNull(controller.plateau.getPieces()[0][3]); // Vérifier que la case précédente est vide
    }

    @Test
    public void testIllegalQueenMove() {
        // Placer une reine blanche
        Piece queen = new Queen(Couleur.BLANC, new Position(0, 3));
        controller.plateau.setPiece(0, 3, queen);

        // Essayer de déplacer la reine d'une manière illégale
        boolean moveResult = queen.isMoveLegal(0, 3, 1, 5, controller.plateau.getPieces());

        // Vérifier que la reine n'a pas été déplacée
        assertFalse(moveResult);
        assertSame(queen, controller.plateau.getPieces()[0][3]);
    }
}
