package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.testclients;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.testgames.TwoRooks;

import java.io.IOException;

public class TwoRooksRunner {
    public static void main(String[] args) throws IOException {
        new GameGUI(new Client("127.0.0.1",5003), new TwoRooks());
    }
}
