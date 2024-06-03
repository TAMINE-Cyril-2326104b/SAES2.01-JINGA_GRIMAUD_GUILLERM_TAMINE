package com.example.chess.Piece;
public class Reine extends Pieces {
    public Reine(Couleur couleur, int x, int y, String imagePath) {
        super(couleur, x, y, imagePath);
    }

    @Override
    public boolean deplacementValide(int nouvelleX, int nouvelleY, Pieces[][] cases) {
        // La reine peut se déplacer comme une tour ou un fou
        Tour tour = new Tour(couleur, x, y, ""); // Simule un déplacement de tour
        Fou fou = new Fou(couleur, x, y, "");    // Simule un déplacement de fou
        return tour.deplacementValide(nouvelleX, nouvelleY, cases) &&
                fou.deplacementValide(nouvelleX, nouvelleY, cases);
    }
}
