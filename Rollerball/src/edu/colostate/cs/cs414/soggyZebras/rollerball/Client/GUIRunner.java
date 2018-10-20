package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

public class GUIRunner {
    public static void main(String[] args) {
        new GameGUI(new Client(), new Game());
        // call new MenuGUI() to open menu window
    }
}
