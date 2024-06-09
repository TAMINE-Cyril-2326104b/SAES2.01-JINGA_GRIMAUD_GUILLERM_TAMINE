package fr.univamu.iut.chess.Piece;

/**
 * La classe Pawn représente un Pion dans le jeu d'échecs.
 * Elle hérite des caractéristiques de la classe abstraite Piece.
 *
 * @autor Estelle GRIMAUD
 * @autor Mathis GUILLERM
 * @autor Paul JINGA
 * @autor Cyril TAMINE
 */
public class Pawn extends Piece {

    /**
     * Constructeur de la classe Pawn.
     *
     * @param color La couleur de la pièce (NOIR ou BLANC).
     * @param imagePath Le chemin de l'image représentant la pièce.
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public Pawn(Couleur color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    /**
     * Constructeur de la classe Pawn sans chemin d'image.
     *
     * @param color La couleur de la pièce (NOIR ou BLANC).
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public Pawn(Couleur color, Position position){
        super(color, position);
    }

    /**
     * Vérifie si le déplacement du Pion est légal.
     * Les règles de déplacement du Pion sont spécifiques :
     * - Il peut avancer d'une case vers l'avant s'il n'y a pas de pièce devant lui.
     * - Il peut avancer de deux cases depuis sa position initiale s'il n'y a pas de pièce sur les deux cases devant lui.
     * - Il peut capturer une pièce en diagonale vers l'avant.
     *
     * @param ligneDepart La ligne de départ du Pion.
     * @param colonneDepart La colonne de départ du Pion.
     * @param ligneArrivee La ligne d'arrivée du Pion.
     * @param colonneArrivee La colonne d'arrivée du Pion.
     * @param plateau L'échiquier actuel avec toutes les pièces.
     * @return true si le déplacement est légal, false sinon.
     */
    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int direction = (this.getColor() == Couleur.BLANC) ? -1 : 1; // Les pions blancs montent, les pions noirs descendent

        // Vérification du déplacement d'une case en avant
        if (ligneArrivee == ligneDepart + direction && colonneArrivee == colonneDepart) {
            return plateau[ligneArrivee][colonneArrivee] == null;
        }

        // Vérification du déplacement de deux cases en avant depuis la position de départ
        if ((this.getColor() == Couleur.BLANC && ligneDepart == 6 || this.getColor() == Couleur.NOIR && ligneDepart == 1) &&
                ligneArrivee == ligneDepart + 2 * direction && colonneArrivee == colonneDepart) {
            return plateau[ligneArrivee][colonneArrivee] == null && plateau[ligneDepart + direction][colonneArrivee] == null;
        }

        // Vérification du déplacement en diagonale pour la capture
        if (ligneArrivee == ligneDepart + direction && (colonneArrivee == colonneDepart + 1 || colonneArrivee == colonneDepart - 1)) {
            return plateau[ligneArrivee][colonneArrivee] != null && plateau[ligneArrivee][colonneArrivee].getColor() != this.getColor();
        }

        return false;
    }
}
