package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateInvitePanel extends MenuPanel {
    public CreateInvitePanel(MenuGUI menuGUI) {
        super("create_invite", menuGUI);
        add(new TextField("user to invite"));
        add(createLinkedActionButton("invite", new SendInviteListener()));
        add(createLinkedButton("Back", "main_menu"));
    }

    class SendInviteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: send invite
        }
    }
}
