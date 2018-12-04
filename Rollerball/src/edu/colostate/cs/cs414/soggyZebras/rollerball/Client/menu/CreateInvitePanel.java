package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateInvitePanel extends MenuPanel {
    public CreateInvitePanel(MenuGUI menuGUI) {
        super("create_invite", menuGUI);
        add(new JLabel("user to invite"));
        add(new TextField(30));
        add(createLinkedActionButton("invite", new SendInviteListener()));
        add(createLinkedButton("Back", "main_menu"));
    }

    class SendInviteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((TextField)getComponent(1)).getText().trim();
            try {
                if (username.equals(getMenuGUI().loggedInUser.getUsername())) {
                    JOptionPane.showMessageDialog(getMenuGUI(), "You cannot send an invite to yourself.");
                }
                else {
                    getMenuGUI().client.sendInvite(username);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
