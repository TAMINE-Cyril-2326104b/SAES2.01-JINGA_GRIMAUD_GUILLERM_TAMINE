package fr.univamu.iut.chess.move;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class KnightMoveTest {

    private ChessPlayerGameController controller;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.plateau = new Chessboard();
    }

    @Test
    public void testKnightMove() {
        // Placer un cavalier blanc
        Piece knight = new Knight(Couleur.BLANC, new Position(0, 1));
        controller.plateau.setPiece(0, 1, knight);
        controller.plateau.movePiece(0, 1, 2, 2, controller.plateau.getPieces());

        // Vérifier que le cavalier a été déplacé
        assertSame(2, knight.getPosition().getRow());
        assertSame(2, knight.getPosition().getCol());
        assertTrue(knight.isMoveLegal(2, 2, 4, 3, controller.plateau.getPieces())); // On teste un déplacement légal
        assertNull(controller.plateau.getPieces()[0][1]); // Vérifier que la case précédente est vide
    }

    @Test
    public void testIllegalKnightMove() {
        // Placer un cavalier blanc
        Piece knight = new Knight(Couleur.BLANC, new Position(0, 1));
        controller.plateau.setPiece(0, 1, knight);

        // Essayer de déplacer le cavalier d'une manière illégale
        boolean moveResult = knight.isMoveLegal(0, 1, 1, 1, controller.plateau.getPieces());

        // Vérifier que le cavalier n'a pas été déplacé
        assertFalse(moveResult);
        assertSame(knight, controller.plateau.getPieces()[0][1]);
    }
}
