package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import java.io.IOException;

public class GUIRunner implements Runnable{

    @Override
    public void run() {
        try {
            new GameGUI(new Client("127.0.0.1",5003), new Game());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
