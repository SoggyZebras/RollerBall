package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import java.awt.*;

/**
 * represents the login screen
 */
public class LoginPanel extends MenuPanel {

    public LoginPanel(MenuGUI menuGUI) {
        super("login", menuGUI);
        // TODO: add forms for logging in
        add(new TextField("username"));
        add(new TextField("password"));
        add(createLinkedButton("Login", "main_menu"));
        add(createLinkedButton("Back", "register_login"));
    }
}
