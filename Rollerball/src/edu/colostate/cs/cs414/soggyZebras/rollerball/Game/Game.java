package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game implements java.io.Serializable {

    protected Map<Location,Piece> board;
    private User player1;
    private User player2;
    private int gameID;

    // set to 'w' or 'b' depending on who's turn it is
    private char whosTurn;

    /**
     * create a new game
     */
    public Game(int id,User p1, User p2) {
        this.board = new HashMap<>();
        this.gameID = id;
        this.player1 = p1;
        this.player2 = p2;

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

    // TODO: just added this so it compiles, it is used in RookTest. It should probably return a starting board state
    public Game() {

    }

    public Game(Map<Location,Piece> m) {
        this.board = m;
    }

    protected void addPiece(Piece p) {
        board.put(p.loc, p);
    }

    public ArrayList<Location> validMoves(Location l){
        return board.get(l).validMoves(board);

    }

    public Map<Location, Piece> makeMove(Location to, Location from){
        board.put((to), board.get(from));
        board.get(to).setLoc(to);
        board.remove(from);
        return board;
    }

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


}
