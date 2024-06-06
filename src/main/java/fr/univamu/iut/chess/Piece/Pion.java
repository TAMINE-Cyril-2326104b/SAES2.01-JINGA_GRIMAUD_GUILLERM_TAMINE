package fr.univamu.iut.chess.Piece;


public class Pion extends Piece {
    public Pion(Color color, String imagePath, Position position) {
        super(color, imagePath, position);
    }



    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] cases) {
        int direction = this.getCouleur().equals("WHITE") ? -1 : 1;

        // Mouvement de 1 case en avant
        if (colonneDepart == colonneArrivee && ligneArrivee == ligneDepart + direction) {
            return true;
        }

        // Mouvement initial de 2 cases
        if (colonneDepart == colonneArrivee && ligneArrivee == ligneDepart + 2 * direction && (ligneDepart == 1 || ligneDepart == 6)) {
            return true;
        }

        // Capture diagonale
        return Math.abs(colonneArrivee - colonneDepart) == 1 && ligneArrivee == ligneDepart + direction;
    }


}


