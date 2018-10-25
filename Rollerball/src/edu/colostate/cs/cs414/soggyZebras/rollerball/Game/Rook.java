package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Rook extends Piece {

    public Rook(Location l, char color, String type) {
        super(l, color, type);
    }

    public ArrayList<Location> validMoves(HashMap state) {
        return new ArrayList<>();
    }

    public boolean externalRing() {
        return loc.getRow() == 0 || loc.getCol() == 0 || loc.getRow() == 6 || loc.getCol() == 6;
    }

    /**
     * get the current 'forward' direction of this piece
     * @return
     */
    public Location getForward() {
        // top of board
        if ((loc.row == 0 && loc.col < 6)
        || (loc.row == 1 && loc.col < 5 && loc.col > 0))
            return new Location(0, 1);
        // right side of board
        if ((loc.col == 6 && loc.row < 6)
            || (loc.col == 5 && loc.row < 5 && loc.row > 0))
            return new Location(1, 0);
        // bottom of board
        if ((loc.row == 6 && loc.col > 0)
            || (loc.row == 5 && loc.col > 1 && loc.col < 6))
            return new Location(0, -1);
        // left side of board
        if ((loc.col == 0 && loc.row > 0)
            || (loc.col == 1 && loc.row > 1 && loc.row < 6))
            return new Location(-1, 0);
        return new Location(0, 0);
    }
}
