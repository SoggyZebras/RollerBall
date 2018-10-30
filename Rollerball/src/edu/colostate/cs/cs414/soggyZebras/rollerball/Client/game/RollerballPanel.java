package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * creates and draws the graphics of the game
 */
public class RollerballPanel extends JPanel {

    private Map<Location,Piece> board;
    private Client client;
    private PieceDrawer pieceDrawer;

    // the index of the selected square
    // can be set to -1,-1 to unselect a square
    private int selectedSquareRow;
    private int selectedSquareCol;

    // the pixel size the squares on the board
    private int squareSide;

    // if the user hasn't selected a piece, this is null
    private Piece selectedPiece;

    // when a piece is clicked, this will be populated with the potential moves for that piece
    private ArrayList<Location> potentialMoves;

    public RollerballPanel(Game game, Client client, int gameSide) throws IOException {
        super();
        setSize(gameSide, gameSide);
        addMouseListener(new RollerballMouseListener(this));

        board = game.getBoard();
        this.client = client;
        client.setGui(this);
        this.client.initialize();
        selectedPiece = null;
        unselectSquares();

        potentialMoves = new ArrayList<>();

        this.squareSide = getWidth() / 7;

        pieceDrawer = new PieceDrawer("Rollerball/res/pieces.png", squareSide);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawPieces(g2);
        drawSelectedSquare(g2);
        drawHighlightedSquares(g2);
    }

    private void drawPieces(Graphics2D g2) {
        for (Piece p : board.values()) {
            pieceDrawer.draw(g2, p);
        }
    }

    /**
     * draws the game board (background only, no pieces)
     * @param g2 a Graphics2D object that will be used to draw
     */
    private void drawBackground(Graphics2D g2) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i == 2 || i == 3 || i == 4) && (j == 2 || j == 3 || j == 4)) {
                    g2.setColor(Color.gray);
                }
                else if ((i + j) % 2 == 0) {
                    g2.setColor(Color.white);
                }
                else {
                    g2.setColor(Color.orange);
                }
                g2.fillRect(i * squareSide, j * squareSide, squareSide, squareSide);
            }
        }
    }

    private void drawSelectedSquare(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.drawRect(selectedSquareCol * squareSide, selectedSquareRow * squareSide, squareSide, squareSide);
    }

    private void drawHighlightedSquares(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.setStroke(new BasicStroke(3));
        for (Location l : potentialMoves) {
            g2.drawRect(l.getCol() * squareSide, l.getRow() * squareSide, squareSide, squareSide);
        }
    }

    /**
     * highlight a square on the board
     * @param row
     * @param col
     */
    private void selectSquare(int row, int col) {
        if (isValidSquare(row, col)) {
            selectedSquareRow = row;
            selectedSquareCol = col;
        }
    }

    private boolean isValidSquare(int row, int col) {
        return !(row > 1 && row < 5 && col > 1 && col < 5);
    }

    private void unselectSquares() {
        selectedSquareRow = -1;
        selectedSquareCol = -1;
    }

    /**
     * this method is called when the user clicks a square on the board
     * @param x the x pixel coordinate of the click
     * @param y the y pixel coordinate of the click
     */
    public void onClick(int x, int y) {
        Location clickLoc = new Location(y / squareSide, x / squareSide);

        // if a piece has already been selected, try to make a move and update the board
        if (selectedPiece != null) {
            if (potentialMoves.contains(clickLoc)) {
                client.makeMove(selectedPiece.getLoc(), clickLoc);
                selectedPiece = null;
                unselectSquares();
                potentialMoves.clear();
            }
        }
        // select a piece if its clicked on
        else if (board.containsKey(clickLoc)) {
            selectSquare(clickLoc.row, clickLoc.col);
            selectedPiece = board.get(clickLoc);
            client.checkValidMove(selectedPiece.getLoc());
        }
        repaint();
    }

    private void updateBoard(Map<Location,Piece> map) {
        board = map;
        // update the pieces locations
        for (Location l : map.keySet()) {
            board.get(l).setLoc(l);
        }
    }

    public void updateState(Map<Location,Piece> map) {
        updateBoard(map);
        repaint();
    }

    public void updateValidMoves(ArrayList<Location> l){
        potentialMoves = l;
        repaint();
    }
}
