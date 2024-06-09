package fr.univamu.iut.chess.Piece;

/**
 * La classe Queen représente une pièce de Reine dans un jeu d'échecs.
 * Elle hérite des caractéristiques de la classe abstraite Piece.
 *
 * @author [Auteur(s)]
 */
public class Queen extends Piece {

    /**
     * Constructeur de la classe Queen.
     *
     * @param color     La couleur de la pièce.
     * @param imagePath Le chemin vers l'image représentant la pièce.
     * @param position  La position initiale de la pièce.
     */
    public Queen(Couleur color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    /**
     * Constructeur de la classe Queen sans spécifier le chemin de l'image.
     *
     * @param color    La couleur de la pièce.
     * @param position La position initiale de la pièce.
     */
    public Queen(Couleur color, Position position) {
        super(color, position);
    }

    /**
     * Vérifie si le déplacement de la reine est légal.
     *
     * @param ligneDepart     La ligne de départ.
     * @param colonneDepart   La colonne de départ.
     * @param ligneArrivee    La ligne d'arrivée.
     * @param colonneArrivee  La colonne d'arrivée.
     * @param plateau         Le plateau de jeu.
     * @return                Vrai si le déplacement est légal, sinon faux.
     */
    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        // La reine peut se déplacer comme une tour ou comme un fou

        // Créer une tour et un fou pour réutiliser leurs méthodes de vérification de déplacement
        Rook rook = new Rook(this.getColor(), this.getImagePath(), this.getPosition());
        Bishop bishop = new Bishop(this.getColor(), this.getImagePath(), this.getPosition());

        // Le déplacement de la reine est légal si elle peut se déplacer comme une tour ou comme un fou
        return rook.isMoveLegal(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau) ||
                bishop.isMoveLegal(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau);
    }
}
