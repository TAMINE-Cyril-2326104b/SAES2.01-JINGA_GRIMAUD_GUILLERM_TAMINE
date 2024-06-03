package com.example.chess.Piece;

import javafx.scene.layout.GridPane;

public class Plateau {
    private Pieces[][] cases;
    private GridPane gridPane;

    public Plateau(GridPane gridPane) {
        this.gridPane = gridPane;
        cases = new Pieces[8][8];
        initialiserPieces();
    }

    private void initialiserPieces() {
        // Initialisation des pièces noires
        cases[0][0] = new Tour(Pieces.Couleur.NOIR, 0, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/tourNoire.png");
        cases[1][0] = new Cavalier(Pieces.Couleur.NOIR, 1, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/cavalierNoir.png");
        cases[2][0] = new Fou(Pieces.Couleur.NOIR, 2, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/fouNoir.png");
        cases[3][0] = new Reine(Pieces.Couleur.NOIR, 3, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/dameNoire.png");
        cases[4][0] = new Roi(Pieces.Couleur.NOIR, 4, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/roiNoir.png");
        cases[5][0] = new Fou(Pieces.Couleur.NOIR, 5, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/fouNoir.png");
        cases[6][0] = new Cavalier(Pieces.Couleur.NOIR, 6, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/cavalierNoir.png");
        cases[7][0] = new Tour(Pieces.Couleur.NOIR, 7, 0, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/tourNoire.png");
        for (int i = 0; i < 8; i++) {
            cases[i][1] = new Pion(Pieces.Couleur.NOIR, i, 1, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesNoires/pionNoir.png");
        }

        // Initialisation des pièces blanches
        cases[0][7] = new Tour(Pieces.Couleur.BLANC, 0, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/tourBlanche.png");
        cases[1][7] = new Cavalier(Pieces.Couleur.BLANC, 1, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/cavalierBlanc.png");
        cases[2][7] = new Fou(Pieces.Couleur.BLANC, 2, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/fouBlanc.png");
        cases[3][7] = new Reine(Pieces.Couleur.BLANC, 3, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/reineBlanche.png");
        cases[4][7] = new Roi(Pieces.Couleur.BLANC, 4, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/roiBlanc.png");
        cases[5][7] = new Fou(Pieces.Couleur.BLANC, 5, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/fouBlanc.png");
        cases[6][7] = new Cavalier(Pieces.Couleur.BLANC, 6, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/cavalierBlanc.png");
        cases[7][7] = new Tour(Pieces.Couleur.BLANC, 7, 7, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/tourBlanche.png");
        for (int i = 0; i < 8; i++) {
            cases[i][6] = new Pion(Pieces.Couleur.BLANC, i, 6, "https://github.com/TAMINE-Cyril-2326104b/SAES2.01-JINGA_GRIMAUD_GUILLERM_TAMINE/blob/ffc4ae1d890d9de41500e9da720f3e0d4b2837ed/src/main/resources/img/piecesBlanches/pionBlanc.png");
        }
    }

    public void afficherPlateau() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Pieces piece = cases[i][j];
                if (piece != null) {
                    gridPane.add(piece.getImageView(), i, j);
                }
            }
        }
    }

    public boolean deplacerPiece(int x, int y, int nouvelleX, int nouvelleY) {
        // Vérifier si les coordonnées sont valides
        if (x < 0 || x >= 8 || y < 0 || y >= 8 || nouvelleX < 0 || nouvelleX >= 8 || nouvelleY < 0 || nouvelleY >= 8) {
            return false;
        }

        Pieces piece = cases[x][y];
        if (piece == null) {
            return false; // Aucune pièce à déplacer
        }

        // Vérifier si le déplacement est valide pour cette pièce
        if (!piece.deplacementValide(nouvelleX, nouvelleY, cases)) {
            return false;
        }

        // Vérifier si une autre pièce occupe déjà la case de destination
        if (cases[nouvelleX][nouvelleY] != null) {
            return false;
        }

        // Déplacer la pièce
        cases[nouvelleX][nouvelleY] = piece;
        cases[x][y] = null;
        piece.setPosition(nouvelleX, nouvelleY);

        // Mettre à jour l'affichage du plateau
        afficherPlateau();

        return true;
    }
}
