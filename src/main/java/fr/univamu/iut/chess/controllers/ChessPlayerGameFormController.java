package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Contrôleur gérant le formulaire de saisie des noms des joueurs.
 */
public class ChessPlayerGameFormController {

    @FXML
    private TextField player1Field_firstname;

    @FXML
    private TextField player1Field_name;

    @FXML
    private TextField player2Field_firstname;

    @FXML
    private TextField player2Field_name;

    /**
     * Méthode appelée lors de la soumission du formulaire.
     * Elle récupère les noms des joueurs et les enregistre dans un fichier CSV.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'enregistrement du fichier CSV.
     */
    @FXML
    private void handleSubmitButtonAction() throws IOException {
        String player1_firstname = player1Field_firstname.getText();
        String player1_name = player1Field_name.getText();
        String player2_firstname = player2Field_firstname.getText();
        String player2_name = player2Field_name.getText();
        writeCsvFile(player1_firstname, player1_name, player2_firstname, player2_name);
    }

    /**
     * Méthode pour écrire les noms des joueurs dans un fichier CSV.
     *
     * @param player1_fn prénom du joueur 1
     * @param player1_n  nom du joueur 1
     * @param player2_fn prénom du joueur 2
     * @param player2_n  nom du joueur 2
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'écriture du fichier CSV.
     */
    private void writeCsvFile(String player1_fn, String player1_n, String player2_fn, String player2_n) throws IOException {
        String fileName = "PlayerGame_joueurs.csv"; // Définition du fichier CSV
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(player1_fn)
                    .append(',')
                    .append(player1_n)
                    .append(',')
                    .append('\n')
                    .append(player2_fn)
                    .append(',')
                    .append(player2_n)
                    .append(',')
                    .append('\n'); // Enregistrement des noms et prénoms des joueurs dans le fichier
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Charger la prochaine scène après avoir enregistré les noms
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessPlayerGame.fxml"));
        Scene firstScene = new Scene(secondSceneParent);

        Stage stage = (Stage) player1Field_firstname.getScene().getWindow();
        stage.setScene(firstScene);
        stage.centerOnScreen();
        stage.show();
    }
}
