package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.testclients;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;

import java.util.*;

/**
 * Created by Jonathan on 12/1/2018.
 */
public class AI_Player {

    //Order of operations:
    //1)Capture if possible or
    //2)Avoid capture or
    //3)Pick a random piece with a valid move and pick a random valid move in available moves

    //Generate a random number between 0 and length of hashMap keys - then iterate from 0 that many times

    //AI will always be the black player as it is instantiated by another user
    //setGame MUST BE CALLED INITIALLY BY CLIENT? TO ADD GAME TO ALL GAMES LIST

    public void setGame(Game passed){
        boolean found = false;
        for(Game g: allGames){
            if(g.getGameID()==passed.getGameID()){
                g = passed; //updating the outdated game state to the new state passed
                found = true;
            }
        }
        if(!found){
            allGames.add(passed);
        }
    }

    private Game currGame;
    private ArrayList<Game> allGames = new ArrayList<>();
    private Client currClient;
    private Map<Location, Piece> Board;
    private ArrayList<Location> allWLocs = new ArrayList<>();
    private ArrayList<Location> allBLocs = new ArrayList<>();


    public void Move(int gameID) {
        System.out.println("AI is making a move");

        boolean found = false;
        //LOCATE CORRECT CURRENT GAME:
        for (Game g : allGames) {
            if (g.getGameID() == gameID) {
                currGame = g;
                Board = currGame.getBoard();
                found = true;
            }
        }
        if (!found) {
            throw new RuntimeException("Incorrect gameID passed to AI Move function");
        }

        //POPULATE CURRENT PIECE LOCATIONS:
        for (Location I : Board.keySet()) {
            if (Board.get(I).getColor() == 'w') {
                allWLocs.add(I);
            }//Now all the current white piece locations are populated
        }

        for (Location I : Board.keySet()) {
            if (Board.get(I).getColor() == 'b') {
                allBLocs.add(I);
            }//Now all the current black piece locations are populated
        }

        boolean check = capture();
        if(!check){
            check = avoid();
            if(!check){
                selectMove();
            }
        }
    }


    private boolean capture(){

        boolean canCapture = false;

        for(Location I :Board.keySet()){
            if(Board.get(I).getColor()=='b'){
                for(Location X : currGame.validMoves(I)) {
                    for(Location Y: allWLocs){
                        if (X==Y){
                            canCapture = true;
                            currClient.makeMove(X,Y);
                            System.out.println("AI has captured White piece: "+Board.get(Y).getType() + "At location: "+Y.toString());
                            break;
                        }
                    }
                }
            }
        }
        return canCapture;
    }

    private boolean avoid (){

        boolean canCapture = false;

        for(Location I :Board.keySet()){
            if(Board.get(I).getColor()=='w'){
                for(Location X : currGame.validMoves(I)) {
                    for(Location Y: allBLocs){
                        if (X==Y){
                            canCapture = true;
                            System.out.println("AI can be captured by White piece: "+Board.get(X).getType() + "At location: "+X.toString());
                            //Need to move from unsafe location to safe location if we have a valid move
                            //If no valid moves are available then we cant move anywhere and we will have to be captured
                            if(currGame.validMoves(Y).isEmpty()){
                                canCapture = false;
                                break;
                            }
                            else {//The validMoves list will have at least 1 location:
                                currClient.makeMove(Y, currGame.validMoves(Y).get(0));
                            }
                            break;
                        }
                    }
                }
            }
        }
        return canCapture;

    }

    private void selectMove(){

        Random rand = new Random();

        for(Location I: allBLocs){
            int value = rand.nextInt(allBLocs.size());
            Location loc = allBLocs.get(value);
            ArrayList<Location> valMoves = currGame.validMoves(loc);
            if(!valMoves.isEmpty()){//We know that the piece has a valid location it can move to.
                value = rand.nextInt(valMoves.size());
                Location move = valMoves.get(value);
                currClient.makeMove(loc,move);
                break;
            }
            else throw new RuntimeException("Something went wrong in the AI selectMove func - at least one piece should always have a valid move")
        }
    }

}
