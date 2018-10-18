package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

        setSize(320, 480);
    }

    /**
     * show a specific menu in this window
     * @param newMenu the name of the new menu to show, for example "register"
     */
    public void setMenu(String newMenu) {
        cardContainer.show(newMenu);
    }
}
