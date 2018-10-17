package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

public class Location {

    public int row;
    public int col;

    public Location (int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
