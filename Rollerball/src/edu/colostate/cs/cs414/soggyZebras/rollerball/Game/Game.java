package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import javax.swing.*;
import java.util.*;

public class Game implements java.io.Serializable {
    private static final long serialVersionUID = 652968509827690L;
    protected Map<Location,Piece> board;
    private User player1;
    private User player2;
    private boolean inProgress;
    private User winner;
    private User loser;
    private int gameID;

    // set to 'w' or 'b' depending on who's turn it is
    private User whosTurn;

    /**
     * create a new game with the initial game board
     */
    public Game(int id,User p1, User p2) {
        this.board = new HashMap<>();
        this.gameID = id;
        this.player1 = p1;
        this.player2 = p2;
        this.inProgress = true;
        this.whosTurn = p1;
        this.winner = this.loser = null;

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

    public Game(){}

    public Game(Map<Location,Piece> m) {
        this.board = m;
    }

    public void addPiece(Piece p) {
        board.put(p.loc, p);
    }

    public ArrayList<Location> validMoves(User p, Location l){
            return board.get(l).validMoves(board);
    }

    public ArrayList<Location> validMoves(Location loc, Map <Location,Piece> board){
        return board.get(loc).validMoves(board);
    }

    // TODO can we maybe remove this/do we need the check for which user has the turn (in other method)
    public ArrayList<Location> validMoves(Location l){
        return board.get(l).validMoves(board);
    }

    /**
     * Function to move a piece from starting location to new location
     * @param to - new location to move to
     * @param from - old location to move from
     * @return - returns board state
     */
    public Map<Location, Piece> makeMove(User p, Location to, Location from){
        if(whosTurn == p) {
            board.put((to), board.get(from));
            board.get(to).setLoc(to);
            board.remove(from);

            if (whosTurn.equals(player1)) {
                whosTurn = player2;
            } else {
                whosTurn = player1;
            }
        }

        return board;
    }

    /**
     * Function to calculate if the white pieces have won by checkmating the black king
     * @return - returns true if white piece has won
     */
    public boolean wonGameW(){

        //TODO: need a check for if the piece can capture and it is not your turn so you cannot get away in time but the king might have a move - check on clicked? user has chosen the wrong piece and cannot get away


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

        //TODO: need a check for if the piece can capture and it is not your turn so you cannot get away in time but the king might have a move - check on clicked? user has chosen the wrong piece and cannot get away

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
        else if(KingMoves.isEmpty()&&!allBLocs.contains(kingLoc)){
            //TODO: call JoptionPane with message that King is in Check
            //JOptionPane.showMessageDialog(this, "White King in Check!");
            return false; //King is in initial position or surrounded by friendly pieces
        }
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
     * Function to get current board state of game
     * @return - returns game Board
     */
    public Map<Location, Piece> getBoard() {
        return board;
    }


    public User getPlayer1(){
        return player1;
    }


    public User getPlayer2(){
        return player2;
    }


    public int getGameID(){
        return gameID;
    }


    public void setGameID(int gID){
        gameID = gID;
    }


    public User getWhosTurn(){
        return whosTurn;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public User getWinner() {
        return winner;
    }

    public User getLoser() {
        return loser;
    }
}
