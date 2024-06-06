package fr.univamu.iut.chess.Piece;

public class Tour extends Piece {
    public Tour(Color color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] cases) {
        // La tour peut se déplacer horizontalement ou verticalement
        int deltaX = ligneArrivee - ligneDepart;
        int deltaY = colonneArrivee - colonneDepart;
        int xDir = (deltaX > 0) ? 1 : -1;
        int yDir = (deltaY > 0) ? 1 : -1;

        if (deltaX == 0) {
            for (int i = 1; i < Math.abs(deltaY); i++) {
                if (cases[ligneDepart][colonneDepart + i * yDir] != null) {
                    return false; // Il y a une pièce sur le chemin
                }
            }
            return true; // Aucune pièce sur le chemin
        } else if (deltaY == 0) {
            for (int i = 1; i < Math.abs(deltaX); i++) {
                if (cases[ligneDepart + i * xDir][colonneDepart] != null) {
                    return false; // Il y a une pièce sur le chemin
                }
            }
            return true; // Aucune pièce sur le chemin
        } else {
            return false; // Déplacement invalide (ni horizontal, ni vertical)
        }
    }
}