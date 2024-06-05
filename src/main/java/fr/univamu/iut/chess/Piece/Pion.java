package fr.univamu.iut.chess.Piece;


public class Pion extends Piece {
    public Pion(String couleur, String imagePath) {
        super(couleur, imagePath);
    }


    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee) {
        // Logique de déplacement des pions
        return false;
    }
}


