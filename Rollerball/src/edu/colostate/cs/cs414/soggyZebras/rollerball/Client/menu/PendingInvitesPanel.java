package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay.PendingInviteListDisplay;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Invite;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PendingInvitesPanel extends MenuPanel {

    private DefaultListModel<PendingInviteListDisplay> pendingInvitesListModel;
    private PendingInviteListDisplay selectedInvite;

    public PendingInvitesPanel(MenuGUI menuGUI) {
        super("pending_invites", menuGUI);
        selectedInvite = null;
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
        pendingInvitesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedInvite = pendingInvitesList.getSelectedValue();
            }
        });

        // make it so that the selected user doesn't change even if it is refreshed
        if (selectedInvite != null) {
            pendingInvitesList.setSelectedValue(selectedInvite, true);
        }

        JScrollPane listScroller = new JScrollPane(pendingInvitesList);
        listScroller.setPreferredSize(new Dimension(250, 100));
        add(listScroller);

        add(createLinkedActionButton("Accept Invite", new AcceptInviteListener(pendingInvitesList)));

        // to make sure Back is on a new line
        add(new JLabel("                                                            "));
        add(createLinkedButton("Back", "main_menu"));
    }

    /**
     * used for testing
     * @return get the user id of the first user in the list
     */
    public int getFirstInviteID() {
        if (pendingInvitesListModel.size() > 0)
            return pendingInvitesListModel.get(0).invite.getInviteID();
        else return -1;
    }

    class AcceptInviteListener implements ActionListener {
        private JList<PendingInviteListDisplay> pendingInvitesList;

        public AcceptInviteListener(JList<PendingInviteListDisplay> pendingInviteList) {
            this.pendingInvitesList = pendingInviteList;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PendingInviteListDisplay listInvite = pendingInvitesList.getSelectedValue();
            if (listInvite == null) {
                JOptionPane.showMessageDialog(getMenuGUI(), "No invite was selected.");
                return;
            }
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
