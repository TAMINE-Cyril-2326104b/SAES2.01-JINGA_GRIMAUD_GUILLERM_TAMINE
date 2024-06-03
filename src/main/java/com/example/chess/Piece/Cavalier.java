package com.example.chess.Piece;

public class Cavalier extends Pieces {
    public Cavalier(Couleur couleur, int x, int y, String imagePath) {
        super(couleur, x, y, imagePath);
    }

    @Override
    public boolean deplacementValide(int nouvelleX, int nouvelleY, Pieces[][] cases) {
        int deltaX = nouvelleX - x;
        int deltaY = nouvelleY - y;

        // Le cavalier peut se déplacer en "L" (2 cases dans une direction, 1 dans l'autre)
        return (deltaX == 1 && deltaY == 2) || (deltaX == 2 && deltaY == 1) ||
                (deltaX == -1 && deltaY == 2) || (deltaX == 2 && deltaY == -1) ||
                (deltaX == -2 && deltaY == 1) || (deltaX == 1 && deltaY == -2) ||
                (deltaX == -2 && deltaY == -1) || (deltaX == -1 && deltaY == -2) &&
                (cases[nouvelleX][nouvelleY] == null || cases[nouvelleX][nouvelleY].getCouleur() != couleur);
    }
}