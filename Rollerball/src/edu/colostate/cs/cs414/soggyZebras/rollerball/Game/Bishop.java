package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;
import java.util.ArrayList;
import java.util.HashMap;

public class Bishop extends Piece {
    
    public Bishop(Location l, char color, String type) {
        super(l, color, type);
    }



    public ArrayList<Location> validMoves(HashMap state){
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
            validMoves = quadThree(state, row, col);
        }

        return validMoves;

    }



    public ArrayList<Location> quadOne(HashMap state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        System.out.println("Quad 1");
        while(checkBounds(row-1, col-1)
                && (state.get(new Location(row-1, col-1)) == null)){
            moves.add(new Location(row-1, col-1));
            row--;
            col--;
        }
        while(checkBounds(row-1, col+1)
                && (state.get(new Location(row-1, col+1)) == null)
                && moves.contains(new Location(3, 0))){
            moves.add(new Location(row-1, col+1) );
            row--;
            col++;
        }
        row = loc.row;
        col = loc.col;
        if(checkBounds(row+1, col+1)
                && (state.get(new Location(row+1, col+1)) == null)){
            moves.add(new Location(row+1, col+1) );
        }
        if(checkBounds(row-1, col+1)
                && (state.get(new Location(row-1, col+1)) == null)){
            moves.add(new Location(row-1, col+1) );
        }
        if(checkBounds(row+1, col-1)
                && (state.get(new Location(row+1, col-1)) == null)){
            moves.add(new Location(row+1, col-1) );
            if(row == 5 && col == 2 && (state.get(new Location(5, 0)) == null) ){
                moves.add(new Location(5, 0) );
            }
        }

        if(moves.contains(new Location(5, 0))
                && (state.get(new Location(4, 1)) == null)
                && !moves.contains(new Location(4, 1))){
            moves.add(new Location(4, 1) );
        }
        return moves;
    }



    public boolean checkBounds(int row, int column){
        if ((row >= 0) && (row <=6) && (column >= 0) && (column <=6)){
            if((row==2 || row ==3 || row == 4) && (1 < column  && column < 5)){
                return false;
            }
            return true;
        }
        return false;
    }

    public ArrayList<Location> quadTwo(HashMap state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        System.out.println("Quad 2");
        while(checkBounds(row-1, col+1) && (state.get(new Location(row-1, col+1)) == null)){
            moves.add(new Location(row-1, col+1));
            row--;
            col++;
        }
        while(checkBounds(row+1, col+1)
                && (state.get(new Location(row+1, col+1)) == null) && moves.contains(new Location(0, 3))){
            moves.add(new Location(row+1, col+1) );
            row++;
            col++;
        }
        row = loc.row;
        col = loc.col;


        if(checkBounds(row+1, col-1)&& (state.get(new Location(row+1, col-1)) == null)){
            moves.add(new Location(row+1, col-1) );
        }
        if(checkBounds(row+1, col+1)&& (state.get(new Location(row+1, col+1)) == null)){
            moves.add(new Location(row+1, col+1) );
        }
        if(checkBounds(row-1, col-1)&& (state.get(new Location(row-1, col-1)) == null)){
            moves.add(new Location(row-1, col-1) );
            if(row == 2 && col == 1 && (state.get(new Location(0, 1)) == null) ){
                moves.add(new Location(0, 1) );
            }
        }
        if(moves.contains(new Location(0, 1))
                && (state.get(new Location(1, 2)) == null)
                && !moves.contains(new Location(1, 2))){
            moves.add(new Location(1, 2) );
        }

        return moves;
    }

    public ArrayList<Location> quadThree(HashMap state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        System.out.println("Quad 3");
        while(checkBounds(row+1, col+1)
                && (state.get(new Location(row+1, col+1)) == null)){
            moves.add(new Location(row+1, col+1));
            row++;
            col++;
        }
        while(checkBounds(row+1, col-1)
                && (state.get(new Location(row+1, col-1)) == null)
                && moves.contains(new Location(3, 6))){
            moves.add(new Location(row+1, col-1) );
            row++;
            col--;
        }
        row = loc.row;
        col = loc.col;


        if(checkBounds(row-1, col-1)
                && (state.get(new Location(row-1, col-1)) == null)){
            moves.add(new Location(row-1, col-1) );
        }
        if(checkBounds(row+1, col-1)
                && (state.get(new Location(row+1, col-1)) == null)){
            moves.add(new Location(row+1, col-1) );
        }
        if(checkBounds(row-1, col+1)
                && (state.get(new Location(row-1, col+1)) == null)){
            moves.add(new Location(row-1, col+1) );
            if(row == 1 && col == 4 && (state.get(new Location(1, 6)) == null) ){
                moves.add(new Location(1, 6) );
            }
        }
        if(moves.contains(new Location(1, 6))
                && (state.get(new Location(2, 5)) == null)
                && !moves.contains(new Location(2, 5))){
            moves.add(new Location(2, 5) );
        }
        return moves;
    }


    public ArrayList<Location> quadFour(HashMap state, int row, int col){
        ArrayList<Location> moves = new ArrayList<>();
        System.out.println("Quad 4");
        while(checkBounds(row+1, col-1)
                && (state.get(new Location(row+1, col-1)) == null)){
            moves.add(new Location(row+1, col-1));
            row++;
            col--;
        }
        while(checkBounds(row-1, col-1)
                && (state.get(new Location(row-1, col-1)) == null)
                && moves.contains(new Location(6, 3))){
            moves.add(new Location(row-1, col-1) );
            row--;
            col--;
        }
        row = loc.row;
        col = loc.col;


        if(checkBounds(row-1, col-1)
                && (state.get(new Location(row-1, col-1)) == null)){
            moves.add(new Location(row-1, col-1) );
        }
        if(checkBounds(row-1, col+1)
                && (state.get(new Location(row-1, col+1)) == null)){
            moves.add(new Location(row-1, col+1) );
        }
        if(checkBounds(row+1, col+1)
                && (state.get(new Location(row+1, col+1)) == null)){
            moves.add(new Location(row+1, col+1) );
            if(row == 4 && col == 5 && (state.get(new Location(6, 5)) == null) ){
                moves.add(new Location(6, 5) );
            }
        }
        if(moves.contains(new Location(6, 5))
                && (state.get(new Location(5, 4)) == null)
                && !moves.contains(new Location(5, 4))){
            moves.add(new Location(5, 4) );
        }
        return moves;
    }


    public static void main(String[] args){


    }

}
