module com.example.chess {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.chess to javafx.fxml;
    exports com.example.chess;
    exports com.example.chess.Piece;
    opens com.example.chess.Piece to javafx.fxml;
}