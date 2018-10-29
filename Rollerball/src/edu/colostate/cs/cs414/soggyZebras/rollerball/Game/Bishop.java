package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;
import java.util.ArrayList;
import java.util.HashMap;

public class Bishop extends Piece {

    Location l;
    char color;
    String type;


    public Bishop(Location l, char color, String type) {
        super(l, color, type);
        this.l = l;
        this.color = color;
        this.type = type;
    }



    public ArrayList<Location> validMoves(HashMap state){

        ArrayList<Location> moves = new ArrayList<>();
        int row = l.row;
        int col = l.col;
         // Qual 1 Rows 4-6 and columns 0-3

        if((row > 3)  && (row < 7) && (col >= 0) && (col <= 3)){
            System.out.println("Quad 1");
            while(checkBounds(row-1, col-1) && (state.get(new Location(row-1, col-1)) == null)){
                moves.add(new Location(row-1, col-1));
                row--;
                col--;
            }
            while(checkBounds(row-1, col+1)
                    && (state.get(new Location(row-1, col+1)) == null) && moves.contains(new Location(3, 0))){
                moves.add(new Location(row-1, col+1) );
                row--;
                col++;
            }
            row = l.row;
            col = l.col;
            if(checkBounds(row+1, col+1)&& (state.get(new Location(row+1, col+1)) == null)){
                moves.add(new Location(row+1, col+1) );
            }
            if(checkBounds(row-1, col+1)&& (state.get(new Location(row-1, col+1)) == null)){
                moves.add(new Location(row-1, col+1) );
            }
            if(checkBounds(row+1, col-1)&& (state.get(new Location(row+1, col-1)) == null)){
                moves.add(new Location(row+1, col-1) );
                if(row == 5 && col == 2 && (state.get(new Location(5, 0)) == null) ){
                    moves.add(new Location(5, 0) );
                }
            }
        }

        return moves;

    }

    private boolean checkBounds(int row, int column){
        if ((row >= 0) && (row <=6) && (column >= 0) && (column <=6)){
            if((row==2 || row ==3 || row == 4) && (1 < column  && column < 5)){
                return false;
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        Bishop n = new Bishop(new Location(4, 1),'w', "");
        HashMap board = new HashMap <>();
        board.put(new Location(0, 0), null);
        board.put(new Location(0, 1), null);
        board.put(new Location(0, 2), null);
        board.put(new Location(0, 3), null);
        board.put(new Location(0, 4), null);
        board.put(new Location(0, 5), null);
        board.put(new Location(0, 6), null);
        board.put(new Location(1, 0), null);
        board.put(new Location(1, 1), null);
        board.put(new Location(1, 2), null);
        board.put(new Location(1, 3), null);
        board.put(new Location(1, 4), null);
        board.put(new Location(1, 5), null);
        board.put(new Location(1, 6), null);
        board.put(new Location(2, 0), null);
        board.put(new Location(2, 1), null);
        board.put(new Location(2, 5), null);
        board.put(new Location(2, 6), null);
        board.put(new Location(3, 0), null);
        board.put(new Location(3, 1), null);
        board.put(new Location(3, 5), null);
        board.put(new Location(3, 6), null);
        board.put(new Location(4, 0), null);
        board.put(new Location(4, 1), null);
        board.put(new Location(4, 5), null);
        board.put(new Location(4, 6), null);
        board.put(new Location(5, 0), null);
        board.put(new Location(5, 1), null);
        board.put(new Location(5, 2), null);
        board.put(new Location(5, 3), null);
        board.put(new Location(5, 4), null);
        board.put(new Location(5, 5), null);
        board.put(new Location(5, 6), null);
        board.put(new Location(6, 0), null);
        board.put(new Location(6, 1), null);
        board.put(new Location(6, 2), null);
        board.put(new Location(6, 3), null);
        board.put(new Location(6, 4), null);
        board.put(new Location(6, 5), null);
        board.put(new Location(6, 6), null);
        System.out.println(n.validMoves(board));

    }

}
