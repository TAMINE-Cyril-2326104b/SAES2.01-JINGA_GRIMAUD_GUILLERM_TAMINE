package fr.univamu.iut.chess.Piece;

public class Fou extends Piece {

    public Fou(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int deltaLigne = Math.abs(ligneArrivee - ligneDepart);
        int deltaColonne = Math.abs(colonneArrivee - colonneDepart);

        // Vérifier que le fou se déplace en diagonale
        if (deltaLigne == deltaColonne) {
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
