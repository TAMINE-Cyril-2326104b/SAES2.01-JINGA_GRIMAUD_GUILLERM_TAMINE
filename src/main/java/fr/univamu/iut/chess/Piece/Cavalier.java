package fr.univamu.iut.chess.Piece;

public class Cavalier extends Piece {
        public Cavalier(Color color, String imagePath, Position position) {
            super(color, imagePath, position);
        }

        @Override
        public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
            // Implémentation du déplacement du cavalier
            return false;
        }
    }

