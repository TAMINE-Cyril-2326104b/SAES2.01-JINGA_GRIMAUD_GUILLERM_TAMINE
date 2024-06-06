package fr.univamu.iut.chess.Piece;

public class Roi extends Piece {
    public Roi(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
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
