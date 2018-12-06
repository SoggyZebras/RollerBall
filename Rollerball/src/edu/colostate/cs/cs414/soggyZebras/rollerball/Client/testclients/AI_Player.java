package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.testclients;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Invite;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
    private Map<Location, Piece> currBoard;
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
                g = passed;//updating the outdated game state to the new state passed
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
    public AI_Player(String ip)throws IOException{

        cl = new AI_Client(this,ip,35355);
        AI = new User(0,"AI_Player","YouwillNeverBeatme YouwillNeverBeatme","Ai@gov.email");
        cl.initialize();
        cl.register(AI.getUsername(),AI.getPassword(),AI.getEmail());
    }


    /**
     * Parent function to calculate which of the three AI options will be executed
     * @param gameID - ID of current game that AI player is calculating move for
     */
    public void Move(int gameID) {
        allBLocs.clear();
        allWLocs.clear();


        boolean found = false;
        //LOCATE CORRECT CURRENT GAME:
        for (Game g : AI.getGames()) {
            if (g.getGameID() == gameID) {
                currGame = g;
                currBoard = currGame.getBoard();
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
            for (Location I : currBoard.keySet()) {
                if (currBoard.get(I).getColor() == 'w') {
                    allWLocs.add(I);
                }//Now all the current white piece locations are populated
            }

            for (Location I : currBoard.keySet()) {
                if (currBoard.get(I).getColor() == 'b') {
                    allBLocs.add(I);
                }//Now all the current black piece locations are populated
            }
            System.out.println("AI calculating move");
            //Logic to calculate which condition to take
            boolean check = kingCapture(gameID, currBoard);
            if (!check) {
                check = capture(gameID); //move if can move to avoid
                if (!check) {
                   check = avoid(gameID);
                   if(!check){

                       boolean findFutureKCapture = false;
                       boolean findFutureCapture = false;

                       for(Location locFrom: allBLocs){
                           ArrayList<Location> validBMoves = currGame.validMoves(AI,locFrom);

                           for(Location locTo: validBMoves){
                               Map <Location,Piece> newBoard = new HashMap<>(currBoard);
                               newBoard.remove(locFrom);
                               switch(currBoard.get(locFrom).getType()){
                                   case "pawn":
                                       Pawn newPawn = new Pawn(locTo, currBoard.get(locFrom).getColor(),currBoard.get(locFrom).getType());
                                       newBoard.put(locTo,newPawn);
                                   case "rook":
                                       Rook newRook = new Rook(locTo, currBoard.get(locFrom).getColor(),currBoard.get(locFrom).getType());
                                       newBoard.put(locTo,newRook);
                                   case "bishop":
                                       Bishop newBishop = new Bishop(locTo, currBoard.get(locFrom).getColor(),currBoard.get(locFrom).getType());
                                       newBoard.put(locTo,newBishop);
                                   case "king":
                                       King newKing = new King(locTo, currBoard.get(locFrom).getColor(),currBoard.get(locFrom).getType());
                                       newBoard.put(locTo,newKing);

                               }

                               findFutureKCapture = fKingCapture(locTo,newBoard);
                               if(findFutureKCapture){
                                   System.out.println("AI has found a FUTURE MOVE TO CAPTURE THE KING");
                                   cl.makeMove(locTo,locFrom,gameID);
                                   break;
                               }
                               findFutureCapture = fCapture(locTo,newBoard);
                               if(findFutureCapture){
                                   System.out.println("AI has found a FUTURE MOVE TO CAPTURE A PIECE");
                                   cl.makeMove(locTo,locFrom,gameID);
                                   break;
                               }
                           }
                           if(findFutureKCapture||findFutureCapture)break;
                       }
                       if(!findFutureKCapture&&!findFutureCapture){
                           System.out.println("AI didn't find good future move, calling Random Move");
                           selectRandom(gameID);
                       }

                   }
                }
            }
        }
    }

    /**
     * Function to calculate if the future move of a piece on a hypothetical board/game state will capture a piece
     * @return - returns true if the future move in consideration will capture a white piece
     */
    private boolean fCapture(Location loc, Map<Location, Piece> boardPassed){
        //check for future capture condition one move into the hypothetical future game
        boolean canCapture = false;

            for (Location X : currGame.validMoves(loc, boardPassed)) { //Valid black moves
                for (Location Y : allWLocs) { //Valid white moves
                    if (X.equals(Y)) {
                        canCapture = true;
                        break;
                    }
                }
                if (canCapture) break;
            }

        return canCapture;
    }

    /**
     * Function to calculate if the white king can be capture by the future hypothetical move of a black piece
     * @return - returns true if the white king can be captured in this hypothetical state
     */
    private boolean fKingCapture(Location loc, Map<Location, Piece>boardPassed){

        boolean canCapture = false;
        for (Location X : currGame.validMoves(loc, boardPassed)) { //Valid black moves from overridden game function
            for (Location Y : allWLocs) { //Valid white moves
                if (X.equals(Y)&&boardPassed.get(Y).getType()=="king") {
                    canCapture = true;
                    //System.out.println("Looking for future King to take");

                    break;
                }
            }
            if (canCapture) break;
        }

        return canCapture;

    }

    /**
     * Function to initially calculate if the white king can be capture by a black AI piece
     * @return - returns true if the white king can be captured
     */
    private boolean kingCapture(int gID, Map <Location, Piece> board){
        boolean canCapture = false;

        for(Location I : allBLocs){
            for (Location X : currGame.validMoves(AI, I)) { //Valid black moves
                for (Location Y : allWLocs) { //Valid white moves
                    if (X.equals(Y)&& board.get(Y).getType()=="king") {
                        canCapture = true;
                        cl.makeMove(Y, I, gID); //TO, FROM
                        System.out.println("AI can capture King at location: " + Y.toString());
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
                            System.out.println("AI has captured White piece: " + currBoard.get(Y).getType() + " At location: " + Y.toString());
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
                            System.out.println("AI can be captured by White piece: "+currBoard.get(X).getType() + "At location: "+X.toString());
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
     * This function will consider the next move after the immediate move, as the first move cannot capture but the next move might
     * Special consideration is given to if the piece can capture the White King
     */
    private void selectRandom(int gID){

        System.out.println("AI is calculating Random move");
        Random rand = new Random();
        boolean canMove = false;


        for(Location I: allBLocs){
            int value = rand.nextInt(allBLocs.size());
            Location defaultFrom = allBLocs.get(value);
            ArrayList<Location> valMoves = currGame.validMoves(AI,defaultFrom);

            if(valMoves != null) {
                if (!valMoves.isEmpty()) {//We know that the piece has a valid location it can move to
                    value = rand.nextInt(valMoves.size());
                    Location defaultTo = valMoves.get(value);
                    canMove = true;
                    cl.makeMove(defaultTo, defaultFrom, gID); //TO, FROM
                    break;
                }
            }
        }

        if(!canMove) throw new RuntimeException("Something went wrong in the AI selectRandom func - at least one piece should always have a valid current move");

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
        AI_Player p = new AI_Player(args[0]);
    }
}
