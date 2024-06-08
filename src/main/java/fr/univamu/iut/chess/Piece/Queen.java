package fr.univamu.iut.chess.Piece;

public class Queen extends Piece {
    // La classe Reine hérite des caractéristiques de la classe abstraite Piece.
    public Queen(Couleur color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    // On utilise l'annotation @Override et on réecrit la fonction isMoveLegal :
    // La reine possède à la fois les déplacements du fou et de la tour, on va donc les réutiliser dans le programme.
    @Override
    public boolean isMoveLegal(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        // La reine peut se déplacer comme une tour ou comme un fou

        Rook rook = new Rook(this.getColor(), this.getImagePath(), this.getPosition());
        Bishop bishop = new Bishop(this.getColor(), this.getImagePath(), this.getPosition());

        return rook.isMoveLegal(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau) ||
                bishop.isMoveLegal(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau);
    }
}
