package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class PendingInvitesPanel extends MenuPanel {

    // holds player who invited current user->button for that invite
    private HashMap<String,Component> inviteButtons;

    public PendingInvitesPanel(MenuGUI menuGUI) {
        super("pending_invites", menuGUI);


        // TODO: get list of games player has been invited to, maybe invites they've sent too
        ArrayList<String> invites = new ArrayList<>();
        invites.add("joe");
        invites.add("bob");
        invites.add("billy");

        // add all invites to the gui and create a map entry for them
        inviteButtons = new HashMap<>();
        for (String s : invites) {
            inviteButtons.put(s,
                    add(createLinkedActionButton("accept invite from " + s, new AcceptInviteListener(s))));
        }

        // to make sure Back is on a new line
        add(new JLabel("                                                            "));
        add(createLinkedButton("Back", "main_menu"));
    }


    class AcceptInviteListener implements ActionListener {
        private String user;

        public AcceptInviteListener(String user) {
            this.user = user;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.err.println("accepted invite");
            remove(inviteButtons.get(user));
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
