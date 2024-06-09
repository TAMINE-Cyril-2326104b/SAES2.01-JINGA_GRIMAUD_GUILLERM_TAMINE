package fr.univamu.iut.chess.Piece;



public class Knight extends Piece {

    public Knight(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    public Knight(Couleur couleur, Position position) {
        super(couleur, position);
    }

    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int deltaLigne = Math.abs(ligneArrivee - ligneDepart);
        int deltaColonne = Math.abs(colonneArrivee - colonneDepart);

        // Vérifier que le déplacement forme un "L"
        if ((deltaLigne == 2 && deltaColonne == 1) || (deltaLigne == 1 && deltaColonne == 2)) {
            Piece pieceArrivee = plateau[ligneArrivee][colonneArrivee];
            return pieceArrivee == null || pieceArrivee.getColor() != this.getColor();
        }

        return false;
    }
}
