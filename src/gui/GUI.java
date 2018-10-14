package gui;

import javax.swing.*;

public class GUI {
    private final int WIDTH = 400;
    private final int HEIGHT = 400;

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
        panel = new RollerballPanel(WIDTH, HEIGHT);
        jframe.add(panel);
    }

    public void show() {
        jframe.setVisible(true);
    }
}
