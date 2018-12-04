package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.*;

public class Game implements java.io.Serializable {

    protected Map<Location,Piece> board;

    // set to 'w' or 'b' depending on who's turn it is
    //private char whosTurn;

    /**
     * create a new game with the initial game board
     */
    public Game() {
        this.board = new HashMap<>();

        // add white pieces
        addPiece(new Pawn(new Location(5, 2), 'w', "pawn"));
        addPiece(new Pawn(new Location(6, 2), 'w', "pawn"));
        addPiece(new King(new Location(5, 3), 'w', "king"));
        addPiece(new Bishop(new Location(6, 3), 'w', "bishop"));
        addPiece(new Rook(new Location(5, 4), 'w', "rook"));
        addPiece(new Rook(new Location(6, 4), 'w', "rook"));

        // add black pieces
        addPiece(new Pawn(new Location(0, 4), 'b', "pawn"));
        addPiece(new Pawn(new Location(1, 4), 'b', "pawn"));
        addPiece(new King(new Location(1, 3), 'b', "king"));
        addPiece(new Bishop(new Location(0, 3), 'b', "bishop"));
        addPiece(new Rook(new Location(0, 2), 'b', "rook"));
        addPiece(new Rook(new Location(1, 2), 'b', "rook"));
    }

    public Game(Map<Location,Piece> m) {
        this.board = m;
    }

    public void addPiece(Piece p) {
        board.put(p.loc, p);
    }

    public ArrayList<Location> validMoves(Location l){
        return board.get(l).validMoves(board);

    }

    /**
     * Function to calculate if the white pieces have won by checkmating the black king
     * @return - returns true if white piece has won
     */
    public boolean wonGameW(){

        Set<Location> allWLocs = new HashSet<Location>();
        ArrayList<Location> KingMoves = new ArrayList<Location>();
        ArrayList<Location> compare = new ArrayList<Location>();
        Location kingLoc = null;

        for(Location I :board.keySet()){
            if(board.get(I).getColor()=='w'){
                for(Location X : validMoves(I)) {
                    allWLocs.add(X);
                }
            }
            if(board.get(I).getColor()=='b'&& board.get(I).getType()=="king"){
                KingMoves = validMoves(I);
                kingLoc = I;
            }
        }

        //Now all valid black moves and white king moves are populated.

        if(KingMoves.isEmpty()&&allWLocs.contains(kingLoc)){ //case for if King has no valid moves and will be captured
            return true;
        }
        else if(KingMoves.isEmpty()&&!allWLocs.contains(kingLoc)) return false; //King is in initial position or surrounded by friendly pieces
        else {
            compare.addAll(KingMoves);
            for (Location I : KingMoves) {
                if (allWLocs.contains(I)) {
                    compare.remove(I);
                }
            }
        }
        return compare.isEmpty();

    }


    /**
     * Function to calculate if the black pieces have won by checkmating the white king
     * @return - returns true if black piece has won
     */
    public boolean wonGameB(){
        Set<Location> allBLocs = new HashSet<Location>();
        ArrayList<Location> KingMoves = new ArrayList<Location>();
        ArrayList<Location> compare = new ArrayList<Location>();

        Location kingLoc = null;

        for(Location I :board.keySet()){
            if(board.get(I).getColor()=='b'){
                for(Location X : validMoves(I)) {
                    allBLocs.add(X);
                }
            }
            if(board.get(I).getColor()=='w'&& board.get(I).getType()=="king"){
                KingMoves = validMoves(I);
                kingLoc = I;
            }
        }
        //Now all valid black moves and white king moves are populated.

        if(KingMoves.isEmpty()&&allBLocs.contains(kingLoc)){ //case for if King has no valid moves and will be captured
            return true;
        }
        else if(KingMoves.isEmpty()&&!allBLocs.contains(kingLoc)) return false; //King is in initial position or surrounded by friendly pieces
        else {
            compare.addAll(KingMoves);
            for (Location I : KingMoves) {
                if (allBLocs.contains(I)) {
                    compare.remove(I);
                }
            }
        }
        return compare.isEmpty();
    }


    /**
     * Function to determine a stalemate condition in the game where neither player has any moves
     * @return - returns true of both sets of moves are empty
     */
    public boolean stalemate(){
        Set<Location> allWLocs = new HashSet<Location>();
        Set<Location> allBLocs = new HashSet<Location>();

        for(Location I :board.keySet()){
            if(board.get(I).getColor()=='b'){
                for(Location X : validMoves(I)) {
                    allBLocs.add(X);
                }
            }
            else{
                for(Location X : validMoves(I)) {
                    allWLocs.add(X);
                }
            }
        }

        return(allWLocs.isEmpty()&&allBLocs.isEmpty()); //Case for neither player having a valid move

    }


    /**
     * Function to move a piece from starting location to new location
     * @param to - new location to move to
     * @param from - old location to move from
     * @return - returns board state
     */
    public Map<Location, Piece> makeMove(Location to, Location from){
        System.err.println(board);
        //if(board.get(from).validMoves(board).contains(to)){
            board.put((to), board.get(from));
            board.get(to).setLoc(to);
            board.put(from, null);

        //}
        return board;
    }

    /**
     * Function to get current board state of game
     * @return - returns game Board
     */
    public Map<Location, Piece> getBoard() {
        return board;
    }
}
