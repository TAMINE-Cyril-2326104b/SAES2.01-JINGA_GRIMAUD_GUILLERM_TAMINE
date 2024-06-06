package fr.univamu.iut.chess.Piece;

public abstract class Piece {
    private Color couleur;
    private String imagePath;
    private Position position;

    public Piece(Color couleur, String imagePath, Position position) {
        this.couleur = couleur;
        this.imagePath = imagePath;
        this.position = position;
    }

    public Color getCouleur() {
        return couleur;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] cases);
}
