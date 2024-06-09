package fr.univamu.iut.chess.Piece;

/**
 * La classe Chessboard représente un plateau d'échecs avec ses pièces positionnées initialement.
 * Elle permet de manipuler les pièces sur le plateau.
 *
 * @autor Estelle GRIMAUD
 * @autor Mathis GUILLERM
 * @autor Paul JINGA
 * @autor Cyril TAMINE
 */
public class Chessboard {
    private final Piece[][] pieces;

    /**
     * Constructeur de la classe Chessboard.
     * Initialise le plateau avec les pièces placées dans leur position de départ.
     */
    public Chessboard() {
        this.pieces = new Piece[8][8];

        // Initialiser les pions
        for (int i = 0; i < 8; i++) {
            pieces[1][i] = new Pawn(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/pionNoir.png", new Position(1, i));
            pieces[6][i] = new Pawn(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/pionBlanc.png", new Position(6, i));
        }

        // Initialiser les tours
        pieces[7][0] = new Rook(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/tourBlanche.png", new Position(7, 0));
        pieces[7][7] = new Rook(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/tourBlanche.png", new Position(7, 7));
        pieces[0][0] = new Rook(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/tourNoire.png", new Position(0, 0));
        pieces[0][7] = new Rook(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/tourNoire.png", new Position(0, 7));

        // Initialiser les cavaliers
        pieces[7][1] = new Knight(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/cavalierBlanc.png", new Position(7, 1));
        pieces[7][6] = new Knight(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/cavalierBlanc.png", new Position(7, 6));
        pieces[0][1] = new Knight(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/cavalierNoir.png", new Position(0, 1));
        pieces[0][6] = new Knight(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/cavalierNoir.png", new Position(0, 6));

        // Initialiser les fous
        pieces[7][2] = new Bishop(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/fouBlanc.png", new Position(7, 2));
        pieces[7][5] = new Bishop(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/fouBlanc.png", new Position(7, 5));
        pieces[0][2] = new Bishop(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/fouNoir.png", new Position(0, 2));
        pieces[0][5] = new Bishop(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/fouNoir.png", new Position(0, 5));

        // Initialiser les reines
        pieces[7][3] = new Queen(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/reineBlanche.png", new Position(7, 3));
        pieces[0][3] = new Queen(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/reineNoire.png", new Position(0, 3));

        // Initialiser les rois
        pieces[7][4] = new King(Couleur.BLANC, "/fr/univamu/iut/chess/img/piecesBlanc/roiBlanc.png", new Position(7, 4));
        pieces[0][4] = new King(Couleur.NOIR, "/fr/univamu/iut/chess/img/piecesNoir/roiNoir.png", new Position(0, 4));
    }

    /**
     * Renvoie la matrice de pièces représentant le plateau.
     *
     * @return La matrice de pièces.
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Renvoie la pièce à une position spécifique sur le plateau.
     *
     * @param ligne   La ligne de la pièce.
     * @param colonne La colonne de la pièce.
     * @return        La pièce à la position spécifiée, ou null s'il n'y a pas de pièce.
     */
    public Piece getPieces(int ligne, int colonne) {
        return pieces[ligne][colonne];
    }

    /**
     * Déplace une pièce d'une position de départ à une position d'arrivée sur le plateau.
     *
     * @param ligneDepart      La ligne de départ de la pièce.
     * @param colonneDepart    La colonne de départ de la pièce.
     * @param ligneArrivee     La ligne d'arrivée de la pièce.
     * @param colonneArrivee   La colonne d'arrivée de la pièce.
     * @param pieces           La matrice de pièces représentant le plateau.
     */
    public void movePiece(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] pieces) {
        Piece piece = this.pieces[ligneDepart][colonneDepart];
        this.pieces[ligneDepart][colonneDepart] = null;
        piece.setPosition(new Position(ligneArrivee, colonneArrivee));
        this.pieces[ligneArrivee][colonneArrivee] = piece;
    }

    /**
     * Définit une pièce à une position spécifique sur le plateau.
     *
     * @param row    La ligne où placer la pièce.
     * @param col    La colonne où placer la pièce.
     * @param piece  La pièce à placer.
     */
    public void setPiece(int row, int col, Piece piece) {
        this.pieces[row][col] = piece;
    }

    /**
     * Trouve la position du roi d'une couleur spécifique sur le plateau.
     *
     * @param kingColor La couleur du roi à chercher.
     * @return          La position du roi, ou null si le roi n'est pas trouvé.
     */
    public Position findKingPosition(Couleur kingColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = pieces[row][col];
                if (piece != null && piece instanceof King && piece.getColor() == kingColor) {
                    return new Position(row, col);
                }
            }
        }
        // Si le roi n'est pas trouvé (ce qui ne devrait pas arriver), renvoie null.
        return null;
    }
}
