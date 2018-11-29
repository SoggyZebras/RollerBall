package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import java.util.ArrayList;

public class GameCache {

    ArrayList<Game> games;


    public GameCache(){
        games = new ArrayList<Game>();
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




}


