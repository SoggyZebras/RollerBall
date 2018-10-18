package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import javax.swing.*;

/**
 * handles the window the game is shown in
 */
public class GameGUI extends JFrame {
    public final int WIDTH = 350;
    public final int HEIGHT = 350;

    private JFrame jframe;
    private RollerballPanel panel;

    public GameGUI() {
        // setup enclosing frame
        super("Rollerball");
        setSize(WIDTH, HEIGHT + 22);
        setResizable(false);
        setFocusable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // setup panel (what gets drawn on)
        panel = new RollerballPanel(WIDTH, HEIGHT);
        add(panel);
    }

    /**
     * display the window
     */
    public void showWindow() {
        setVisible(true);
    }


}
