package fr.univamu.iut.chess.Piece;

public abstract class Piece {
    private Couleur color;
    private String imagePath;
    private Position position;

    public Piece(Couleur color, String imagePath, Position position) {
        this.color = color;
        this.imagePath = imagePath;
        this.position = position;
    }

    public Couleur getColor() {
        return color;
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

    public abstract boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau);
}
