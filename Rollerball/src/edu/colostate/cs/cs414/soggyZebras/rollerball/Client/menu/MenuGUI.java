package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.RollerballPanel;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * a jframe that holds different states of the game's menu
 * manages the menu and any games that were created by the menu
 */
public class MenuGUI extends JFrame {

    // the container holding all the cards
    private CardContainer cardContainer;

    /**
     * the client used to communicate with the server
     */
    public Client client;

    /**
     * the user who is currently logged into this gui, null until a login or register succeeds
     */
    public User loggedInUser;

    private ArrayList<RollerballPanel> activeGameGUIs;

    public MenuGUI() {
        super("Rollerball Menu");

        activeGameGUIs = new ArrayList<>();

        try {
            // TODO: change server address
            client = new Client("127.0.0.1",5003);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void addActiveGameGUI(RollerballPanel gameGUI) {
        activeGameGUIs.add(gameGUI);
    }

    // THESE 2 METHODS ARE CALLED BY THE CLIENT WHEN GAME STATE IS UPDATED
    public void updateValidMoves(int gameID, ArrayList<Location> validMoves) {
        // TODO: look through games, find game gui (RollerballPanel) with matching id, call updateValidMoves that object
        System.err.println("updated valid moves");
    }

    public void refresh(User updatedUser) {
        System.err.println("updated user");
        // TODO: update menu for this user, update games including open gui games
    }
}
