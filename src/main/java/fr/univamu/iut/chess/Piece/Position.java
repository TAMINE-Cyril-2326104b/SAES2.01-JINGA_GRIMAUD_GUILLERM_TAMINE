package fr.univamu.iut.chess.Piece;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col){
        this.col=col;
        this.row=row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
