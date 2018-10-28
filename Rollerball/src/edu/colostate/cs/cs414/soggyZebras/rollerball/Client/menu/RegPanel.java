package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import java.awt.*;

/**
 * represents the registration screen
 */
public class RegPanel extends MenuPanel {

    public RegPanel(MenuGUI menuGUI) {
        super("register", menuGUI);
        // TODO: add forms for registering
        add(new TextField("username"));
        add(new TextField("email"));
        add(new TextField("password"));
        add(createLinkedButton("Register", "main_menu"));
        add(createLinkedButton("Back", "register_login"));
    }
}
