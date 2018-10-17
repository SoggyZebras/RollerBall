package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

public class Piece {

    int row;
    int col;
    char color;

    public Piece (Location l, char color) {
        this.row = l.getRow();
        this.col = l.getCol();
        this.color = color;
    }

    public int getRow() { return row; }

    public int getCol() { return col; }

    public char getColor() { return color; }

    public boolean ExternalRing() {
        if (row == 1 || col == 1 || row == 7 || col ==7) { return true; }
        else { return false; }
    }

}
