package fr.univamu.iut.chess.Piece;

public class Pawn extends Piece {
    // La classe Pion hérite des caractéristiques de la classe abstraite Piece.
    public Pawn(Couleur color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int direction = (this.getColor() == Couleur.WHITE) ? -1 : 1; // Les pions blancs montent, les pions noirs descendent

        // Vérification du déplacement d'une case en avant
        if (ligneArrivee == ligneDepart + direction && colonneArrivee == colonneDepart) {
            return plateau[ligneArrivee][colonneArrivee] == null;
        }

        // Vérification du déplacement de deux cases en avant depuis la position de départ
        if ((this.getColor() == Couleur.WHITE && ligneDepart == 6 || this.getColor() == Couleur.BLACK && ligneDepart == 1) &&
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
