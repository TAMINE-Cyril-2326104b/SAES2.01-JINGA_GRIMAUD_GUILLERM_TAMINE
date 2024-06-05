module com.example.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens fr.univamu.iut.chess to javafx.fxml;
    exports fr.univamu.iut.chess;

    exports fr.univamu.iut.chess.Piece;
    opens fr.univamu.iut.chess.Piece to javafx.fxml;

    exports fr.univamu.iut.chess.controllers;
    opens fr.univamu.iut.chess.controllers to javafx.fxml;
}