package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay.GameListDisplay;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * represents the main menu screen (current games, history, new game)
 */
public class MainMenuPanel extends MenuPanel {
    public MainMenuPanel(MenuGUI menuGUI) {
        super("main_menu", menuGUI);

    }

    @Override
    public void refresh() {
        removeAll();
        JLabel title = new JLabel("Active Games");
        add(title);

        DefaultListModel<GameListDisplay> activeGamesListModel = new DefaultListModel();

        // get this users games
        if (getMenuGUI().loggedInUser != null) {
            for (Game game : getMenuGUI().loggedInUser.getGames()) {
                activeGamesListModel.add(0, new GameListDisplay(game));
            }
        }

        // TODO: remove this soon
        Game g1 = new Game();
        Game g2 = new Game();
        activeGamesListModel.add(0, new GameListDisplay(g1));
        activeGamesListModel.add(1, new GameListDisplay(g2));

        JList<GameListDisplay> activeGamesList = new JList<>(activeGamesListModel);
        activeGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(activeGamesList);
        listScroller.setPreferredSize(new Dimension(250, 100));
        add(listScroller);

        add(createLinkedActionButton("Start Selected Game", new StartGameListener(activeGamesList)));
        add(createLinkedButton("Invite Players", "create_invite"));
        add(createLinkedButton("Pending Invites", "pending_invites"));
        add(createLinkedButton("Game History", "game_history"));
        add(createLinkedActionButton("Logout", new LogoutListener()));
        add(createLinkedActionButton("Unregister", new UnregisterListener()));
    }

    class StartGameListener implements ActionListener {
        private JList<GameListDisplay> gamesList;

        public StartGameListener(JList<GameListDisplay> gamesListModel) {
            this.gamesList = gamesListModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            GameListDisplay selected = gamesList.getSelectedValue();
            int gameID = selected.game.getGameID(); // TODO: get gameid
            getMenuGUI().openGameGUI(gameID);
        }
    }

    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int logout = JOptionPane.showOptionDialog(getMenuGUI(),
                    "Are you sure you want to logout?", "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (logout == 0) {
                try {
                    getMenuGUI().client.logout(getMenuGUI().loggedInUser.getUserID());
                }
                catch(IOException i){
                    i.printStackTrace();
                }
                getMenuGUI().setMenu("register_login");
            }
        }
    }

    class UnregisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int unreg = JOptionPane.showOptionDialog(getMenuGUI(),
                    "Are you sure you want to unregister? All of this user's data will be lost", "Unregister Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (unreg == 0) {
                // TODO: unregister user from db
                try {
                    getMenuGUI().client.deregister(getMenuGUI().loggedInUser.getUserID());
                }
                catch(IOException i){
                    i.printStackTrace();
                }
                getMenuGUI().setMenu("register_login");
            }
        }
    }
}
