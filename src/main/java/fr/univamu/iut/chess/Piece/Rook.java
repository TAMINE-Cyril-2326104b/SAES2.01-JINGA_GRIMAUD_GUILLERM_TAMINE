package fr.univamu.iut.chess.Piece;

/**
 * La classe Rook représente une pièce de Tour dans un jeu d'échecs.
 * Elle hérite des caractéristiques de la classe abstraite Piece.
 *
 * @author [Auteur(s)]
 */
public class Rook extends Piece {

    /**
     * Constructeur de la classe Rook.
     *
     * @param couleur    La couleur de la pièce.
     * @param imagePath  Le chemin vers l'image représentant la pièce.
     * @param position   La position initiale de la pièce.
     */
    public Rook(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    /**
     * Constructeur de la classe Rook sans spécifier le chemin de l'image.
     *
     * @param couleur   La couleur de la pièce.
     * @param position  La position initiale de la pièce.
     */
    public Rook(Couleur couleur, Position position) {
        super(couleur, position);
    }

    /**
     * Vérifie si le déplacement de la tour est légal.
     *
     * @param ligneDepart     La ligne de départ.
     * @param colonneDepart   La colonne de départ.
     * @param ligneArrivee    La ligne d'arrivée.
     * @param colonneArrivee  La colonne d'arrivée.
     * @param plateau         Le plateau de jeu.
     * @return                Vrai si le déplacement est légal, sinon faux.
     */
    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        // Vérifier si le déplacement est vertical ou horizontal
        if (ligneDepart != ligneArrivee && colonneDepart != colonneArrivee) {
            return false;
        }

        // Vérifier s'il y a une collision sur le chemin
        if (detecterCollision(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau)) {
            return false;
        }

        // Vérifier s'il n'y a pas de pièce de la même couleur sur la case d'arrivée
        Piece pieceArrivee = plateau[ligneArrivee][colonneArrivee];
        return pieceArrivee == null || pieceArrivee.getColor() != this.getColor();
    }

    /**
     * Détecte s'il y a une collision entre la position de départ et la position d'arrivée.
     *
     * @param ligneDepart     La ligne de départ.
     * @param colonneDepart   La colonne de départ.
     * @param ligneArrivee    La ligne d'arrivée.
     * @param colonneArrivee  La colonne d'arrivée.
     * @param plateau         Le plateau de jeu.
     * @return                Vrai s'il y a une collision, sinon faux.
     */
    private boolean detecterCollision(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int deltaX = ligneArrivee - ligneDepart;
        int deltaY = colonneArrivee - colonneDepart;

        int stepX = Integer.signum(deltaX);
        int stepY = Integer.signum(deltaY);

        // Parcourir les cases entre la position de départ et d'arrivée
        for (int i = ligneDepart + stepX, j = colonneDepart + stepY; i != ligneArrivee || j != colonneArrivee; i += stepX, j += stepY) {
            if (plateau[i][j] != null) {
                return true; // Il y a une pièce sur le chemin
            }
        }

        return false; // Pas de collision
    }
}
