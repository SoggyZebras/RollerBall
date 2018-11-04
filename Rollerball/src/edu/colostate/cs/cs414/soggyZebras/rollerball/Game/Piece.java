package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Piece implements Serializable{

    protected Location loc;
    protected char color;
    protected String type;

    public Piece (Location l, char color, String type) {
        this.loc = l;
        this.color = color;
        this.type = type;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location l) {
        this.loc = l;
    }

    public int getRow() { return loc.getRow(); }

    public int getCol() { return loc.getCol(); }

    public char getColor() { return color; }

    public String getType() { return type; }

    public boolean ExternalRing() {
        if (loc.getRow() == 0 || loc.getCol() == 0 || loc.getRow() == 6 || loc.getCol() ==6) { return true; }
        else { return false; }
    }


    public ArrayList<Location> validMoves(Map<Location, Piece> state){
            return new ArrayList<Location>();
    }

    public void setLoc(Location l) {
        loc = l;
    }
}
