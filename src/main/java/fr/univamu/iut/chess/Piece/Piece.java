package fr.univamu.iut.chess.Piece;

public abstract class Piece {
        private String couleur;
        private String url;

        public Piece(String couleur, String imagePath) {
            this.couleur = couleur;
            this.url = imagePath;
        }


        public String getImageView() {
            return url;
        }

        public abstract boolean estDeplacementValide(int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee);
    }