package fr.univamu.iut.chess.Piece;

/**
 * La classe abstraite Piece représente une pièce dans le jeu d'échecs.
 * Toutes les pièces du jeu doivent hériter de cette classe.
 *
 * @author Estelle GRIMAUD
 * @author Mathis GUILLERM
 * @author Paul JINGA
 * @author Cyril TAMINE
 */
public abstract class Piece {
    private Couleur color;
    private String imagePath;
    private Position position;

    /**
     * Constructeur de la classe Piece.
     *
     * @param color La couleur de la pièce (NOIR ou BLANC).
     * @param imagePath Le chemin de l'image représentant la pièce.
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public Piece(Couleur color, String imagePath, Position position) {
        this.color = color;
        this.imagePath = imagePath;
        this.position = position;
    }

    /**
     * Constructeur de la classe Piece sans chemin d'image.
     *
     * @param color La couleur de la pièce (NOIR ou BLANC).
     * @param position La position initiale de la pièce sur l'échiquier.
     */
    public Piece(Couleur color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Renvoie la couleur de la pièce.
     *
     * @return La couleur de la pièce (NOIR ou BLANC).
     */
    public Couleur getColor() {
        return color;
    }

    /**
     * Renvoie le chemin de l'image représentant la pièce.
     *
     * @return Le chemin de l'image représentant la pièce.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Renvoie la position actuelle de la pièce sur l'échiquier.
     *
     * @return La position de la pièce.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Définit une nouvelle position pour la pièce sur l'échiquier.
     *
     * @param position La nouvelle position de la pièce.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Vérifie si le déplacement de la pièce est légal.
     *
     * @param ligneDepart La ligne de départ de la pièce.
     * @param colonneDepart La colonne de départ de la pièce.
     * @param ligneArrivee La ligne d'arrivée de la pièce.
     * @param colonneArrivee La colonne d'arrivée de la pièce.
     * @param plateau L'échiquier actuel avec toutes les pièces.
     * @return true si le déplacement est légal, false sinon.
     */
    public abstract boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau);
}
