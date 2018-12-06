package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay.PastGameListDisplay;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ProfilePanel extends MenuPanel {

    public ProfilePanel(MenuGUI menuGUI) {
        super("profile", menuGUI);
    }

    @Override
    public void refresh() {
        removeAll();
        add(new JLabel("<html>Profile<br>Username: " + getMenuGUI().loggedInUser.getUsername()
                + "<br>Email: " + getMenuGUI().loggedInUser.getEmail() + "<br>Game History: </html>"));

        DefaultListModel<PastGameListDisplay> pastGameModel = new DefaultListModel();
        // load won games into list
        for (Game game : getMenuGUI().loggedInUser.getGames()) {
            if (game.getWinner() != null) {
                pastGameModel.addElement(new PastGameListDisplay(game, getMenuGUI().loggedInUser.getUsername()));
            }
        }
        JList<PastGameListDisplay> pastGameList = new JList<>(pastGameModel);
        JScrollPane listScroller = new JScrollPane(pastGameList);
        listScroller.setPreferredSize(new Dimension(250, 100));
        add(listScroller);

        add(createLinkedActionButton("Logout", new LogoutListener()));
        add(createLinkedActionButton("Unregister", new UnregisterListener()));
        add(createLinkedButton("Back", "main_menu"));
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
