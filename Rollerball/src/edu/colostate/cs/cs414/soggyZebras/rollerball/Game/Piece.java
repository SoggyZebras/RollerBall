package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

public class Piece {

    Location loc;
    char color;
    String type;

    public Piece (Location l, char color, String type) {
        this.loc = l;
        this.color = color;
        this.type = type;
    }

    public int getRow() { return loc.getRow(); }

    public int getCol() { return loc.getCol(); }

    public char getColor() { return color; }

    public String getType() { return type; }

    public boolean ExternalRing() {
        if (loc.getRow() == 0 || loc.getCol() == 0 || loc.getRow() == 6 || loc.getCol() ==6) { return true; }
        else { return false; }
    }

}
