package fr.univamu.iut.chess.Piece;

public class Rook extends Piece {
    // La classe Rook hérite des caractéristiques de la classe abstraite Piece.
    public Rook(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    // On utilise l'annotation @Override et on réecrit la fonction isMoveLegal :
    // La tour ne peut se déplacer uniquement à la verticale ou l'horizontale.
    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        if (ligneDepart != ligneArrivee && colonneDepart != colonneArrivee) {
            return false;
        }

        if (detecterCollision(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau)) {
            return false;
        }

        Piece pieceArrivee = plateau[ligneArrivee][colonneArrivee];
        return pieceArrivee == null || pieceArrivee.getColor() != this.getColor();
    }

    private boolean detecterCollision(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int deltaX = ligneArrivee - ligneDepart;
        int deltaY = colonneArrivee - colonneDepart;

        int stepX = Integer.signum(deltaX);
        int stepY = Integer.signum(deltaY);

        for (int i = ligneDepart + stepX, j = colonneDepart + stepY; i != ligneArrivee || j != colonneArrivee; i += stepX, j += stepY) {
            if (plateau[i][j] != null) {
                return true;
            }
        }

        return false;
    }
}
