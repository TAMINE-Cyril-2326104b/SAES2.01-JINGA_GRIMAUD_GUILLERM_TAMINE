package fr.univamu.iut.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
public class ChessApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //Plateau plateau=new Plateau(gridPane);

        FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("fxml/ChessMainPage.fxml")); // On charge le fichier FXML.
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700); // On crée notre scène à partir du fichier FXML et on définit sa taille.
        stage.setTitle("Chess.com"); // On définit le titre de notre interface.

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}