package com.example.chess.Piece;
public class Roi extends Pieces {
    public Roi(Couleur couleur, int x, int y, String imagePath) {
        super(couleur, x, y, imagePath);
    }

    @Override
    public boolean deplacementValide(int nouvelleX, int nouvelleY, Pieces[][] cases) {
        int deltaX = nouvelleX - x;
        int deltaY = nouvelleY - y;

        // Le roi peut se déplacer d'une case dans n'importe quelle direction
        return deltaX >= -1 && deltaX <= 1 && deltaY >= -1 && deltaY <= 1 &&
                (cases[nouvelleX][nouvelleY] == null || cases[nouvelleX][nouvelleY].getCouleur() != couleur);
    }
}