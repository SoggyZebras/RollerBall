package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;

/**
 * a jframe that holds different states of the game's menu
 */
public class MenuGUI extends JFrame {

    // the container holding all the cards
    private CardContainer cardContainer;

    /**
     * the user who is currently logged into this gui, null until a login or register succeeds
     */
    public User loggedInUser;

    public MenuGUI() {
        super("Rollerball Menu");

        loggedInUser = null;

        cardContainer = new CardContainer(this);
        add(cardContainer);

        setSize(300, 320);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * show a specific menu in this window
     * @param newMenu the name of the new menu to show, for example "register"
     */
    public void setMenu(String newMenu) {
        cardContainer.show(newMenu);
    }

    public CardContainer getCardContainer() {
        return cardContainer;
    }
}
