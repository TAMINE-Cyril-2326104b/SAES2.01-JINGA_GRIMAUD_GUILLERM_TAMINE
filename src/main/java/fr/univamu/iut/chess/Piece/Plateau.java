package fr.univamu.iut.chess.Piece;

public class Plateau {
    private final Piece[][] pieces;

    public Plateau() {
        Pion pionBlanc=new Pion("Blanc", "/fr/univ-amu/iut/chess/img/piecesBlanc/pionBlanc.png");
        for (int ligne=0; ligne < 8 ; ++ligne){
            placerPiece(pionBlanc, ligne,1);
        }
        Pion pionNoir=new Pion("Noir", "/fr/univ-amu/iut/chess/img/piecesNoir/pionNoir.png");
        placerPiece(pionNoir, 8,8);
        this.pieces = new Piece[8][8];
    }

    public void placerPiece(Piece piece, int ligne, int colonne) {
        this.pieces[ligne][colonne] = piece;
    }

    public Piece getPieces(int i, int j) {
        return pieces[i][j];
    }
}
