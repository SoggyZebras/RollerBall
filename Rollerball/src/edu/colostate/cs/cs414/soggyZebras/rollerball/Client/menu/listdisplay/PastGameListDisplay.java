package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.listdisplay;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

/**
 * a wrapper class used to display a game in the list of active games
 */
public class PastGameListDisplay {
    public Game game;
    private String myUsername;

    public PastGameListDisplay(Game game, String myUsername) {
        this.game = game;
        this.myUsername = myUsername;
    }

    @Override
    public int hashCode() {
        return game.getGameID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PastGameListDisplay that = (PastGameListDisplay) o;
        return game.getGameID() == that.game.getGameID();
    }

    @Override
    public String toString() {
        String otherUser = game.getPlayer1().getUsername().equals(myUsername) ?
                game.getPlayer2().getUsername() : game.getPlayer1().getUsername();

        String winMessage = game.getWinner().getUsername().equals(otherUser) ? "they" : "you";

        return winMessage + " won - game against " + otherUser + " - ID: " + game.getGameID();
    }
}
