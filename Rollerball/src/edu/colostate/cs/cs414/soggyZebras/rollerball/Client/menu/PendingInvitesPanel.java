package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay.PendingInviteListDisplay;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Invite;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PendingInvitesPanel extends MenuPanel {

    private DefaultListModel<PendingInviteListDisplay> pendingInvitesListModel;

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

        // get users pending invites
        if (getMenuGUI().loggedInUser != null) {
            for (Invite invite : getMenuGUI().loggedInUser.getGotInvites()) {
                pendingInvitesListModel.add(0, new PendingInviteListDisplay(invite));
            }
        }

        // TODO: remove this soon
        Invite i1 = new Invite("bob", "joe", 0);
        Invite i2 = new Invite("bob", "joebob", 1);
        Invite i3 = new Invite("bob", "joey", 2);
        pendingInvitesListModel.add(0, new PendingInviteListDisplay(i1));
        pendingInvitesListModel.add(1, new PendingInviteListDisplay(i2));
        pendingInvitesListModel.add(2, new PendingInviteListDisplay(i3));

        JList<PendingInviteListDisplay> pendingInvitesList = new JList<>(pendingInvitesListModel);
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
        private JList<PendingInviteListDisplay> pendingInvitesList;

        public AcceptInviteListener(JList<PendingInviteListDisplay> pendingInviteList) {
            this.pendingInvitesList = pendingInviteList;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PendingInviteListDisplay invite = pendingInvitesList.getSelectedValue();
            pendingInvitesListModel.remove(pendingInvitesListModel.indexOf(invite));
            // TODO: tell server that game was started
            getMenuGUI().revalidate();
            getMenuGUI().repaint();

            // load game is 1 if they choose no, 0 if they choose yes
            int loadGame = JOptionPane.showOptionDialog(getMenuGUI(),
                    "Game created with user " + invite.invite.getInviter() + ", would you like to load this game?", "Game Started",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (loadGame == 0) {
                // TODO: get game id
                int gameId = 0;
                getMenuGUI().openGameGUI(gameId);
            }
        }
    }

}
