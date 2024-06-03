package com.example.chess.Piece;
public class Pion extends Pieces{
    public Pion(Couleur couleur, int x, int y, String imagePath) {
        super(couleur, x, y, imagePath);
    }

    @Override
    public boolean deplacementValide(int nouvelleX, int nouvelleY, Pieces[][] cases) {
        if (couleur == Couleur.BLANC) {
            // Déplacement en avant d'une case
            if (nouvelleY == y + 1 && nouvelleX == x && cases[x][y + 1] == null) {
                return true;
            }
            // Déplacement en avant de deux cases depuis la rangée de départ
            if (y == 1 && nouvelleY == y + 2 && nouvelleX == x && cases[x][y + 1] == null && cases[x][y + 2] == null) {
                return true;
            }
            // Capture diagonale
            if (nouvelleY == y + 1 && (nouvelleX == x - 1 || nouvelleX == x + 1) && cases[nouvelleX][nouvelleY] != null) {
                return true;
            }
        } else {
            // Déplacement en avant d'une case
            if (nouvelleY == y - 1 && nouvelleX == x && cases[x][y - 1] == null) {
                return true;
            }
            // Déplacement en avant de deux cases depuis la rangée de départ
            if (y == 6 && nouvelleY == y - 2 && nouvelleX == x && cases[x][y - 1] == null && cases[x][y - 2] == null) {
                return true;
            }
            // Capture diagonale
            if (nouvelleY == y - 1 && (nouvelleX == x - 1 || nouvelleX == x + 1) && cases[nouvelleX][nouvelleY] != null) {
                return true;
            }
        }
        return false;
    }

}
