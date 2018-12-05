package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.testclients;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Invite;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

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
    User AI;
    AI_Client cl;
    private ArrayList<Game> allGames = new ArrayList<>();
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

        cl = new AI_Client(this,"129.82.44.55",35355);
        AI = new User(0,"AI_Player","YouwillNeverBeatme YouwillNeverBeatme","Ai@gov.email");
        cl.initialize();
        cl.register(AI.getUsername(),AI.getPassword(),AI.getEmail());
    }


    /**
     * Parent function to calculate which of the three AI options will be executed
     * @param gameID - ID of current game that AI player is calculating move for
     */
    public void Move(int gameID) {
        System.out.println("AI calculating move");
        allBLocs.clear();
        allWLocs.clear();

        boolean found = false;
        //LOCATE CORRECT CURRENT GAME:
        for (Game g : AI.getGames()) {
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

        if(currGame.getWhosTurn() == AI) {
            System.out.println("AI's turn");
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
            boolean checkC = capture(gameID);
            if (!checkC) {
                boolean checkA = avoid(gameID); //move if can move to avoid
                if (!checkA) {
                    selectMove(gameID);
                }
            }
        }
    }

    /**
     * Function to calculate if any white pieces can be captured by AI player
     * @return - returns true if there is a white piece able to be captured
     */
    private boolean capture(int gID){

        boolean canCapture = false;

        for(Location I : allBLocs){
                for (Location X : currGame.validMoves(AI, I)) { //Valid black moves
                    for (Location Y : allWLocs) { //Valid white moves
                        if (X.equals(Y)) {
                            canCapture = true;
                            cl.makeMove(Y, I, gID); //TO, FROM
                            System.out.println("AI has captured White piece: " + Board.get(Y).getType() + " At location: " + Y.toString());
                            break;
                        }
                    }
                    if (canCapture) break;
                }

            if(canCapture)break;
        }


        return canCapture;
    }

    /**
     * Function to calculate if any black pieces are in immediate danger of being captured
     * @return - returns true if a black piece can be captured
     */
    private boolean avoid (int gID){

        boolean canCapture = false;

        for(Location I : allWLocs){
                for(Location X : currGame.validMoves(AI,I)) {
                    for(Location Y: allBLocs){
                        if (X.equals(Y)){
                            System.out.println("AI can be captured by White piece: "+Board.get(X).getType() + "At location: "+X.toString());
                            //Need to move from unsafe location to safe location if we have a valid move
                            //If no valid moves are available then we cant move anywhere and we will have to be captured
                            if(!currGame.validMoves(AI,Y).isEmpty()){
                                cl.makeMove(currGame.validMoves(AI,Y).get(0), Y ,gID);
                                canCapture = true;
                                break;
                            }

                        }
                    }
                    if(canCapture)break;
                }

            if(canCapture)break;
        }


        return canCapture;

    }

    /**
     * Function to calculate which piece will move to which position
     */
    private void selectMove(int gID){

        System.out.println("AI is making normal move");
        Random rand = new Random();
        boolean canMove = false;

        for(Location I: allBLocs){
            int value = rand.nextInt(allBLocs.size());
            Location loc = allBLocs.get(value);
            ArrayList<Location> valMoves = currGame.validMoves(AI,loc);

            if(valMoves != null) {
                if (!valMoves.isEmpty()) {//We know that the piece has a valid location it can move to.
                    value = rand.nextInt(valMoves.size());
                    Location move = valMoves.get(value);
                    canMove = true;
                    cl.makeMove(move, loc, gID); //TO, FROM
                    break;
                }
            }
        }

        if(!canMove) throw new RuntimeException("Something went wrong in the AI selectMove func - at least one piece should always have a valid move");

    }


    //-----------UPDATE STATE METHODS---------//


    public void refresh(User u) throws IOException {
        AI = u;
        for(Invite i : u.getGotInvites()){
            cl.respondInvite(i.getInviter(),i.getInviteID());
        }
        for(Game g : u.getGames()){
                Move(g.getGameID());
        }
    }

    public void onRegisterResponse(User u, String resp){
        AI = u;
    }


    public static void main(String[] args) throws IOException {
        AI_Player p = new AI_Player();
    }
}
