package fr.univamu.iut.chess.Piece;

public class Plateau {
    private final Piece[][] pieces;

    public Plateau() {
        Pion pionBlanc=new Pion("Blanc", "/fr/univ-amu/iut/chess/img/piecesBlanc/pionBlanc.png");
        placerPiece(pionBlanc, 0,1);
        this.pieces = new Piece[8][8];
    }

    public void placerPiece(Piece piece, int ligne, int colonne) {
        this.pieces[ligne][colonne] = piece;
    }

    public Piece getPieces(int i, int j) {
        return pieces[i][j];
    }
}
