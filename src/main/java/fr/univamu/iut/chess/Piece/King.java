package fr.univamu.iut.chess.Piece;

/**
 * La classe King représente un Roi dans le jeu d'échecs.
 * Elle hérite des caractéristiques de la classe abstraite Piece.
 *
 * @autor Estelle GRIMAUD
 * @autor Mathis GUILLERM
 * @autor Paul JINGA
 * @autor Cyril TAMINE
 */
public class King extends Piece {
    /**
     * Constructeur de la classe King.
     *
     * @param couleur La couleur de la pièce (NOIR ou BLANC).
     * @param imagePath Le chemin de l'image représentant la pièce.
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public King(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    /**
     * Constructeur de la classe King sans chemin d'image.
     *
     * @param couleur La couleur de la pièce (NOIR ou BLANC).
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public King(Couleur couleur, Position position) {
        super(couleur, position);
    }

    /**
     * Vérifie si le déplacement du Roi est légal.
     * Le Roi peut se déplacer d'une case dans n'importe quelle direction.
     *
     * @param ligneDepart La ligne de départ du Roi.
     * @param colonneDepart La colonne de départ du Roi.
     * @param ligneArrivee La ligne d'arrivée du Roi.
     * @param colonneArrivee La colonne d'arrivée du Roi.
     * @param plateau L'échiquier actuel avec toutes les pièces.
     * @return true si le déplacement est légal, false sinon.
     */
    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int deltaLigne = Math.abs(ligneArrivee - ligneDepart);
        int deltaColonne = Math.abs(colonneArrivee - colonneDepart);

        // Vérifier que le Roi se déplace d'une case dans n'importe quelle direction
        if (deltaLigne <= 1 && deltaColonne <= 1) {
            Piece pieceArrivee = plateau[ligneArrivee][colonneArrivee];
            // Le Roi ne peut pas se déplacer sur une case occupée par une pièce de la même couleur
            return pieceArrivee == null || pieceArrivee.getColor() != this.getColor();
        }

        return false;
    }
}
