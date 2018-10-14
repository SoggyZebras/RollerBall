package gui;

import javax.swing.*;
import java.awt.*;

public class RollerballPanel extends JPanel {

    public RollerballPanel(GUI gui) {
        super();
        setSize(gui.WIDTH, gui.HEIGHT);
        addMouseListener(new RollerballMouseListener(gui));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackground(g2);
    }

    /**
     * draws the game board (background only, no pieces)
     * @param g2 a Graphics2D object that will be used to draw
     */
    private void drawBackground(Graphics2D g2) {
        int squareWidth = getWidth() / 7;
        int squareHeight = getHeight() / 7;
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


}
