package fr.univamu.iut.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La classe ChessApplication est la classe principale de l'application Chess.
 * Elle hérite de la classe Application de JavaFX et implémente la méthode start pour démarrer l'interface graphique.
 * Cette classe charge le fichier FXML de la page principale du jeu d'échecs.
 */
public class ChessApplication extends Application {

    /**
     * Méthode start de JavaFX, appelée lors du démarrage de l'application.
     *
     * @param stage L'objet Stage principal de l'application.
     * @throws IOException Si une erreur survient lors du chargement du fichier FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Chargement du fichier FXML de la page principale du jeu d'échecs
        FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("fxml/ChessMainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1520, 800); // Création de la scène et définition de sa taille
        stage.setTitle("Chess.com"); // Définition du titre de la fenêtre

        // Affichage de la scène dans la fenêtre
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Méthode principale de l'application, démarrant le programme.
     *
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        launch();
    }
}
