package fr.univamu.iut.chess.Piece;

public class Tour extends Piece {
    public Tour(Color color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
        // Implémentation du déplacement de la tour
        return false;
    }
}