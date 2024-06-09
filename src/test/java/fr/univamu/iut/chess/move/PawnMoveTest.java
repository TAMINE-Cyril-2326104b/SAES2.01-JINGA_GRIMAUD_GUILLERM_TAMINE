package fr.univamu.iut.chess.move;

import fr.univamu.iut.chess.Piece.*;
import fr.univamu.iut.chess.controllers.ChessPlayerGameController;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PawnMoveTest {

    private ChessPlayerGameController controller;

    @Before
    public void setUp() {
        controller = new ChessPlayerGameController();
        controller.plateau = new Chessboard();

    }


    @Test
    public void testPawnMove() {
            // Déplacer un pion blanc d'une case vers le haut
            Piece pawn = new Pawn(Couleur.WHITE, new Position(1, 0));
            controller.plateau.setPiece(1, 0, pawn);
            controller.plateau.movePiece(1,0,2,0, controller.plateau.getPieces());

            // Vérifier que le pion a été déplacé
            assertSame(2, pawn.getPosition().getRow());
            assertNull(controller.plateau.getPieces()[1][0]); // Vérifier que la case précédente est vide
        };

}


