package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
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

    public Map<Integer,RollerballPanel> activeGameGUIs;

    public MenuGUI() {
        super("Rollerball Menu");

        activeGameGUIs = new HashMap<>();

        try {
            // TODO: change server address
            client = new Client("127.0.0.1",35355);
            client.initialize();
            client.setGui(this);
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
        cardContainer.refreshAll(loggedInUser);
        cardContainer.show(newMenu);
    }

    public CardContainer getCardContainer() {
        return cardContainer;
    }

    /**
     * open a game window for the game with the given gameID
     * @param gameID
     */
    public void openGameGUI(int gameID) {
        if (!activeGameGUIs.containsKey(gameID)) {

            // find game with correct id
            Game loadedGame = null;
            for (Game game : loggedInUser.getGames()) {
                if (game.getGameID() == gameID) {
                    loadedGame = game;
                }
            }

            // open that game
            try {
                GameGUI gameGUI = new GameGUI(client, loadedGame, this);
                activeGameGUIs.put(gameID, gameGUI.panel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // THESE METHODS ARE CALLED BY THE CLIENT WHEN GAME/MENU STATE IS UPDATED

    /**
     * updates the valid moves of a RollerballPanel with a specific gameID
     * @param gameID
     * @param validMoves
     */
    public void updateValidMoves(int gameID, ArrayList<Location> validMoves) {
        RollerballPanel gameWindow = activeGameGUIs.get(gameID);
        gameWindow.updateValidMoves(validMoves);
    }

    /**
     * updates the logged in user and updates all the menus
     * @param updatedUser
     */
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

    /**
     * called when the server responds to a register attempt
     * @param user the user that was registered, null if unsuccessful
     * @param message an error message
     */
    public void onRegisterResponse(User user, String message) {
        login(user, message);
    }

    /**
     * called when the server responds to a login attempt
     * @param user the user that was logged in, null if unsuccessful
     * @param message an error message
     */
    public void onLoginResponse(User user, String message) {
        System.err.println("login response");
        login(user, message);
    }

    /**
     * log in a user, show error if user is null
     * @param user
     * @param message
     */
    private void login(User user, String message) {
        if (user != null) {
            loggedInUser = user;
            setMenu("main_menu");
            refresh(user);
        }
        else {
            // TODO: popup error message
            JOptionPane.showMessageDialog(this, message);
            System.err.println(message);
        }
    }
}
