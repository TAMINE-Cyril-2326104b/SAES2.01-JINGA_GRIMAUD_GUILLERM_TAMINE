package fr.univamu.iut.chess.Piece;

public class Roi extends Piece {
    public Roi(Color color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] cases) {
        // Implémentation du déplacement du roi
        return false;
    }
}