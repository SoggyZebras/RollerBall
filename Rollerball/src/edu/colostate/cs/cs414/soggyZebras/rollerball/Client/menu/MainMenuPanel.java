package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

/**
 * represents the main menu screen (current games, history, new game)
 */
public class MainMenuPanel extends MenuPanel {
    public MainMenuPanel(MenuGUI menuGUI) {
        super("main_menu", menuGUI);
        add(createLinkedButton("New Game", "new_game"));
        add(createLinkedButton("Current Games", "current_games"));
        add(createLinkedButton("Game History", "game_history"));
        add(createLinkedButton("Logout", "register_login"));
        add(createLinkedButton("Unregister", "register_login"));
    }
}
