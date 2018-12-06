package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay.PendingInviteListDisplay;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay.UserListDisplay;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateInvitePanel extends MenuPanel {

    private UserListDisplay selectedUser;
    private DefaultListModel<UserListDisplay> userListModel;

    public CreateInvitePanel(MenuGUI menuGUI) {
        super("create_invite", menuGUI);
        add(new JLabel("User to invite:"));
        add(new TextField(30));
        add(createLinkedActionButton("Invite", new SendInviteListener()));
        add(createLinkedButton("Back", "main_menu"));

        userListModel = new DefaultListModel();

        // get all users on system
        if (getMenuGUI().loggedInUser != null) {
            for (User user : menuGUI.allUsers) {
                userListModel.add(0, new UserListDisplay(user));
            }
        }

        JList<UserListDisplay> userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedUser = userList.getSelectedValue();
            }
        });

        // make it so that the selected user doesn't change even if it is refreshed
        if (selectedUser != null) {
            userList.setSelectedValue(selectedUser, true);
        }

        add(createLinkedActionButton("Invite selected user", new SendInviteFromListListener(selectedUser)));
    }

    class SendInviteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((TextField)getComponent(1)).getText().trim();
            try {
                if (username.equals(getMenuGUI().loggedInUser.getUsername())) {
                    JOptionPane.showMessageDialog(getMenuGUI(), "You cannot send an invite to yourself.");
                }
                else {
                    getMenuGUI().client.sendInvite(username);
                    JOptionPane.showMessageDialog(getMenuGUI(), "Your invite was successfully sent to " + username + ".");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class SendInviteFromListListener implements ActionListener {

        public SendInviteFromListListener(UserListDisplay user) {
            try {
                if (user != null) {
                    String username = user.user.getUsername();
                    getMenuGUI().client.sendInvite(username);
                    JOptionPane.showMessageDialog(getMenuGUI(), "Your user was successfully sent to " + username + ".");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
