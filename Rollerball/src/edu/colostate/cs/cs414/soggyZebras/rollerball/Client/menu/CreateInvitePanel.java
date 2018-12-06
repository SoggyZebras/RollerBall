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
import java.util.Arrays;

public class CreateInvitePanel extends MenuPanel {

    private UserListDisplay selectedUser;
    private DefaultListModel<UserListDisplay> userListModel;

    public CreateInvitePanel(MenuGUI menuGUI) {
        super("create_invite", menuGUI);
        refresh();
    }

    public void updateUserList() {
        // get all users on system
        userListModel.clear();
        if (getMenuGUI().loggedInUser != null) {
            for (User user : getMenuGUI().allUsers) {
                if (!getMenuGUI().loggedInUser.getUsername().equals(user.getUsername())) {
                    userListModel.add(0, new UserListDisplay(user));
                }
            }
        }
        revalidate();
        repaint();
    }

    @Override
    public void refresh() {
        removeAll();
        add(new JLabel("User to invite:"));
        add(new TextField(30));
        add(createLinkedActionButton("Invite", new SendInviteListener()));

        userListModel = new DefaultListModel();

        JList<UserListDisplay> userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedUser = userList.getSelectedValue();
            }
        });


        JScrollPane listScroller = new JScrollPane(userList);
        listScroller.setPreferredSize(new Dimension(250, 100));
        add(listScroller);

        // make it so that the selected user doesn't change even if it is refreshed
        if (selectedUser != null) {
            userList.setSelectedValue(selectedUser, true);
        }

        add(createLinkedActionButton("Invite selected user", new SendInviteFromListListener()));
        add(createLinkedButton("Back", "main_menu"));
    }


    class SendInviteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = ((TextField)getComponent(1)).getText().trim();
            try {
                if (username.equals(getMenuGUI().loggedInUser.getUsername())) {
                    JOptionPane.showMessageDialog(getMenuGUI(), "You cannot send an invite to yourself.");
                }
                else if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(getMenuGUI(), "Enter a username to send an invite to.");
                }
                else {
                    getMenuGUI().client.sendInvite(username);
                    JOptionPane.showMessageDialog(getMenuGUI(), "Your invite was successfully sent to " + username + ".");
                    selectedUser = null;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class SendInviteFromListListener implements ActionListener {

        public SendInviteFromListListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedUser != null) {
                    String username = selectedUser.user.getUsername();
                    getMenuGUI().client.sendInvite(username);
                    JOptionPane.showMessageDialog(getMenuGUI(), "Your invite was successfully sent to " + username + ".");
                    selectedUser = null;
                }
                else {
                    JOptionPane.showMessageDialog(getMenuGUI(), "No user was selected");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
