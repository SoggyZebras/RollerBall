package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.testgames.TwoRooks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CurrentGamesPanel extends MenuPanel {
    public CurrentGamesPanel(MenuGUI menuGUI) {
        super("current_games", menuGUI);
        // TODO: this is temporary, this menu should be populated with games stored on database
        add(createLinkedActionButton("saved game 1", new ResumeGameListener()));
    }

    class ResumeGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // TODO: this should load a game from the server, not create a new one
                new GameGUI(new Client("127.0.0.1",5003), new TwoRooks());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
