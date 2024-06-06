package fr.univamu.iut.chess.Piece;

public class Cavalier extends Piece {

    public Cavalier(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
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
