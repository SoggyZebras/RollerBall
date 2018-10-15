package gui;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;

/**
 * creates and draws the graphics of the game
 */
public class RollerballPanel extends JPanel {

    // the index of the selected square
    // can be set to -1,-1 to unselect a square
    private int selectedSquareRow;
    private int selectedSquareCol;

    // the pixel width and height of the squares on the board
    private int squareWidth;
    private int squareHeight;

    public RollerballPanel(GUI gui) {
        super();
        setSize(gui.WIDTH, gui.HEIGHT);
        addMouseListener(new RollerballMouseListener(this));

        squareWidth = getWidth() / 7;
        squareHeight = getHeight() / 7;

        selectedSquareRow = -1;
        selectedSquareCol = -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
        drawSelectedSquare(g2);
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
                g2.fillRect(i * squareWidth, j * squareHeight, squareWidth, squareHeight);
            }
        }
    }

    private void drawSelectedSquare(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.drawRect(selectedSquareCol * squareWidth, selectedSquareRow * squareHeight, squareWidth, squareHeight);
    }

    /**
     * highlight a square on the board
     * @param row
     * @param col
     */
    private void selectSquare(int row, int col) {
        selectedSquareRow = row;
        selectedSquareCol = col;
    }


    /**
     * this method is called when the user clicks a square on the board
     * @param x the x pixel coordinate of the click
     * @param y the y pixel coordinate of the click
     */
    public void onClick(int x, int y) {
        selectSquare(y / squareHeight, x / squareWidth);
        repaint();
    }


}
