package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * utility for drawing pieces on the board
 */
public class PieceDrawer {

    private BufferedImage bishopB;
    private BufferedImage bishopW;
    private BufferedImage kingB;
    private BufferedImage kingW;
    private BufferedImage pawnB;
    private BufferedImage pawnW;
    private BufferedImage rookB;
    private BufferedImage rookW;

    private int squareSize;

    public PieceDrawer(String filepath, int squareSize) {
        try {
            this.squareSize = squareSize;
            BufferedImage piecesImage = ImageIO.read(getClass().getResource(filepath));
            int side = 333;
            bishopB = piecesImage.getSubimage(2 * side, side, side, side);
            bishopW = piecesImage.getSubimage(2 * side, 0, side, side);
            kingB = piecesImage.getSubimage(side, side, side, side);
            kingW = piecesImage.getSubimage(side, 0, side, side);
            pawnB = piecesImage.getSubimage(5 * side, side, side, side);
            pawnW = piecesImage.getSubimage(5 * side, 0, side, side);
            rookB = piecesImage.getSubimage(4 * side, side, side, side);
            rookW = piecesImage.getSubimage(4 * side, 0, side, side);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g, Piece piece) {
        if (piece instanceof Bishop) draw(g, (Bishop)piece);
        if (piece instanceof King) draw(g, (King) piece);
        if (piece instanceof Pawn) draw(g, (Pawn)piece);
        if (piece instanceof Rook) draw(g, (Rook)piece);
    }

    private void draw(Graphics2D g, Bishop piece) {
        if (piece.getColor() == 'w')
            g.drawImage(bishopW, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
        else
            g.drawImage(bishopB, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
    }

    private void draw(Graphics2D g, King piece) {
        if (piece.getColor() == 'w')
            g.drawImage(kingW, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
        else
            g.drawImage(kingB, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
    }

    private void draw(Graphics2D g, Pawn piece) {
        if (piece.getColor() == 'w')
            g.drawImage(pawnW, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
        else
            g.drawImage(pawnB, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
    }

    private void draw(Graphics2D g, Rook piece) {
        if (piece.getColor() == 'w')
            g.drawImage(rookW, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
        else
            g.drawImage(rookB, piece.getCol() * squareSize, piece.getRow() * squareSize, squareSize, squareSize, null);
    }
}
