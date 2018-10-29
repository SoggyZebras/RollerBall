package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;
import java.util.ArrayList;
import java.util.HashMap;

public class King extends Piece {

    public  ArrayList<Location> moves = new ArrayList<>();

    public King(Location loc, char color, String type) {
        super(loc, color, type);
    }

    public ArrayList<Location> validMoves(HashMap state) {

        int row = loc.getRow();
        int col = loc.getCol();

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

    //still need to check for if the move is safe relative to other pieces.

    private void moveRight(int row, int col) {
        if (col == 0 || col == 5) {
            add(row, col + 1);
        }
        else if (col != 0 && col != 5 && col != 6 && row != 2 && row != 3 && row != 4) {
            add(row, col + 1);
        }
    }

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

    private void moveUpLeft(int row, int col) {
        if (row == 0 || col == 0 || (col == 5 && (row == 2 || row == 3 || row == 5)) || (row == 5 && (col == 3 || col == 4)))
        {
            return;
        }
        else
        {
            add(row - 1, col - 1);
        }
    }

    private void moveDownLeft(int row, int col) {
        if (row == 0 || row == 6 || (row == 1 && (col == 3 || col == 4 || col == 5)) || (col == 5 && (row == 2 || row == 3)))
        {
            return;
        }
        else
        {
            add(row + 1, col - 1);
        }
    }

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

    private void add(int row, int col) {
        moves.add(new Location(row, col));
    }
}
