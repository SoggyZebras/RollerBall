package edu.colostate.cs.cs414.soggyZebras.rollerball.Driver;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.GUIRunner;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.GameGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Server;

import java.io.IOException;

public class Driver {

  public static void main(String []args) throws IOException, InterruptedException {
    Thread t1 = new Thread(new Server(5003,128));
    Thread t2 = new Thread(new MenuGUI());
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
