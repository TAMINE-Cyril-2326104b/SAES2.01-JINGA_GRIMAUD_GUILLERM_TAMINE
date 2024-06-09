package fr.univamu.iut.chess.Piece;

/**
 * La classe Position représente les coordonnées d'une case sur l'échiquier.
 * Elle est utilisée pour définir la position des pièces.
 *
 * @author [Auteur(s)]
 */
public class Position {
    private int row;
    private int col;

    /**
     * Constructeur de la classe Position.
     *
     * @param row La ligne de la position.
     * @param col La colonne de la position.
     */
    public Position(int row, int col){
        this.col=col;
        this.row=row;
    }

    /**
     * Renvoie la colonne de la position.
     *
     * @return La colonne de la position.
     */
    public int getCol() {
        return col;
    }

    /**
     * Renvoie la ligne de la position.
     *
     * @return La ligne de la position.
     */
    public int getRow() {
        return row;
    }
}
