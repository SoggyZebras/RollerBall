package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;

/**
 * a jframe that holds different states of the game's menu
 */
public class MenuGUI extends JFrame {

    // the container holding all the cards
    private CardContainer cardContainer;

    public MenuGUI() {
        super("Rollerball Menu");

        cardContainer = new CardContainer(this);
        add(cardContainer);

        setSize(320, 320);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
