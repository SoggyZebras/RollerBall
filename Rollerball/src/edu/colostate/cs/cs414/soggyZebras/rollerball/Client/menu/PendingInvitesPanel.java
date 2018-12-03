package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PendingInvitesPanel extends MenuPanel {

    private DefaultListModel<String> pendingInvitesListModel;

    public PendingInvitesPanel(MenuGUI menuGUI) {
        super("pending_invites", menuGUI);
        refresh(menuGUI.loggedInUser);
    }

    @Override
    public void refresh(User updatedUser) {
        removeAll();
        JLabel title = new JLabel("Pending Invites");
        add(title);

        pendingInvitesListModel = new DefaultListModel();

        // TODO: actually get active games from server
        if (getMenuGUI().loggedInUser != null) {
        }
        pendingInvitesListModel.add(0, "invite from a");
        pendingInvitesListModel.add(1, "invite from a");
        pendingInvitesListModel.add(2, "invite from a");
        JList<String> pendingInvitesList = new JList<>(pendingInvitesListModel);
        pendingInvitesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(pendingInvitesList);
        listScroller.setPreferredSize(new Dimension(250, 100));
        add(listScroller);

        add(createLinkedActionButton("Accept Invite", new AcceptInviteListener(pendingInvitesList)));

        // to make sure Back is on a new line
        add(new JLabel("                                                            "));
        add(createLinkedButton("Back", "main_menu"));
        // TODO: update pending invites buttons
    }

    class AcceptInviteListener implements ActionListener {
        private JList<String> pendingInvitesList;

        public AcceptInviteListener(JList<String> pendingInviteList) {
            this.pendingInvitesList = pendingInviteList;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String user = pendingInvitesList.getSelectedValue();
            pendingInvitesListModel.remove(pendingInvitesListModel.indexOf(user));
            // TODO: tell server that game was started
            getMenuGUI().revalidate();
            getMenuGUI().repaint();

            // load game is 1 if they choose no, 0 if they choose yes
            int loadGame = JOptionPane.showOptionDialog(getMenuGUI(),
                    "Game created with user " + user + ", would you like to load this game?", "Game Started",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (loadGame == 0) {
                // TODO: get game id
                int gameId = 0;
                getMenuGUI().openGameGUI(gameId);
            }
        }
    }

}
