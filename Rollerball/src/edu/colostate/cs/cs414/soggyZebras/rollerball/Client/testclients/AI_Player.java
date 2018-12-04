package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.testclients;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;

import java.io.IOException;
import java.util.*;

/**
 * Created by Jonathan on 12/1/2018.
 */
public class AI_Player {

    //Order of operations:
    //1)Capture if possible or
    //2)Avoid capture or
    //3)Pick a random piece with a valid move and pick a random valid move in available moves

    //Generate a random number between 0 and length of array - then pick that piece/valid move of piece

    //AI will always be the black player as it is instantiated by another user
    //setGame MUST BE CALLED INITIALLY BY CLIENT? TO ADD GAME TO ALL GAMES LIST

    private Game currGame;
    private ArrayList<Game> allGames = new ArrayList<>();
    private Client currClient;
    private Map<Location, Piece> Board;
    private ArrayList<Location> allWLocs = new ArrayList<>();
    private ArrayList<Location> allBLocs = new ArrayList<>();

    /**
     * Function to setGame in AI, AI player keeps a list of all current games it is playing in and updates the game state accordingly
     * @param passed - Current game state object that needs to be updated
     */
    public void setGame(Game passed){
        boolean found = false;
        
        //TODO: check for concurrent modification exception in this:
        for(Game g: allGames){
            if(g.getGameID()==passed.getGameID()){
                g = passed; //updating the outdated game state to the new state passed
                found = true;
                break;
            }
        }
        if(!found){
            allGames.add(passed);
        }
    }

    /**
     * constructor to setup the Client needed for the AI to interface with the server
     * @throws IOException
     */
    public AI_Player()throws IOException{

        currClient = new Client("127.0.0.1",5003);
    }


    /**
     * Parent function to calculate which of the three AI options will be executed
     * @param gameID - ID of current game that AI player is calculating move for
     */
    public void Move(int gameID) {
        System.out.println("AI is making a move");

        boolean found = false;
        //LOCATE CORRECT CURRENT GAME:
        for (Game g : allGames) {
            if (g.getGameID() == gameID) {
                currGame = g;
                Board = currGame.getBoard();
                found = true;
                break;
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

        //Logic to calculate which condition to take
        boolean check = capture();
        if(!check){
            check = avoid();
            if(!check){
                selectMove();
            }
        }
    }

    /**
     * Function to calculate if any white pieces can be captured by AI player
     * @return - returns true if there is a white piece able to be captured
     */
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
                    if(canCapture)break;
                }
            }
            if(canCapture)break;
        }


        return canCapture;
    }

    /**
     * Function to calculate if any black pieces are in immediate danger of being captured
     * @return - returns true if a black piece can be captured
     */
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
                            }
                            else {//The validMoves list will have at least 1 location:
                                currClient.makeMove(Y, currGame.validMoves(Y).get(0));
                                break;
                        }
                        }
                    }
                    if(canCapture)break;
                }
            }
            if(canCapture)break;
        }


        return canCapture;

    }

    /**
     * Function to calculate which piece will move to which position
     */
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
