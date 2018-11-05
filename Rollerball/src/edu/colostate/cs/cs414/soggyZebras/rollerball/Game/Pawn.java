package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Map;

public class Pawn extends Piece{

    public Pawn(Location loc, char color, String type) {
        super(loc, color, type);
        quadrant = setQuadrant(); //needs to update every time validMoves is called or once in constructor?

    }

    public  ArrayList<Location> moves = new ArrayList<>();

    public ArrayList<Location> validMoves(Map<Location, Piece> state){
        quadrant = setQuadrant(); //needs to update every time validMoves is called or once in constructor?

        if(quadrant == 1){moveLeft(loc.getRow(),loc.getCol(),state);}
        else if(quadrant == 2){moveUp(loc.getRow(),loc.getCol(),state);}
        else if(quadrant == 3){moveRight(loc.getRow(),loc.getCol(),state);}
        else if(quadrant == 4){moveDown(loc.getRow(),loc.getCol(),state);}
        //else {throw new java.lang.RuntimeException("Invalid quadrant for pawn piece");}

        return moves;
    }

    private int quadrant;

    //Only viable move will be "forward" relative to a specific quadrant:

    private void moveUp(int row, int col,Map<Location, Piece> state){

        if(col ==0){//case for outer ring
            if(checkFriendly(state,row-1,col))
                //returns true if friend piece not in that position
                add(row - 1, col);
            if (checkFriendly(state,row-1,col+1))
                add(row - 1, col + 1);
        }

        else if(col==1 && row==2){//case for only condition where pawn can move to 3 potential spaces
            if(checkFriendly(state,row-1,col))
                add(row - 1, col);
            if(checkFriendly(state,row-1,col-1))
                add(row - 1, col - 1);
            if(checkFriendly(state,row-1,col+1))
                add(row - 1, col + 1);
        }

        else{//inside ring but not special case
            if(checkFriendly(state,row-1,col))
                add(row - 1, col);
            if(checkFriendly(state,row-1,col-1))
                add(row - 1, col - 1);
        }
    }

    private void moveLeft(int row, int col,Map<Location, Piece> state){
        if(row ==6){//case for outer ring
            if(checkFriendly(state,row,col-1))
                add(row, col - 1);
            if(checkFriendly(state,row-1,col-1))
                add(row - 1, col - 1);
        }

        else if(row ==5 && col == 2){//case for only condition where pawn can move to 3 potential spaces
            if(checkFriendly(state,row,col-1))
                add(row, col - 1);
            if(checkFriendly(state,row+1,col-1))
                add(row + 1, col - 1);
            if (checkFriendly(state,row-1,col-1))
                add(row - 1, col - 1);
        }

        else{//inside ring but not special case
            if(checkFriendly(state,row,col-1))
                add(row, col - 1);
            if (checkFriendly(state,row+1,col-1))
                add(row + 1, col - 1);
        }
    }

    private void moveRight(int row, int col,Map<Location, Piece> state){
        if(row ==0){
            if(checkFriendly(state,row,col+1))
                add(row, col + 1);
            if(checkFriendly(state,row+1,col+1))
                add(row + 1, col + 1);
        }

        else if(row ==1 && col == 4){
            if(checkFriendly(state,row,col+1))
                add(row, col + 1);
            if (checkFriendly(state,row-1,col+1))
                add(row - 1, col + 1);
            if (checkFriendly(state,row+1,col+1))
                add(row + 1, col + 1);
        }
        else{
            if(checkFriendly(state,row,col+1))
                add(row, col + 1);
            if(checkFriendly(state,row-1,col+1))
                add(row - 1, col + 1);
        }
    }

    private void moveDown(int row, int col,Map<Location, Piece> state){
        if(col ==6){//case for outer ring
            if(checkFriendly(state,row+1,col))
                add(row + 1, col);
            if(checkFriendly(state,row+1,col-1))
                add(row + 1, col - 1);
        }

        else if(col==5 && row==4){//case for only condition where pawn can move to 3 potential spaces
            if(checkFriendly(state,row+1,col))
                add(row + 1, col);
            if(checkFriendly(state,row+1,col-1))
                add(row + 1, col - 1);
            if(checkFriendly(state,row+1,col+1))
                add(row + 1, col + 1);
        }

        else{//inside ring but not special case
            if(checkFriendly(state,row+1,col))
                add(row + 1, col);
            if(checkFriendly(state,row+1,col+1))
                add(row + 1, col + 1);
        }
    }


    public int getQuadrant(){return quadrant;}

    private int setQuadrant(){
        //Sets piece quadrant from current position - Quads are numbered starting at 1 for bottom and rotating clockwise

        //top or bottom
        if(loc.getRow()<=1){
            if((ExternalRing()&&loc.getCol()<=5) || (loc.getCol()>=1&&loc.getCol()<=4))
                return 3; //Quadrant is top side - black starting quad
        }

        else if(loc.getRow()>=5) {
            if((ExternalRing()&&loc.getCol()>=1) || (loc.getCol()>=2&&loc.getCol()<=5))
                return 1; //Quadrant is bottom side- white starting quad
        }

        //left or right
        else if(loc.getCol()<=1 ){
            if((ExternalRing()&&loc.getRow()>=1) || (loc.getRow()>=2&&loc.getRow()<=5))
                return 2; //Quadrant is left side
        }

        else if(loc.getCol()>=5 ){
            if((ExternalRing()&&loc.getRow()<=5) || (loc.getRow()>=1&&loc.getRow()<=4))
                return 4; //Quadrant is right side
        }
        //Quad not set with valid int
        throw new java.lang.RuntimeException("Quadrant was not set for pawn - invalid location sent");
    }

    private void add(int row, int col) {
        moves.add(new Location(row, col));
    }

    private boolean checkFriendly(Map<Location, Piece> state, int row, int col){
        Location loc = new Location(row, col);

        if(!state.containsKey(loc)){
            return true;
        }
        else if(state.get(loc).getColor()==this.getColor()){ //case for if piece in that position is the same color
            return false;
        }
        return true;
    }
}
