package com.example.chess.Piece;

import javafx.scene.layout.GridPane;

public class Jeu {
    private Plateau plateau;
    private Joueur joueurBlanc;
    private Joueur joueurNoir;
    private boolean tourJoueurBlanc;

    private GridPane grid;

    public Jeu() {
        // Initialise le plateau et les joueurs
        plateau = new Plateau(grid);
        joueurBlanc = Joueur.BLANC;
        joueurNoir = Joueur.NOIR;
        tourJoueurBlanc = true; // Commence avec les Blancs
        initialiserPartie();
    }

    private void initialiserPartie() {
        // Initialise le plateau avec les pièces
        plateau.afficherPlateau();
    }

    public void jouerTour(int x, int y, int nouvelleX, int nouvelleY) {
        // Vérifie si c'est le tour du joueur blanc ou noir
        Joueur joueurActuel = (tourJoueurBlanc) ? joueurBlanc : joueurNoir;

        // Vérifie si le déplacement est valide et effectue le déplacement
        if (plateau.deplacerPiece(x, y, nouvelleX, nouvelleY)) {
            // Déplacement réussi, change de joueur
            tourJoueurBlanc = !tourJoueurBlanc;
        }

    }
    public enum Joueur {
        BLANC,
        NOIR
    }
}

