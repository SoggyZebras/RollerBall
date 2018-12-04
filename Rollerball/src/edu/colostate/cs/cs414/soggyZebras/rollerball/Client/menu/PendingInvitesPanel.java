package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay.PendingInviteListDisplay;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Invite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

public class PendingInvitesPanel extends MenuPanel {

    private DefaultListModel<PendingInviteListDisplay> pendingInvitesListModel;

    public PendingInvitesPanel(MenuGUI menuGUI) {
        super("pending_invites", menuGUI);
        refresh();
    }

    @Override
    public void refresh() {
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

        JList<PendingInviteListDisplay> pendingInvitesList = new JList<>(pendingInvitesListModel);
        pendingInvitesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(pendingInvitesList);
        listScroller.setPreferredSize(new Dimension(250, 100));
        add(listScroller);

        add(createLinkedActionButton("Accept Invite", new AcceptInviteListener(pendingInvitesList)));

        // to make sure Back is on a new line
        add(new JLabel("                                                            "));
        add(createLinkedButton("Back", "main_menu"));
    }

    class AcceptInviteListener implements ActionListener {
        private JList<PendingInviteListDisplay> pendingInvitesList;

        public AcceptInviteListener(JList<PendingInviteListDisplay> pendingInviteList) {
            this.pendingInvitesList = pendingInviteList;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PendingInviteListDisplay listInvite = pendingInvitesList.getSelectedValue();
            pendingInvitesListModel.remove(pendingInvitesListModel.indexOf(listInvite));
            getMenuGUI().revalidate();
            getMenuGUI().repaint();

            try {
                getMenuGUI().client.respondInvite(listInvite.invite.getInviter(), listInvite.invite.getInviteID());
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // notify user
            JOptionPane.showMessageDialog(getMenuGUI(),
                    "Game created with user " + listInvite.invite.getInviter() + ".\nGo to the main menu to start the game.");
        }
    }

}
