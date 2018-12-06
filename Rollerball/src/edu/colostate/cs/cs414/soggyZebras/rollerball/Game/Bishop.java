package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Bishop extends Piece {
    private static final long serialVersionUID = 6582222227757690L;

    public Bishop(Location l, char color, String type) {
        super(l, color, type);
    }


    /**
     * This will determine moves based on the quadrant the piece is in
     * @param state
     * @return ArrayList<Location>
     */
    public ArrayList<Location> validMoves(Map<Location, Piece> state){
        ArrayList<Location> validMoves = new ArrayList<Location>();
        int row = loc.row;
        int col = loc.col;
         // Qual 1 Rows 4-6 and columns 0-3
        if((row > 3)  && (row < 7) && (col >= 0) && (col <= 3)){
            validMoves = quadOne(state, row, col);
        }

        // Qual 2 Rows 0-3 and columns 0-2
        if((row >= 0)  && (row <= 3) && (col >= 0) && (col <= 2)){
            validMoves = quadTwo(state, row, col);
        }

        // Quad 3 Rows 0-2 and columns 3-6
        if((row >= 0)  && (row <= 2) && (col >= 3) && (col <= 6)){
            validMoves = quadThree(state, row, col);
        }

        // Quad 4 Rows 3-6 and columns 4-6
        if((row >= 3)  && (row <= 6) && (col >= 4) && (col <= 6)){
            validMoves = quadFour(state, row, col);
        }

        return validMoves;

    }



    /**
     * This will make sure that a given row and column are inbounds of the games
     * @param row
     * @param column
     * @return boolean
     */
    public boolean checkBounds(int row, int column){
        if ((row >= 0) && (row <=6) && (column >= 0) && (column <=6)){
            if((row==2 || row ==3 || row == 4) && (1 < column  && column < 5)){
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkLoc(Map<Location, Piece> state, int row, int col) {
       if (((state.get(new Location(row, col)) == null) ) || (state.get(new Location(row, col)).getColor() != color))  {
            return true;
        }
        return false;
    }

    /**
     *  This will get a list of the valid moves based on being in quadrant one
     * @param state
     * @param row
     * @param col
     * @return ArrayList<Location>
     */
    public ArrayList<Location> quadOne(Map<Location, Piece> state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        while(checkBounds(row-1, col-1) && (checkLoc(state,row-1, col-1))){
            moves.add(new Location(row-1, col-1));
            if(state.get(new Location(row-1, col-1)) != null){
                if(state.get(new Location(row-1, col-1)).getColor() != color) break;
            }
            row--;
            col--;
        }
        while(checkBounds(row-1, col+1)
                && (checkLoc( state,row-1, col+1))
                && moves.contains(new Location(3, 0))){
            moves.add(new Location(row-1, col+1));
            if(state.get(new Location(row-1, col+1)) != null){
                if(state.get(new Location(row-1, col+1)).getColor() != color) break;
            }
            row--;
            col++;
        }
        row = loc.row;
        col = loc.col;
        if(checkBounds(row+1, col+1)
                && (checkLoc( state,row+1, col+1))){
            moves.add(new Location(row+1, col+1) );
        }
        if(checkBounds(row-1, col+1)
                && (checkLoc( state,row-1, col+1))){
            moves.add(new Location(row-1, col+1) );
        }
        if(checkBounds(row+1, col-1)
                && (checkLoc( state,row+1, col-1))){
            moves.add(new Location(row+1, col-1) );
            if(row == 5 && col == 2 &&
                    (checkLoc( state,5, 1))){
                moves.add(new Location(5, 0) );
            }
        }

        if(moves.contains(new Location(5, 0))
                && (checkLoc( state,4, 1))
                && !moves.contains(new Location(4, 1))){
            moves.add(new Location(4, 1) );
        }
        return moves;
    }




    /**
     *  This will get a list of the valid moves based on being in quadrant two
     * @param state
     * @param row
     * @param col
     * @return ArrayList<Location>
     */
    public ArrayList<Location> quadTwo(Map<Location, Piece> state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        while(checkBounds(row-1, col+1) &&
                (checkLoc( state,row-1, col+1))){
            moves.add(new Location(row-1, col+1));
            if(state.get(new Location(row-1, col+1)) != null){
                if(state.get(new Location(row-1, col+1)).getColor() != color) break;
            }
            row--;
            col++;
        }
        while(checkBounds(row+1, col+1)
                && (checkLoc( state,row+1, col+1))){
            moves.add(new Location(row+1, col+1) );
            if(state.get(new Location(row+1, col+1)) != null){
                if(state.get(new Location(row+1, col+1)).getColor() != color) break;
            }
            row++;
            col++;
        }
        row = loc.row;
        col = loc.col;


        if(checkBounds(row+1, col-1)
                && (checkLoc( state,row+1, col-1))){
            moves.add(new Location(row+1, col-1) );
        }

        if(checkBounds(row+1, col+1)
                && (checkLoc( state,row+1, col+1))){
            moves.add(new Location(row+1, col+1) );
        }
        if(checkBounds(row-1, col-1)
                && (checkLoc( state,row-1, col-1))){
            moves.add(new Location(row-1, col-1) );
            if(row == 2 && col == 1
                    && (checkLoc( state,0, 1))){
                moves.add(new Location(0, 1) );
            }
        }

        if(moves.contains(new Location(0, 1))
                && (checkLoc( state,1, 2))
                && !moves.contains(new Location(1, 2))){
            moves.add(new Location(1, 2) );
        }

        return moves;
    }

    /**
     *  This will get a list of the valid moves based on being in quadrant three
     * @param state
     * @param row
     * @param col
     * @return ArrayList<Location>
     */

    public ArrayList<Location> quadThree(Map<Location, Piece> state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        while(checkBounds(row+1, col+1)
                && (checkLoc( state,row+1, col+1))){
            moves.add(new Location(row+1, col+1));
            if(state.get(new Location(row+1, col+1)) != null){
                if(state.get(new Location(row+1, col+1)).getColor() != color) break;
            }
            row++;
            col++;
        }
        while(checkBounds(row+1, col-1)
                && (checkLoc( state,row+1, col-1))
                && moves.contains(new Location(3, 6))){
            moves.add(new Location(row+1, col-1) );
            if(state.get(new Location(row+1, col-1)) != null){
                if(state.get(new Location(row+1, col-1)).getColor() != color) break;
            }
            row++;
            col--;
        }
        row = loc.row;
        col = loc.col;


        if(checkBounds(row-1, col-1)
                && (checkLoc( state,row-1, col-1))){
            moves.add(new Location(row-1, col-1) );
        }
        if(checkBounds(row+1, col-1)
                && (checkLoc( state,row+1, col-1))){
            moves.add(new Location(row+1, col-1) );
        }
        if(checkBounds(row-1, col+1)
                && (checkLoc( state,row-1, col+1))){
            moves.add(new Location(row-1, col+1) );
            if(row == 1 && col == 4 && (checkLoc( state,1, 6))){
                moves.add(new Location(1, 6) );
            }
        }
        if(moves.contains(new Location(1, 6))
                && (checkLoc( state,2, 5))
                && !moves.contains(new Location(2, 5))){
            moves.add(new Location(2, 5) );
        }
        return moves;
    }

    /**
     *  This will get a list of the valid moves based on being in quadrant  Four
     * @param state
     * @param row
     * @param col
     * @return ArrayList<Location>
     */


    public ArrayList<Location> quadFour(Map<Location, Piece> state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        while(checkBounds(row+1, col-1)
                && (checkLoc( state,row+1, col-1))){
            moves.add(new Location(row+1, col-1));
            if(state.get(new Location(row+1, col-1)) != null){
                if(state.get(new Location(row+1, col-1)).getColor() != color) break;
            }
            row++;
            col--;
        }
        while(checkBounds(row-1, col-1)
                && (checkLoc( state,row-1, col-1))
                && moves.contains(new Location(6, 3))){
            moves.add(new Location(row-1, col-1) );
            if(state.get(new Location(row-1, col-1)) != null){
                if(state.get(new Location(row-1, col-1)).getColor() != color) break;
            }
            row--;
            col--;
        }
        row = loc.row;
        col = loc.col;


        if(checkBounds(row-1, col-1)
                && (checkLoc( state,row-1, col-1))){
            moves.add(new Location(row-1, col-1) );
        }
        if(checkBounds(row-1, col+1)
                && (checkLoc( state,row-1, col+1))){
            moves.add(new Location(row-1, col+1) );
        }
        if(checkBounds(row+1, col+1)
                && (checkLoc( state,row+1, col+1))){
            moves.add(new Location(row+1, col+1) );
            if(row == 4 && col == 5 && (checkLoc( state,6, 5))){
                moves.add(new Location(6, 5) );
            }
        }
        if(moves.contains(new Location(6, 5))
                && (checkLoc( state,5, 4))
                && !moves.contains(new Location(5, 4))){
            moves.add(new Location(5, 4) );
        }
        return moves;
    }

}
