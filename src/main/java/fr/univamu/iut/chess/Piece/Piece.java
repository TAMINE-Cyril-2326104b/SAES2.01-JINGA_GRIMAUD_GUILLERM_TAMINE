package fr.univamu.iut.chess.Piece;

public abstract class Piece {
        private Color color;
        private String url;
        private Position position;

        public Piece(Color color, String url, Position position) {
            this.color=color;
            this.url = url;
            this.position=position;
        }


        public String getImagePath() {
            return url;
        }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee);

        public Color getCouleur(){
            return color;
        }
}