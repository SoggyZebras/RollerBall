package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.RollerballPanel;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private Map<Integer,RollerballPanel> activeGameGUIs;

    public MenuGUI() {
        super("Rollerball Menu");

        activeGameGUIs = new HashMap<>();

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

    public void addActiveGameGUI(int gameID, RollerballPanel gameGUI) {
        activeGameGUIs.put(gameID, gameGUI);
    }

    // THESE 2 METHODS ARE CALLED BY THE CLIENT WHEN GAME STATE IS UPDATED
    public void updateValidMoves(int gameID, ArrayList<Location> validMoves) {
        RollerballPanel gameWindow = activeGameGUIs.get(gameID);
        gameWindow.updateValidMoves(validMoves);
    }

    public void refresh(User updatedUser) {
        this.loggedInUser = updatedUser;

        // refresh menu
        // this assumes that each card has its refresh function filled out
        cardContainer.refreshAll(updatedUser);

        // update game windows
        for (Game userGame : updatedUser.getGames()) {
            RollerballPanel activeGame = activeGameGUIs.get(userGame.getGameID());
            if (activeGame != null) {
                // TODO: should we just pass the entire game?
                activeGame.updateState(userGame.getBoard());
            }
        }
    }
}
