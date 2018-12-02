package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class PendingInvitesPanel extends MenuPanel {

    // holds player who invited current user->button for that invite
    private HashMap<String,Component> inviteButtons;

    private JScrollPane scrollPane;

    public PendingInvitesPanel(MenuGUI menuGUI) {
        super("pending_invites", menuGUI);

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(250, 100));

        ArrayList<String> invites = new ArrayList<>();
        if (getMenuGUI().loggedInUser != null) {
            // TODO: lookup users invites, call invites.add(invitersName) on each
        }
        invites.add("temp-joe");
        invites.add("temp-bob");
        invites.add("temp-billy");


        // add all invites to the gui and create a map entry for them
        inviteButtons = new HashMap<>();
        for (String s : invites) {
            inviteButtons.put(s,
                    scrollPane.add(createLinkedActionButton("accept invite from " + s, new AcceptInviteListener(s))));
        }

        add(scrollPane);

        // to make sure Back is on a new line
        add(new JLabel("                                                            "));
        add(createLinkedButton("Back", "main_menu"));
    }

    @Override
    public void refresh(User updatedUser) {
        // TODO: update pending invites buttons
    }

    class AcceptInviteListener implements ActionListener {
        private String user;

        public AcceptInviteListener(String user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            scrollPane.remove(inviteButtons.get(user));
            getMenuGUI().revalidate();
            getMenuGUI().repaint();

            // load game is 1 if they choose no, 0 if they choose yes
            int loadGame = JOptionPane.showOptionDialog(getMenuGUI(),
                    "Game created with user " + user + ", would you like to load this game?", "Game Started",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            if (loadGame == 0) {
                // TODO: start game
            }
        }
    }

}
