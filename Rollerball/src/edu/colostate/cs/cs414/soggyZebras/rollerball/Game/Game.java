package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game implements java.io.Serializable {

    protected Map<Location,Piece> board;
    private TCPConnection player1;
    private TCPConnection player2;
    private int gameID;

    // set to 'w' or 'b' depending on who's turn it is
    private char whosTurn;

    /**
     * create a new game
     */
    public Game(int id) {
        this.board = new HashMap<>();
        this.gameID = id;

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

    protected void addPiece(Piece p) {
        board.put(p.loc, p);
    }

    public ArrayList<Location> validMoves(Location l){
        return board.get(l).validMoves(board);

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


    public TCPConnection getPlayer1(){
        return player1;
    }


    public void setPlayer1(TCPConnection c1){
        player1 = c1;
    }


    public TCPConnection getPlayer2(){
        return player2;
    }

    public void setPlayer2(TCPConnection c2){
        player2 = c2;
    }

    public int getGameID(){
        return gameID;
    }

    public void setGameID(int gID){
        gameID = gID;
    }


}
