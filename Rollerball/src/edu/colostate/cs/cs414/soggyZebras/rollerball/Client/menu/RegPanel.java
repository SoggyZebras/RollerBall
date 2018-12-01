package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;

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
        add(createLinkedButton("Register", "main_menu"));
        add(createLinkedButton("Back", "register_login"));
    }
}
