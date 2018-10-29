package edu.colostate.cs.cs414.soggyZebras.rollerball.Game;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private Map<Location,Piece> board;

    // set to 'w' or 'b' depending on who's turn it is
    private char whosTurn;

    /**
     * create a new game
     */
    public Game() {
        board = new HashMap<>();

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

    public Game(Map<Location, Piece> m) {
        this.board = m;
    }

    protected void addPiece(Piece p) {
        board.put(p.loc, p);
    }


    public Map<Location, Piece> getBoard() {
        return board;
    }
}
