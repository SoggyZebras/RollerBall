package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.MenuGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * represents the outer window an instance of the game is shown in
 */
public class GameGUI extends JFrame {
    public final int WIDTH = 600;
    public final int HEIGHT = 700;

    public RollerballPanel panel;

    public GameGUI(Client client, Game game, MenuGUI menuGUI) throws IOException {
        // setup enclosing frame
        super("Rollerball");
        setSize(WIDTH, HEIGHT + 22);
        setResizable(false);
        setFocusable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // setup panel (what gets drawn on)
        panel = new RollerballPanel(game, client, menuGUI, WIDTH);
        add(panel);
        setVisible(true);

        // add listener for when window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // remove this game from the menus list of active games
                menuGUI.activeGameGUIs.remove(game.getGameID());
            }
        });
    }

}
