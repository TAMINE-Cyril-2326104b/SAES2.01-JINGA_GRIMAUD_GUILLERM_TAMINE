package com.example.chess.Piece;

public class Fou extends Pieces {
    public Fou(Couleur couleur, int x, int y, String imagePath) {
        super(couleur, x, y, imagePath);
    }

    @Override
    public boolean deplacementValide(int nouvelleX, int nouvelleY, Pieces[][] cases) {
        // Le fou peut se déplacer en diagonale
        int deltaX = nouvelleX - x;
        int deltaY = nouvelleY - y;
        int xDir = (deltaX > 0) ? 1 : -1;
        int yDir = (deltaY > 0) ? 1 : -1;

        for (int i = 1; i < Math.abs(deltaX); i++) {
            if (cases[x + i * xDir][y + i * yDir] != null) {
                return false; // Il y a une pièce sur le chemin
            }
        }
        return true; // Aucune pièce sur le chemin
    }
}