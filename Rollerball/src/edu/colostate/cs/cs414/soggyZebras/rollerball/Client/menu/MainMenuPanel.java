package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * represents the main menu screen (current games, history, new game)
 */
public class MainMenuPanel extends MenuPanel {
    public MainMenuPanel(MenuGUI menuGUI) {
        super("main_menu", menuGUI);
        add(createLinkedActionButton("New Game", new StartGameListener()));

        add(createLinkedButton("Current Games", "current_games"));
        add(createLinkedButton("Game History", "game_history"));
        add(createLinkedButton("Logout", "register_login"));
        add(createLinkedButton("Unregister", "register_login"));
    }

    class StartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new GameGUI(new Client("127.0.0.1",5003), new Game());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
