package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            // TODO: send invite
            String username = ((TextField)getComponent(1)).getText().trim();
            clearTextFields();
        }
    }
}
