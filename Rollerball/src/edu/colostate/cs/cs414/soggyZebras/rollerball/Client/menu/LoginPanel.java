package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            // TODO: try to login, update user
            getMenuGUI().setMenu("main_menu");
        }
    }
}
