package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * holds all of the 'cards' that represent the different menus
 */
public class CardContainer extends JPanel {

    /**
     * holds a list of all menu panels that have been added
     */
    public Map<String,MenuPanel> menuPanels;

    public CardContainer(MenuGUI menuGUI) {
        super(new CardLayout());
        menuPanels = new HashMap<>();

        // create menus
        // TODO replace with addMenuPanel Calls
        addMenuPanel("register_login", new RegLoginPanel(menuGUI));
        addMenuPanel("register", new RegPanel(menuGUI));
        addMenuPanel("login", new LoginPanel(menuGUI));
        addMenuPanel("main_menu", new MainMenuPanel(menuGUI));
        addMenuPanel("create_invite", new CreateInvitePanel(menuGUI));
        addMenuPanel("pending_invites", new PendingInvitesPanel(menuGUI));

        // TODO: add more cards here
    }

    private void addMenuPanel(String name, MenuPanel panel) {
        menuPanels.put(name, panel);
        add(name, panel);
    }

    public void refreshAll(User updatedUser) {
        for (MenuPanel mp : menuPanels.values()) {
            mp.refresh(updatedUser);
        }
    }

    public void show(String cardName) {
        CardLayout layout = (CardLayout)getLayout();
        layout.show(this, cardName);
    }
}
