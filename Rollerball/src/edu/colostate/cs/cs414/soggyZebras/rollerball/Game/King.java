package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;
import java.util.ArrayList;
import java.util.Map;

public class King extends Piece {

    public ArrayList<Location> moves;
    public Map<Location, Piece> gameState;
    public ArrayList<Location> badMoves;
    public char myColor;
    public boolean otherKing = false;

    public King(Location loc, char color, String type) {
        super(loc, color, type);
        myColor=color;
    }

    /**
     *
     * @param gameState
     * @return a collection of all of the valid moves the king can make from current location.
     */
    public ArrayList<Location> validMoves(Map<Location,Piece> gameState) {

        int row = loc.getRow();
        int col = loc.getCol();
        this.gameState = gameState;
        moves = new ArrayList<>();
        if(!otherKing){
            badMoves = new ArrayList<>();
            unsafeMoves();
        }

        moveRight(row, col);
        moveLeft(row, col);
        moveUp(row, col);
        moveDown(row, col);
        moveDownLeft(row, col);
        moveDownRight(row, col);
        moveUpLeft(row, col);
        moveUpRight(row, col);

        return moves;
    }

    /**
     *
     * @param row
     * @param col
     * check if king is able to move to the right
     */
    private void moveRight(int row, int col) {
        if (col == 0 || col == 5) {
            add(row, col + 1);
        }
        else if (col != 0 && col != 5 && col != 6 && row != 2 && row != 3 && row != 4) {
            add(row, col + 1);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check if the king is able to move to the left
     */
    private void moveLeft(int row, int col) {
        if (col == 1 || col == 6)
        {
            add(row, col - 1);
        }
        else if (col != 0 && col != 1 && col != 6 && row != 2 && row != 3 && row != 4)
        {
            add(row, col - 1);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check if king is allowed to move up
     */
    private void moveUp(int row, int col) {
        if (row == 1 || row == 6)
        {
            add(row - 1, col);
        }
        else if (row != 0 && row != 1 && row != 6 && col != 2 && col != 3 && col != 4)
        {
            add(row - 1, col);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check if king is allowed to move down
     */
    private void moveDown(int row, int col) {
        if (row == 0 || row == 5)
        {
            add(row + 1, col);
        }
        else if (row != 6 && row != 0 && row != 5 && col != 2 && col != 3 && col != 4)
        {
            add(row + 1, col);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check if king is able to move diagonal to the NE
     */
    private void moveUpRight(int row, int col) {
        if (row == 0 || col == 6 || (col == 1 && (row == 3 || row == 4 || row == 5)) || (row == 5 && (col == 2 || col == 3)))
        {
            return;
        }
        else
        {
            add(row - 1, col + 1);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check if king is able to move diagonal to the NW
     */
    private void moveUpLeft(int row, int col) {
        if (row == 0 || col == 0 || (col == 5 && (row == 3 || row == 4 || row == 5)) || (row == 5 && (col == 3 || col == 4)))
        {
            return;
        }
        else
        {
            add(row - 1, col - 1);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check if king is able to move diagonal to the SW
     */
    private void moveDownLeft(int row, int col) {
        if (col == 0 || row == 6 || (row == 1 && (col == 3 || col == 4 || col == 5)) || (col == 5 && (row == 2 || row == 3)))
        {
            return;
        }
        else
        {
            add(row + 1, col - 1);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check if king is able to move diagonal to the SE
     */
    private void moveDownRight(int row, int col) {
        if(row == 6 || col == 6 || (row == 1 && (col == 1 || col == 2 || col == 3)) || (col == 1 && (row == 2 || row == 3)))
        {
            return;
        }
        else
        {
            add(row + 1, col + 1);
        }
    }

    /**
     *
     * @param row
     * @param col
     * check that there is not a current teammate in the position the king wants to move before adding the location to
     * the list of valid moves. Also checks that the location will be a safe move that does not cause a check mate to occur.
     * This will check the valid moves of all the pieces on the other team.
     */
    private void add(int row, int col) {
        Location l = new Location(row, col);

        if(!spotTakenByTeammate(l) && !(badMoves.contains(l)))
        {
            moves.add(l);
        }
    }

    /**
     *
     * @param potentialLoc
     * @return true if you have a teammate in the spot you want to move.
     */
    private boolean spotTakenByTeammate(Location potentialLoc) {
        Piece p = gameState.get(potentialLoc);
        if (p != null) {
            if (p.color == this.color) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return if the new location won't cause a check mate it is a safe move.
     */
    private void unsafeMoves() {
        ArrayList<Location> temp = new ArrayList<>();
        System.out.println(gameState.keySet());
        for(Location k: gameState.keySet()) {
            if(gameState.get(k).color != myColor && !(gameState.get(k).type.equals("king"))) {
                temp = gameState.get(k).validMoves(gameState);
                if(temp.size() != 0) {
                    for (int j = 0; j < temp.size(); j++) {
                        Location l = temp.get(j);
                        badMoves.add(l);
                    }
                }
            }
        }
    }
}