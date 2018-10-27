package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import java.io.IOException;

public class GUIRunner {
    public static void main(String[] args) throws IOException {
        new GameGUI(new Client("127.0.0.1",5003), new Game());
        // call new MenuGUI() to open menu window
    }
}
