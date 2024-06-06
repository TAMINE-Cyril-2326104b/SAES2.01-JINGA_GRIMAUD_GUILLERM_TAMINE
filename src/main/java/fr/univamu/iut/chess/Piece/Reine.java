package fr.univamu.iut.chess.Piece;

public class Reine extends Piece {
    public Reine(Color color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
        // Implémentation du déplacement de la reine
        return false;
    }
}