package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import java.util.ArrayList;

public class GameCache {

    ArrayList<Game> games;


    public GameCache(){
        games = new ArrayList<>();
    }


    public Game getGame(int id){
        for(Game g: games){
            if(g.getGameID() == id){
                return g;
            }
        }
        return null;
    }


    public void addGame(Game g){
        games.add(g);
    }


    public boolean containsID(int id){
        for(Game g: games){
            if(g.getGameID() == id){
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(int id){
        if(getGame(id).wonGameW() || getGame(id).wonGameB()){
            return true;
        }
        return false;
    }

    public void setGames(ArrayList<Game> g){
        games = g;
    }


}


