package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Rook extends Piece {
    private final Location up = new Location(-1, 0);
    private final Location down = new Location(1, 0);
    private final Location left = new Location(0, -1);
    private final Location right = new Location(0, 1);

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
     * get the current 'forward' direction of this piece (depending on where it is on the board)
     * @return a Location holding the direction of 'forward'
     */
    public Location getForward() {
        // top of board
        if ((loc.row == 0 && loc.col < 6)
        || (loc.row == 1 && loc.col < 5 && loc.col > 0))
            return right;
        // right side of board
        if ((loc.col == 6 && loc.row < 6)
            || (loc.col == 5 && loc.row < 5 && loc.row > 0))
            return down;
        // bottom of board
        if ((loc.row == 6 && loc.col > 0)
            || (loc.row == 5 && loc.col > 1 && loc.col < 6))
            return left;
        // left side of board
        if ((loc.col == 0 && loc.row > 0)
            || (loc.col == 1 && loc.row > 1 && loc.row < 6))
            return up;
        return new Location(0, 0);
    }

    /**
     * get the current 'left' direction of this piece (depending on where it is on the board)
     * @return a Location holding the direction of 'left'
     */
    public Location getLeft() {
        Location fwd = getForward();
        if (fwd.equals(right))
            return up;
        if (fwd.equals(left))
            return down;
        if (fwd.equals(up))
            return left;
        if (fwd.equals(down))
            return right;
        return new Location(0, 0);
    }

    /**
     * get the current 'right' direction of this piece (depending on where it is on the board)
     * @return a Location holding the direction of 'right'
     */
    public Location getRight() {
        Location left = getLeft();
        return new Location(-left.row, -left.col);
    }

    /**
     * get the current 'backward' direction of this piece (depending on where it is on the board)
     * @return a Location holding the direction of 'backward'
     */
    public Location getBackward() {
        Location fwd = getForward();
        return new Location(-fwd.row, -fwd.col);
    }
}
