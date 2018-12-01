package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.*;

public class Game implements java.io.Serializable {

    protected Map<Location,Piece> board;

    // set to 'w' or 'b' depending on who's turn it is
    private char whosTurn;

    /**
     * create a new game
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

    public boolean wonGameW(){

        Set<Location> allWLocs = new HashSet<Location>();
        ArrayList<Location> KingMoves = new ArrayList<Location>();

        for(Location I :board.keySet()){
            if(board.get(I).getColor()=='w'){
                for(Location X : validMoves(I)) {
                    allWLocs.add(X);
                }
            }
            if(board.get(I).getColor()=='b'&& board.get(I).getType()=="king"){
                KingMoves = validMoves(I);
            }
        }
        System.out.println("White King Moves:" + KingMoves.size());
        //Now all valid black moves and white king moves are populated.

        if(KingMoves.isEmpty()){ //case for if all pieces around them are the same color
            return false;
        }
        else {
            for (Location I : KingMoves) {
                if (allWLocs.contains(I)) {
                    KingMoves.remove(I);
                }
            }
        }
        return KingMoves.isEmpty();

    }

    public boolean wonGameB(){
        Set<Location> allBLocs = new HashSet<Location>();
        ArrayList<Location> KingMoves = new ArrayList<Location>();

        for(Location I :board.keySet()){
            if(board.get(I).getColor()=='b'){
                for(Location X : validMoves(I)) {
                    allBLocs.add(X);
                }
            }
            if(board.get(I).getColor()=='w'&& board.get(I).getType()=="king"){
                KingMoves = validMoves(I);
            }
        }
        //Now all valid black moves and white king moves are populated.
        System.out.println("Black King Moves:" + KingMoves.size());

        if(KingMoves.isEmpty()){ //case for if all pieces around them are the same color
            return false;
        }
        else {
            for (Location I : KingMoves) {
                if (allBLocs.contains(I)) {
                    KingMoves.remove(I);
                }
            }
        }
        return KingMoves.isEmpty();
    }

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

        return(allWLocs.isEmpty()&&allBLocs.isEmpty());

    }



    public Map<Location, Piece> makeMove(Location to, Location from){
        System.err.println(board);
        //if(board.get(from).validMoves(board).contains(to)){
            board.put((to), board.get(from));
            board.get(to).setLoc(to);
            board.put(from, null);

        //}
        return board;
    }


    public Map<Location, Piece> getBoard() {
        return board;
    }
}
