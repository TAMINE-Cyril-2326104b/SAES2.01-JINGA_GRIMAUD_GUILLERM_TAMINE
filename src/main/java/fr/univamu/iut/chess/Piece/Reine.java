package fr.univamu.iut.chess.Piece;

public class Reine extends Piece {

    public Reine(Couleur color, String imagePath, Position position) {
        super(color, imagePath, position);
    }

    @Override
    public boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, Piece[][] plateau) {
        // La reine peut se déplacer comme une tour ou comme un fou

        Tour tour = new Tour(this.getColor(), this.getImagePath(), this.getPosition());
        Fou fou = new Fou(this.getColor(), this.getImagePath(), this.getPosition());

        return tour.estDeplacementValide(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau) ||
                fou.estDeplacementValide(ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, plateau);
    }
}
