package fr.univamu.iut.chess.Piece;

public class Fou extends Piece {
    public Fou(Color color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] cases) {
        // Implémentation du déplacement du fou
        return false;
    }
}