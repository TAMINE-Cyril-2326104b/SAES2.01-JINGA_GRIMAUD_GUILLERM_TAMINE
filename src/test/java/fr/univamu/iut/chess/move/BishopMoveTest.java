package fr.univamu.iut.chess.move;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BishopMoveTest {

    private ChessPlayerGameController controller;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.plateau = new Chessboard();
    }

    @Test
    public void testBishopMove() {
        // Placer un fou blanc
        Piece bishop = new Bishop(Couleur.BLANC, new Position(0, 2));
        controller.plateau.setPiece(0, 2, bishop);
        controller.plateau.movePiece(0, 2, 2, 4, controller.plateau.getPieces());

        // Vérifier que le fou a été déplacé
        assertSame(2, bishop.getPosition().getRow());
        assertSame(4, bishop.getPosition().getCol());
        assertTrue(bishop.isMoveLegal(2, 4, 4, 6, controller.plateau.getPieces())); // On teste un déplacement légal
        assertNull(controller.plateau.getPieces()[0][2]); // Vérifier que la case précédente est vide
    }

    @Test
    public void testIllegalBishopMove() {
        // Placer un fou blanc
        Piece bishop = new Bishop(Couleur.BLANC, new Position(0, 2));
        controller.plateau.setPiece(0, 2, bishop);

        // Essayer de déplacer le fou d'une manière illégale
        boolean moveResult = bishop.isMoveLegal(0, 2, 0, 4, controller.plateau.getPieces());

        // Vérifier que le fou n'a pas été déplacé
        assertFalse(moveResult);
        assertSame(bishop, controller.plateau.getPieces()[0][2]);
    }
}
