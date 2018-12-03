package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * represents the registration screen
 */
public class RegPanel extends MenuPanel {

    boolean registering;

    public RegPanel(MenuGUI menuGUI) {
        super("register", menuGUI);
        // TODO: add forms for registering
        refresh(menuGUI.loggedInUser);
    }

    @Override
    public void refresh(User updatedUser) {
        removeAll();
        add(new JLabel("username: "));
        add(new TextField(30));
        add(new JLabel("email: "));
        add(new TextField(30));
        add(new JLabel("password: "));
        add(new TextField(30));
        add(createLinkedActionButton("Register", new RegisterListener()));
        add(createLinkedButton("Back", "register_login"));
        registering = false;
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((TextField)getComponent(1)).getText().trim();
            String email = ((TextField)getComponent(3)).getText().trim();
            String password = ((TextField)getComponent(5)).getText().trim();
            try {
                if (!registering) {
                    registering = true;
                    add(new JLabel("Registering..."));
                    getMenuGUI().setMenu("main_menu");
                    revalidate();
                    repaint();
                    getMenuGUI().client.register(username, password, email);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
