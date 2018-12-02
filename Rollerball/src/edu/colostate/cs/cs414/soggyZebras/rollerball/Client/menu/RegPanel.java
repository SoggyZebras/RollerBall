package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * represents the registration screen
 */
public class RegPanel extends MenuPanel {

    public RegPanel(MenuGUI menuGUI) {
        super("register", menuGUI);
        // TODO: add forms for registering
        add(new JLabel("username: "));
        add(new TextField(30));
        add(new JLabel("email: "));
        add(new TextField(30));
        add(new JLabel("password: "));
        add(new TextField(30));
        add(createLinkedActionButton("Register", new RegisterListener()));
        add(createLinkedButton("Back", "register_login"));
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((TextField)getComponent(1)).getText().trim();
            String email = ((TextField)getComponent(3)).getText().trim();
            String password = ((TextField)getComponent(5)).getText().trim();
            try {
                getMenuGUI().client.register(username, password, email);
                // TODO: notify that we are waiting for a response
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            clearTextFields();
            // TODO: update logged in user on success
            getMenuGUI().setMenu("main_menu");
        }
    }
}
