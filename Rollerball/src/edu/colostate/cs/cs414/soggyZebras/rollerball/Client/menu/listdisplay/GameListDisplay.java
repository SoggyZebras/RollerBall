package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import java.util.Objects;

/**
 * a wrapper class used to display a game in the list of active games
 */
public class GameListDisplay {
    public Game game;

    public GameListDisplay(Game game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        return game.getGameID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameListDisplay that = (GameListDisplay) o;
        return game.getGameID() == that.game.getGameID();
    }

    @Override
    public String toString() {
        return "ID: " + game.getGameID() + " Players: " + game.getPlayer1() + ", " + game.getPlayer2();
    }
}
