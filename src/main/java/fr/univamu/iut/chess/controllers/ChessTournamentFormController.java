package fr.univamu.iut.chess.controllers;

import fr.univamu.iut.chess.ChessApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Contrôleur pour le formulaire de tournoi d'échecs.
 * Gère l'ajout de joueurs au tournoi et l'enregistrement dans un fichier CSV.
 *
 * <p>
 *     Ce contrôleur gère l'ajout de joueurs au tournoi d'échecs et les enregistre
 *     dans un fichier CSV. Il permet également de naviguer entre les différentes vues.
 * </p>
 *
 * @author Votre Nom
 */
public class ChessTournamentFormController {

    @FXML
    private TextField player1Field_firstname;

    @FXML
    private TextField player1Field_name;

    @FXML
    private Label joueurTournoi;

    private int i=1;

    /**
     * Méthode appelée lors de la soumission du formulaire.
     * Redirige l'utilisateur vers une autre scène.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors du chargement de la scène.
     */
    @FXML
    public void handleSubmitButtonAction() throws IOException {
        Parent secondSceneParent = FXMLLoader.load(ChessApplication.class.getResource("fxml/ChessTournament.fxml"));
        Scene firstScene = new Scene(secondSceneParent);

        Stage stage = (Stage) player1Field_firstname.getScene().getWindow();
        stage.setScene(firstScene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Méthode appelée lors de l'ajout d'un joueur.
     * Ajoute le joueur au tournoi et l'enregistre dans un fichier CSV.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'écriture dans le fichier CSV.
     */
    @FXML
    public void handleAddPlayer() throws IOException {
        joueurTournoi.setText("Joueur " + ++i);
        String firstName = player1Field_firstname.getText().trim();
        String lastName = player1Field_name.getText().trim();
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            writeCsvFile(firstName, lastName);
            player1Field_firstname.clear();
            player1Field_name.clear();
        }
    }

    /**
     * Écrit les informations du joueur dans un fichier CSV.
     *
     * @param player1_fn Le prénom du joueur.
     * @param player1_n  Le nom du joueur.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de l'écriture dans le fichier CSV.
     */
    public void writeCsvFile(String player1_fn, String player1_n) throws IOException {
        String fileName = "TournamentGame.csv";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(player1_fn)
                    .append(',')
                    .append(player1_n)
                    .append(',')
                    .append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
