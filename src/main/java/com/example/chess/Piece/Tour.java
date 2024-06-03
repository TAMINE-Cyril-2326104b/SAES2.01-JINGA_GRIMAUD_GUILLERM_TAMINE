package com.example.chess.Piece;
public class Tour extends Pieces {
    public Tour(Couleur couleur, int x, int y, String imagePath) {
        super(couleur, x, y, imagePath);
    }

    @Override
    public boolean deplacementValide(int nouvelleX, int nouvelleY, Pieces[][] cases) {
        // La tour peut se déplacer horizontalement ou verticalement
        int deltaX = nouvelleX - x;
        int deltaY = nouvelleY - y;
        int xDir = (deltaX > 0) ? 1 : -1;
        int yDir = (deltaY > 0) ? 1 : -1;

        if (deltaX == 0) {
            for (int i = 1; i < Math.abs(deltaY); i++) {
                if (cases[x][y + i * yDir] != null) {
                    return false; // Il y a une pièce sur le chemin
                }
            }
            return true; // Aucune pièce sur le chemin
        } else if (deltaY == 0) {
            for (int i = 1; i < Math.abs(deltaX); i++) {
                if (cases[x + i * xDir][y] != null) {
                    return false; // Il y a une pièce sur le chemin
                }
            }
            return true; // Aucune pièce sur le chemin
        } else {
            return false; // Déplacement invalide (ni horizontal, ni vertical)
        }
    }
}