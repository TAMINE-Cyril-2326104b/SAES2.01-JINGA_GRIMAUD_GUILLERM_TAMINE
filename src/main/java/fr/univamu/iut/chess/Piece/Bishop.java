package fr.univamu.iut.chess.Piece;

public class Bishop extends Piece {

    // La classe Fou hérite des caractéristiques de la classe abstraite Piece.
    public Bishop(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    public Bishop(Couleur couleur, Position position) { super(couleur, position); }


    @Override

    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int deltaLigne = Math.abs(ligneArrivee - ligneDepart);
        int deltaColonne = Math.abs(colonneArrivee - colonneDepart);

        // Vérifier que le fou se déplace en diagonale
        if (deltaLigne == deltaColonne && deltaLigne != 0) {
            // Vérifier que toutes les cases entre le départ et l'arrivée sont vides
            int stepLigne = (ligneArrivee - ligneDepart) / deltaLigne;
            int stepColonne = (colonneArrivee - colonneDepart) / deltaColonne;

            int currentLigne = ligneDepart + stepLigne;
            int currentColonne = colonneDepart + stepColonne;

            while (currentLigne != ligneArrivee && currentColonne != colonneArrivee) {
                if (plateau[currentLigne][currentColonne] != null) {
                    return false;
                }
                currentLigne += stepLigne;
                currentColonne += stepColonne;
            }

            Piece pieceArrivee = plateau[ligneArrivee][colonneArrivee];
            return pieceArrivee == null || pieceArrivee.getColor() != this.getColor();
        }

        return false;
    }
}
