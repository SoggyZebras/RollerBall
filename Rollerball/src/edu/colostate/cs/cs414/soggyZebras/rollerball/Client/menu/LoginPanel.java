package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * represents the login screen
 */
public class LoginPanel extends MenuPanel {

    public LoginPanel(MenuGUI menuGUI) {
        super("login", menuGUI);
        // TODO: add forms for logging in
        add(new JLabel("username: "));
        add(new TextField(30));
        add(new JLabel("password: "));
        add(new TextField(30));
        add(createLinkedActionButton("Login", new LoginListener()));
        add(createLinkedButton("Back", "register_login"));
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((TextField)getComponent(1)).getText().trim();
            String password = ((TextField)getComponent(3)).getText().trim();
            try {
                getMenuGUI().client.login(username, password);
                // TODO: notify that we are waiting for a response
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // TODO: update logged in user on success
            clearTextFields();
            getMenuGUI().setMenu("main_menu");
        }
    }
}
