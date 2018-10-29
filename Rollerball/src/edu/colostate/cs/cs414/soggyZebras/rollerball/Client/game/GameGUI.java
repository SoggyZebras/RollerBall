package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import javax.swing.*;
import java.io.IOException;

/**
 * handles the window the game is shown in
 */
public class GameGUI extends JFrame {
    public final int WIDTH = 350;
    public final int HEIGHT = 350;

    private RollerballPanel panel;

    public GameGUI(Client client, Game game) throws IOException {
        // setup enclosing frame
        super("Rollerball");
        setSize(WIDTH, HEIGHT + 22);
        setResizable(false);
        setFocusable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // setup panel (what gets drawn on)
        panel = new RollerballPanel(game, client, WIDTH);
        add(panel);
        setVisible(true);
    }

}
