package gui;

import javax.swing.*;

/**
 * handles the window the game is shown in
 */
public class GUI {
    public final int WIDTH = 500;
    public final int HEIGHT = 500;

    private JFrame jframe;
    private RollerballPanel panel;

    public GUI() {
        // setup enclosing frame
        jframe = new JFrame("Rollerball");
        jframe.setSize(WIDTH, HEIGHT);
        jframe.setResizable(false);
        jframe.setFocusable(true);
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);

        // setup panel (what gets drawn on)
        panel = new RollerballPanel(this);
        jframe.add(panel);
    }

    /**
     * display the window
     */
    public void show() {
        jframe.setVisible(true);
    }


}
