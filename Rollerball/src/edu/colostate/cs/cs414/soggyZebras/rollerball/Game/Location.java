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

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location)obj;
            return this.hashCode() == other.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {
        return row + " " + col;
    }
}
