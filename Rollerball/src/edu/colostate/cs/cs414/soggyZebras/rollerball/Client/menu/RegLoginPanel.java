package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

/**
 * represents the register/login screen (first thing user sees)
 */
public class RegLoginPanel extends MenuPanel {

    public RegLoginPanel(MenuGUI menuGUI) {
        super("register_login", menuGUI);
        add(createLinkedButton("Register", "register"));
        add(createLinkedButton("Login", "login"));
    }
}
