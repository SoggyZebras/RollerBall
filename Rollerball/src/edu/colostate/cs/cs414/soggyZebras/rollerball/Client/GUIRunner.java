package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.MenuGUI;

public class GUIRunner {
    public static void main(String[] args) {
        new MenuGUI().setVisible(true);
        // TODO: have MenuGUI call new GameGUI().setVisible(true);
    }
}
