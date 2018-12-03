package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * represents the login screen
 */
public class LoginPanel extends MenuPanel {
    private boolean loggingIn;

    public LoginPanel(MenuGUI menuGUI) {
        super("login", menuGUI);
        refresh(menuGUI.loggedInUser);
    }

    @Override
    public void refresh(User updatedUser) {
        removeAll();
        add(new JLabel("username: "));
        add(new TextField(30));
        add(new JLabel("password: "));
        add(new TextField(30));
        add(createLinkedActionButton("Login", new LoginListener()));
        add(createLinkedButton("Back", "register_login"));
        loggingIn = false;
        revalidate();
        repaint();
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((TextField)getComponent(1)).getText().trim();
            String password = ((TextField)getComponent(3)).getText().trim();
            try {
                if (!loggingIn) {
                    loggingIn = true;
                    add(new JLabel("Logging in..."));
                    revalidate();
                    repaint();
                    getMenuGUI().client.login(username, password);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
