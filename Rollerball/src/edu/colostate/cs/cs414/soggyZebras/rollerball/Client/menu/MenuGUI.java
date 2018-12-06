package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.RollerballPanel;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    public MenuGUI(String ip) {
        super("Rollerball Menu");

        activeGameGUIs = new HashMap<>();

        try {
            client = new Client(ip,35355);
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

        // add listener for when window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // log out this user
                try {
                    client.logout(loggedInUser.getUserID());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                catch (NullPointerException e1) {
                }
            }
        });
    }

    /**
     * show a specific menu in this window
     * @param newMenu the name of the new menu to show, for example "register"
     */
    public void setMenu(String newMenu) {
        // TODO: do we need to call refresh all here?
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
        // refresh all menus for the user passed in updatedUser
        if (updatedUser != null && updatedUser.getUserID() == loggedInUser.getUserID()) {
            loggedInUser = updatedUser;

            // refresh menu
            // this assumes that each card has its refresh function filled out
            cardContainer.refreshAll();
        }

        // if this is not the user passed in updated user, just update the pending invite and active game lists
        else {
            cardContainer.menuPanels.get("main_menu").refresh();
            cardContainer.menuPanels.get("pending_invites").refresh();
        }

        // update game windows
        // TODO: this is currently not being called by the server when moves are made in the game
        if(updatedUser != null) {
            for (Game userGame : updatedUser.getGames()) {
                RollerballPanel activeGame = activeGameGUIs.get(userGame.getGameID());
                if (activeGame != null) {
                    activeGame.updateState(userGame);
                }
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
        // remove "Registering..." and allow user to attempt it again if there was a problem
        cardContainer.menuPanels.get("register").refresh();
    }

    /**
     * called when the server responds to a login attempt
     * @param user the user that was logged in, null if unsuccessful
     * @param message an error message
     */
    public void onLoginResponse(User user, String message) {
        login(user, message);
        // remove "Logging in..." and allow user to attempt it again if there was a problem
        cardContainer.menuPanels.get("login").refresh();
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
            // TODO: notify login/register panel that register was unsuccessful
            JOptionPane.showMessageDialog(this, message);
            System.err.println(message);
        }
    }
}
