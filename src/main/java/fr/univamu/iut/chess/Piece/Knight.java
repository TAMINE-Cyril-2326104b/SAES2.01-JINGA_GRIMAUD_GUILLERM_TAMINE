package fr.univamu.iut.chess.Piece;

/**
 * La classe Knight représente un Cavalier dans le jeu d'échecs.
 * Elle hérite des caractéristiques de la classe abstraite Piece.
 *
 * @autor Estelle GRIMAUD
 * @autor Mathis GUILLERM
 * @autor Paul JINGA
 * @autor Cyril TAMINE
 */
public class Knight extends Piece {

    /**
     * Constructeur de la classe Knight.
     *
     * @param couleur La couleur de la pièce (NOIR ou BLANC).
     * @param imagePath Le chemin de l'image représentant la pièce.
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public Knight(Couleur couleur, String imagePath, Position position) {
        super(couleur, imagePath, position);
    }

    /**
     * Constructeur de la classe Knight sans chemin d'image.
     *
     * @param couleur La couleur de la pièce (NOIR ou BLANC).
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public Knight(Couleur couleur, Position position) {
        super(couleur, position);
    }

    /**
     * Vérifie si le déplacement du Cavalier est légal.
     * Le Cavalier se déplace en formant un "L" (2 cases dans une direction et 1 case perpendiculaire).
     *
     * @param ligneDepart La ligne de départ du Cavalier.
     * @param colonneDepart La colonne de départ du Cavalier.
     * @param ligneArrivee La ligne d'arrivée du Cavalier.
     * @param colonneArrivee La colonne d'arrivée du Cavalier.
     * @param plateau L'échiquier actuel avec toutes les pièces.
     * @return true si le déplacement est légal, false sinon.
     */
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
