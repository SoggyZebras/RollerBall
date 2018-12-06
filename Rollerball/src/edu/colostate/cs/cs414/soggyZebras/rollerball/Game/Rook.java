package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.ArrayList;
import java.util.Map;

public class Rook extends Piece {
    private static final long serialVersionUID = 6333398267757690L;

    private final Location up = new Location(-1, 0);
    private final Location down = new Location(1, 0);
    private final Location left = new Location(0, -1);
    private final Location right = new Location(0, 1);

    public Rook(Location l, char color, String type) {
        super(l, color, type);
    }

    public ArrayList<Location> validMoves(Map<Location,Piece> gameState) {
        ArrayList<Location> validMoves = new ArrayList<>();

        // forward only, left, right, and back moves can be made on both rings
        addIfValid(getLeft(), validMoves, gameState);
        addIfValid(getRight(), validMoves, gameState);
        addIfValid(getBackward(), validMoves, gameState);

        // add all possible forward only moves (until this piece hits a wall)
        Location fwd = getForward();
        MoveAttemptResult result = null;
        for (int i = 1; i < 8; i++) {
            Location move = new Location(fwd.row * i, fwd.col * i);
            result = addIfValid(move, validMoves, gameState);
            // if we couldn't move to the checked Location or we captured an enemy, stop looping
            if (!result.wasValidSpot || result.friendlyInWay || result.capturedEnemy) break;
        }

        // add bounces if on the external ring and didn't run into a teammate/capture an enemy
        if (externalRing() && !result.friendlyInWay && !result.capturedEnemy) {
            // get wall hit location
            Location wallHit = getWallHit(fwd);
            // add right moves from that location
            for (int i = 1; i < 8; i++) {
                Location right = getRight();
                Location move = new Location(wallHit.row - loc.row + (right.row * i), wallHit.col - loc.col + (right.col * i));
                result = addIfValid(move, validMoves, gameState);
                // if we couldn't move to the checked Location, stop looping
                if (!result.wasValidSpot || result.friendlyInWay || result.capturedEnemy) break;
            }
        }

        return validMoves;
    }

    /**
     * @param fwdDir the forward direction for this piece
     * @return the Location of where this piece would hit a wall
     */
    private Location getWallHit(Location fwdDir) {
        Location wallHit = loc;
        // loop, setting the wall hit location to the spot we're checking until the spot we check is on the board
        for (int i = 1; i < 8; i++) {
            Location checkingLoc = new Location(loc.row + fwdDir.row * i, loc.col + fwdDir.col * i);
            if (isOnBoard(checkingLoc)) {
                wallHit = checkingLoc;
            }
        }
        return wallHit;
    }

    /**
     * add a new Location of position + direction to the given list of valid moves
     * if the location is on the board and not occupied by a friendly piece
     * @param direction the direction of the move
     * @param validMoves the list of valid moves to add to if the given move is valid
     * @param gameState a HashMap of Location -> Piece that represents the board
     * @return the result of the move attempt
     */
    private MoveAttemptResult addIfValid(Location direction, ArrayList<Location> validMoves, Map<Location,Piece> gameState) {
        Location potentialLoc = new Location(loc.row + direction.row, loc.col + direction.col);
        if (isOnBoard(potentialLoc)) {
            // spot has an enemy
            if (spotTakenByEnemy(potentialLoc, gameState)) {
                validMoves.add(potentialLoc);
                return new MoveAttemptResult(true, false, true);
            }
            // spot has a teammate
            else if(spotTakenByTeammate(potentialLoc, gameState)) {
                return new MoveAttemptResult(true, true, false);
            }
            // spot is empty
            else {
                validMoves.add(potentialLoc);
                return new MoveAttemptResult(true, false, false);
            }
        }
        // spot was not on board
        return new MoveAttemptResult(false, false, false);
    }

    /**
     * @param potentialLoc the Location to check
     * @param gameState a HashMap of Location -> Piece that represents the board
     * @return true if the given Location is taken by a teammate
     */
    private boolean spotTakenByTeammate(Location potentialLoc, Map<Location,Piece> gameState) {
        Piece p = gameState.get(potentialLoc);
        if (p != null) {
            if (p.color == this.color) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param potentialLoc the Location to check
     * @param gameState a HashMap of Location -> Piece that represents the board
     * @return true if the given Location is taken by an enemy
     */
    private boolean spotTakenByEnemy(Location potentialLoc, Map<Location,Piece> gameState) {
        Piece p = gameState.get(potentialLoc);
        if (p != null) {
            if (p.color != this.color) {
                return true;
            }
        }
        return false;
    }
    /**
     * @return true if this piece is on the outer ring of the board
     */
    private boolean externalRing() {
        return loc.getRow() == 0 || loc.getCol() == 0 || loc.getRow() == 6 || loc.getCol() == 6;
    }

    /**
     * @param l a Location to check
     * @return true if the given location is on the board
     */
    private boolean isOnBoard(Location l) {
        return (l.row >= 0 && l.row <= 6 && l.col >= 0 && l.col <= 6)
                && !((l.row == 2 || l.row == 3 || l.row == 4) && (l.col == 2 || l.col == 3 || l.col == 4));
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

    /**
     * used to store the result of a move attempt
     */
    class MoveAttemptResult {
        boolean wasValidSpot;
        boolean friendlyInWay;
        boolean capturedEnemy;

        public MoveAttemptResult(boolean wasValidSpot, boolean friendlyInWay, boolean capturedEnemy) {
            this.wasValidSpot = wasValidSpot;
            this.friendlyInWay = friendlyInWay;
            this.capturedEnemy = capturedEnemy;
        }

    }
}
