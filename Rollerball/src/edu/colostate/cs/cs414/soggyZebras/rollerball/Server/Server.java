package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Database.Database.Database;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.sql.ResultSet;

public class Server implements Node,Runnable {


    GameCache games;
    Database db;
    ArrayList<Integer> gameIDs;
    ArrayList<Integer> inviteIDs;
    Random random;

    //=======NETWORK SETUP=======//

    //Local variables for networking
    private int serverPort;
    private TCPServerCache serverCache;
    private TCPServerThread serverThread;

    public Server(int port){

        //instantiate variables and create required threads
        this.serverPort = port;
        this.serverCache = new TCPServerCache();
        games = new GameCache();
        db = new Database();
        this.serverThread = new TCPServerThread(this, serverCache, this.serverPort);

    }


    public void run(){

        //load previous state from database
        init();

        //Start server thread(send/receive threads
        this.serverThread.run();
    }

    private void init(){

    }


    @Override
    public void onEvent(Event e, Socket socket) throws IOException, ClassNotFoundException {
        //React to messages sent to the server
        switch(e.getType()){

            case Client_Make_Move: handleMakeMove(e,socket);break;

            case Client_Request_Check_Move: handleCheckMove(e,socket);break;

            case Client_Sends_Invite: handleClientSendsInvite(e,socket);break;

            case Client_Responds_Invite: handleClientRespondsInvite(e,socket);break;

            case Client_Sends_Login: handleClientSendsLogin(e, socket);break;

            case Client_Sends_Registration: handleClientSendsRegistration(e, socket);break;

            case Client_Sends_Refresh: handleClientSendsRefresh(e, socket);break;

            case Client_Sends_Deregister: handleClientSendsDeregister(e, socket);break;

            case Client_Sends_Logout: handleClientSendsLogout(e, socket);break;
            default:
        }
    }

    private void handleMakeMove(Event e ,Socket socket) throws IOException {
        ClientMakeMove message =(ClientMakeMove) e;
        games.getGame(message.getGameID()).makeMove(serverCache.getUser(socket),message.getTo(),message.getFrom());
        //handleClientRequestGameState(e,socket);
        
    }

    private void handleCheckMove(Event e, Socket socket) throws IOException {
        //When client asks for available spaces, get possible moves from game
        ClientRequestsCheckMove message = (ClientRequestsCheckMove) e;

        ServerRespondsCheckMove response = new ServerRespondsCheckMove(games.getGame(message.getGameID()).validMoves(serverCache.getUser(socket),message.getPlace()),message.getGameID());
        if(response.getList() != null){
            this.serverCache.getUserCon(socket).sendData(response.getFile());
        }
    }

    private void handleClientSendsInvite(Event e, Socket socket) throws IOException{
        ClientSendsInvite message = (ClientSendsInvite) e;
        User sentFrom = this.serverCache.getUser(socket);
        User sendTo = serverCache.getUser(message.getUserTo());
        Invite inv = new Invite(sentFrom.getUsername(), sendTo.getUsername(), genInviteID());

        sendTo.addInviteSent(inv);
        sentFrom.addInviteGot(inv);

        if(serverCache.getConnection(sendTo.getUserID())!= null){
            ServerSendsInvite response = new ServerSendsInvite(sentFrom.getUsername(),sendTo,inv.getInviteID());
            this.serverCache.getConnection(sendTo.getUserID()).sendData(response.getFile());
        }

        ServerRespondsInvite response2 = new ServerRespondsInvite(sentFrom);
        this.serverCache.getConnection(sentFrom.getUserID()).sendData(response2.getFile());

    }

    private void handleClientRespondsInvite(Event e, Socket s) throws IOException {
        ClientRespondsInvite message = (ClientRespondsInvite) e;
        User sentUser = this.serverCache.getUser(s);
        User fromUser = serverCache.getUser(message.getUsername());
        int gID;
        fromUser.removeInviteGot(message.getInviteID());
        sentUser.removeInviteSent(message.getInviteID());
        gID = genGameID();
        Game newGame = new Game(gID,sentUser,fromUser);
        games.addGame(newGame);
        sentUser.addGame(newGame);
        fromUser.addGame(newGame);
        db.insertGame(newGame.getPlayer1().getUsername(),newGame.getPlayer2().getUsername(),newGame,newGame.getWhosTurn().getUsername(),newGame.getWinner().getUsername(),newGame.getLoser().getUsername(),newGame.isInProgress());

        if(serverCache.getConnection(fromUser.getUserID()) != null){
            ServerRespondsInvite response2 = new ServerRespondsInvite(fromUser);
            this.serverCache.getConnection(fromUser.getUserID()).sendData(response2.getFile());
        }

        ServerRespondsInvite response1 = new ServerRespondsInvite(sentUser);
        this.serverCache.getConnection(sentUser.getUserID()).sendData(response1.getFile());

    }

    private void handleClientSendsLogin(Event e, Socket socket) throws IOException, ClassNotFoundException{
        //TODO check the username is valid, check the database to see if that user exists
        // if the user does not exists, send rejection
        // else fetch it, set the user fields and pass it to gui
        // remove old uid from serverthread
        ClientSendsLogin message = (ClientSendsLogin) e;
        User user = null;
        String reason = "";
        if(checkUsername(message.getUsername())){
            if(!serverCache.UserLoggedIn(serverCache.getUser(message.getUsername()).getUserID())){
                user = serverCache.getUser(message.getUsername());
                serverCache.getUserCon(socket).setConID(user.getUserID());
            }
            else{
                reason = "User already logged in";
            }

        }
        else{
            reason = "User does not exist";
        }
        ServerRespondsLogin response = new ServerRespondsLogin(user,reason);
        serverCache.getUserCon(socket).sendData(response.getFile());
        //ResultSet result = db.getUser(message.getUsername(),"");


    }

    private void handleClientSendsRegistration(Event e, Socket socket) throws IOException{
        //TODO check username and password with login database, if in database reject
        // else create user and update database
        ClientSendsRegistration message = (ClientSendsRegistration) e;
        User user = null;
        String reason = "";
        if(checkPassword(message.getPassword())){
                if(!checkUsername(message.getUsername())){
                    user = serverCache.getUser(socket);
                    user.setUsername(message.getUsername());
                    user.setPassword(message.getPassword());
                    user.setEmail(message.getEmail());
                    user.setUserID(serverCache.getUserCon(socket).getConID());

                }
                else{
                    reason = "User already exists!";
                }
        }
        else{
            reason = "Bad Password: password must be at least 8 characters long and cannot contain [ ; ( ) , / } { ] \\ or '";
        }

        ServerRespondsRegistration response = new ServerRespondsRegistration(user,reason);
        serverCache.getUserCon(socket).sendData(response.getFile());
    }

    private void handleClientSendsRefresh(Event e, Socket socket) throws IOException {

        ClientSendsRefresh message = (ClientSendsRefresh) e;

        User sendTo = serverCache.getUser(message.getUserID());
        ServerRespondsRefresh response = new ServerRespondsRefresh(sendTo);

        this.serverCache.getConnection(sendTo.getUserID()).sendData(response.getFile());


    }

    private void handleClientSendsLogout(Event e, Socket socket){
        ClientSendsLogout message = (ClientSendsLogout) e;
        serverCache.removeConnection(message.getuID());
    }

    private void handleClientSendsDeregister(Event e, Socket socket) throws IOException{
        //TODO set all attributes of the user to nothing, and delete the user from the userdatabase
        System.err.println("processing deregistration");
        ClientSendsDeregister message = (ClientSendsDeregister) e;
        User user = serverCache.getUser(message.getUserID());
        user.setEmail("");
        user.setPassword("");
        user.setUsername("");
        user.setGotInvites(new Invite[0]);
        user.setSentInvites(new Invite[0]);
        user.setGames(new Game[0]);

        ServerRespondsDeregister response = new ServerRespondsDeregister(user);
        serverCache.getUserCon(socket).sendData(response.getFile());
    }

    //Check each game and make sure the random numbe generated isn't already in use.
    private int genGameID(){
       int id = random.nextInt(Integer.MAX_VALUE);

       while(!gameIDs.contains(id)){
           id = random.nextInt(Integer.MAX_VALUE);
       }
       gameIDs.add(id);
       return id;
    }

    private boolean checkUsername(String username){
        for(User u : this.serverCache.getAllUsers()){
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    private int genInviteID(){
        int id = random.nextInt(Integer.MAX_VALUE);

        while(!inviteIDs.contains(id)){
            id = random.nextInt(Integer.MAX_VALUE);
        }
        inviteIDs.add(id);
        return id;
    }

    private boolean checkPassword(String pass){
        if(pass.contains(";(),/}{\"\'\\") || pass.length() < 8){
            return false;
        }
        return true;
    }

    public static void main(String []args){
        Server s = new Server(35355);
        s.run();
    }
}
