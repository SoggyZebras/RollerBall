package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.ArrayList;

public class Pawn extends Piece{

    public Pawn(Location loc, char color, String type) {
        super(loc, color, type);
    }

    public  ArrayList<Location> moves = new ArrayList<>();









    private void add(int row, int col) {
        moves.add(new Location(row, col));
    }

}
