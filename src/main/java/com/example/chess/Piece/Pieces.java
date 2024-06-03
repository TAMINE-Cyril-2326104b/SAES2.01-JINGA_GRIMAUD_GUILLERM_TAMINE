package com.example.chess.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Pieces {
    protected Couleur couleur;
    protected int x, y;
    protected ImageView imageView;

    public Pieces(Couleur couleur, int x, int y, String imagePath) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
        this.imageView = new ImageView(new Image( imagePath));
        this.imageView.setFitHeight(64); // Adjust to your preferred size
        this.imageView.setFitWidth(64);  // Adjust to your preferred size
    }

    public enum Couleur {
        BLANC,
        NOIR
    }
    public Couleur getCouleur() {
        return couleur;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public abstract boolean deplacementValide(int nouvelleX, int nouvelleY,  Pieces[][] cases);
}
