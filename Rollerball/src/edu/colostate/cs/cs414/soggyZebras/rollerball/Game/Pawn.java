package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.ArrayList;
import java.util.Map;

public class Pawn extends Piece{

    public Pawn(Location loc, char color, String type) {
        super(loc, color, type);
        quadrant = setQuadrant();
    }

    public  ArrayList<Location> moves = new ArrayList<>();

    @Override
    public ArrayList<Location> validMoves(Map<Location, Piece> state){
        //System.out.println("VM LOC: " +loc.getRow()+" ,"+loc.getCol());
        //System.out.println("QUADRANT OLD: "+ quadrant);
        quadrant = setQuadrant();

        if(!moves.isEmpty()){
            moves.clear();
        }
        if(quadrant == 1){moveLeft(loc.getRow(),loc.getCol(),state);}
        else if(quadrant == 2){moveUp(loc.getRow(),loc.getCol(),state);}
        else if(quadrant == 3){moveRight(loc.getRow(),loc.getCol(),state);}
        else if(quadrant == 4){moveDown(loc.getRow(),loc.getCol(),state);}
        //else {throw new java.lang.RuntimeException("Invalid quadrant for pawn piece");}

        return moves;
    }

    private int quadrant= 0;

    //Only viable move will be "forward" relative to a specific quadrant:

    private void moveUp(int row, int col,Map<Location, Piece> state){

        if(col ==0){//case for outer ring
                //returns true if friend piece not in that position
                add( row - 1, col, state);
                add(row - 1, col + 1, state);
        }

        else if(col==1 && row==2){//case for only condition where pawn can move to 3 potential spaces
                add(row - 1, col, state);
                add(row - 1, col - 1, state);
                add(row - 1, col + 1, state);
        }

        else{//inside ring but not special case
                add(row - 1, col, state);
                add(row - 1, col - 1, state);
        }

    }

    private void moveLeft(int row, int col,Map<Location, Piece> state){
        if(row ==6){//case for outer ring
                add(row, col - 1, state);
                add(row - 1, col - 1, state);
        }

        else if(row ==5 && col == 2){//case for only condition where pawn can move to 3 potential spaces
                add(row, col - 1, state);
                add(row + 1, col - 1, state);
                add(row - 1, col - 1, state);
        }

        else{//inside ring but not special case
                add(row, col - 1, state);
                add(row + 1, col - 1, state);
        }

    }

    private void moveRight(int row, int col,Map<Location, Piece> state){
        if(row ==0){
                add(row, col + 1, state);
                add(row + 1, col + 1, state);
        }

        else if(row ==1 && col == 4){
                add(row, col + 1, state);
                add(row - 1, col + 1, state);
                add(row + 1, col + 1, state);
        }
        else{
                add(row, col + 1, state);
                add(row - 1, col + 1, state);
        }

    }

    private void moveDown(int row, int col,Map<Location, Piece> state){
        if(col ==6){//case for outer ring
                add(row + 1, col, state);
                add(row + 1, col - 1, state);
        }

        else if(col==5 && row==4){//case for only condition where pawn can move to 3 potential spaces
                add(row + 1, col, state);
                add(row + 1, col - 1, state);
                add(row + 1, col + 1, state);
        }

        else{//inside ring but not special case
                add(row + 1, col, state);
                add(row + 1, col + 1, state);
        }

    }


    public int getQuadrant(){return quadrant;}

    private int setQuadrant(){
        //Sets piece quadrant from current position - Quads are numbered starting at 1 for bottom and rotating clockwise
        Location locCur = super.getLoc();

        //top or bottom
        if(locCur.getRow()<=1){
            if((ExternalRing()&&locCur.getCol()<=5) || (locCur.getCol()>=1&&locCur.getCol()<=4)) {
                //System.out.println("SET QUADRANT 3- TOP LOC: ROW: " + locCur.getRow() + " COL: " + locCur.getCol() + "\n");
                return 3; //Quadrant is top side - black starting quad
            }
        }

        if(locCur.getRow()>=5) {
            if((ExternalRing()&&locCur.getCol()>=1) || (!ExternalRing()&&locCur.getCol()>=2&&locCur.getCol()<=5)) {
                //System.out.println("SET QUADRANT 1- BOTTOM LOC: ROW: " + locCur.getRow() + " COL: " + locCur.getCol() + "\n");
                return 1; //Quadrant is bottom side- white starting quad
            }
        }

        //left or right
        if(locCur.getCol()<=1 ){
            if((ExternalRing()&&locCur.getRow()>=1) || (!ExternalRing()&&locCur.getRow()>=2&&locCur.getRow()<=5)) {
                //System.out.println("SET QUADRANT 2- LEFT LOC: ROW: " + locCur.getRow() + " COL: " + locCur.getCol() + "\n");
                return 2; //Quadrant is left side
            }
        }

        if(locCur.getCol()>=5 ){
            if((ExternalRing()&&locCur.getRow()<=5) || (!ExternalRing()&&locCur.getRow()>=1&&locCur.getRow()<=4)) {
                //System.out.println("SET QUADRANT 4- RIGHT LOC: ROW: " + locCur.getRow() + " COL: " + locCur.getCol() + "\n");
                return 4; //Quadrant is right side
            }
        }
        //Quad not set with valid int
        //System.out.println("QUADRANT NOT SET!!!");
        throw new java.lang.RuntimeException("Quadrant was not set for pawn - invalid location sent");
    }

    @Override
    public void setLoc(Location l) {
        this.loc = l;
        this.quadrant = setQuadrant();
        //System.out.println("QUADRANT NEW: "+ quadrant);

    }

        private void add(int row, int col, Map<Location, Piece> state) {
        int curRow = this.getRow();
        int curCol = this.getCol();

        if(checkFriendly(state, row, col)){
            moves.add(new Location(row, col));
        }
    }

    private boolean checkFriendly(Map<Location, Piece> state, int row, int col){
        Location loc = new Location(row, col);

        if(!state.containsKey(loc)){
            return true;
        }
        else if(state.get(loc).getColor()==this.getColor()||(this.getLoc()==loc)){ //case for if piece in that position is the same color
            return false;
        }
        return true;
    }
}
