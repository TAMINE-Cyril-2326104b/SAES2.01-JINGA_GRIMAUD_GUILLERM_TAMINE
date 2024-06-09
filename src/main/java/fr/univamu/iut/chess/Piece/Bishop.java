package fr.univamu.iut.chess.Piece;

/**
 * La classe Bishop représente un fou dans le jeu d'échecs.
 * Elle hérite des caractéristiques de la classe abstraite Piece.
 *
 * @author Estelle GRIMAUD
 * @author Mathis GUILLERM
 * @author Paul JINGA
 * @author Cyril TAMINE
 */
public class Bishop extends Piece {

    /**
     * Constructeur pour créer un fou avec une couleur, une image et une position initiale.
     *
     * @param couleur    La couleur du fou.
     * @param imagePath  Le chemin de l'image représentant le fou.
     * @param position   La position initiale du fou sur l'échiquier.
     */
    public Bishop(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    /**
     * Constructeur pour créer un fou avec une couleur et une position initiale.
     *
     * @param couleur    La couleur du fou.
     * @param position   La position initiale du fou sur l'échiquier.
     */
    public Bishop(Couleur couleur, Position position) {
        super(couleur, position);
    }

    /**
     * Vérifie si un déplacement est légal pour un fou.
     *
     * @param ligneDepart      La ligne de départ.
     * @param colonneDepart    La colonne de départ.
     * @param ligneArrivee     La ligne d'arrivée.
     * @param colonneArrivee   La colonne d'arrivée.
     * @param plateau          Le plateau de jeu, représenté par une matrice de pièces.
     * @return                 true si le déplacement est légal, false sinon.
     */
    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        int deltaLigne = Math.abs(ligneArrivee - ligneDepart);
        int deltaColonne = Math.abs(colonneArrivee - colonneDepart);

        // Vérifie que le fou se déplace en diagonale
        if (deltaLigne == deltaColonne && deltaLigne != 0) {
            // Vérifie que toutes les cases entre le départ et l'arrivée sont vides
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
