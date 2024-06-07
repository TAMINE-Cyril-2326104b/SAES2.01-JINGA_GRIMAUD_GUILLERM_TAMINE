package fr.univamu.iut.chess.Piece;

public class Plateau {
    private final Piece[][] pieces;

    public Plateau() {
        this.pieces = new Piece[8][8];

        // Initialiser les pions
        for (int i = 0; i < 8; i++) {
            pieces[1][i] = new Pion(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/pionNoir.png", new Position(1, i));
            pieces[6][i] = new Pion(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/pionBlanc.png", new Position(6, i));
        }

        // Initialiser les tours
        pieces[7][0] = new Tour(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/tourBlanche.png", new Position(7, 0));
        pieces[7][7] = new Tour(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/tourBlanche.png", new Position(7, 7));
        pieces[0][0] = new Tour(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/tourNoire.png", new Position(0, 0));
        pieces[0][7] = new Tour(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/tourNoire.png", new Position(0, 7));

        // Initialiser les cavaliers
        pieces[7][1] = new Cavalier(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/cavalierBlanc.png", new Position(7, 1));
        pieces[7][6] = new Cavalier(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/cavalierBlanc.png", new Position(7, 6));
        pieces[0][1] = new Cavalier(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/cavalierNoir.png", new Position(0, 1));
        pieces[0][6] = new Cavalier(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/cavalierNoir.png", new Position(0, 6));

        // Initialiser les fous
        pieces[7][2] = new Fou(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/fouBlanc.png", new Position(7, 2));
        pieces[7][5] = new Fou(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/fouBlanc.png", new Position(7, 5));
        pieces[0][2] = new Fou(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/fouNoir.png", new Position(0, 2));
        pieces[0][5] = new Fou(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/fouNoir.png", new Position(0, 5));

        // Initialiser les reines
        pieces[7][3] = new Reine(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/reineBlanche.png", new Position(7, 3));
        pieces[0][3] = new Reine(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/reineNoire.png", new Position(0, 3));

        // Initialiser les rois
        pieces[7][4] = new Roi(Couleur.WHITE, "/fr/univamu/iut/chess/img/piecesBlanc/roiBlanc.png", new Position(7, 4));
        pieces[0][4] = new Roi(Couleur.BLACK, "/fr/univamu/iut/chess/img/piecesNoir/roiNoir.png", new Position(0, 4));
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public Piece getPieces(int ligne, int colonne) {
        return pieces[ligne][colonne];
    }

    public void deplacerPiece(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] pieces) {
        Piece piece = this.pieces[ligneDepart][colonneDepart];
        this.pieces[ligneDepart][colonneDepart] = null;
        piece.setPosition(new Position(ligneArrivee, colonneArrivee));
        this.pieces[ligneArrivee][colonneArrivee] = piece;
    }
    public Position findKingPosition(Couleur kingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = pieces[row][col];
                if (piece != null && piece instanceof Roi && piece.getColor() == kingColor) {
                    return new Position(row, col);
                }
            }
        }
        // Si le roi n'est pas trouvé (ce qui ne devrait pas arriver), renvoie null ou une exception
        return null;
    }
}
