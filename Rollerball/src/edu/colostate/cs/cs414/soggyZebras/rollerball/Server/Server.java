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
        //Start server thread(send/receive threads
        this.serverThread.run();
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

            default:
        }
    }

    private void handleMakeMove(Event e ,Socket socket) throws IOException {
        ClientMakeMove message =(ClientMakeMove) e;
        games.getGame(message.getGameID()).makeMove(message.getTo(),message.getFrom());
        //handleClientRequestGameState(e,socket);
        
    }

    private void handleCheckMove(Event e, Socket socket) throws IOException {
        //When client asks for available spaces, get possible moves from game
        ClientRequestsCheckMove message = (ClientRequestsCheckMove) e;

        ServerRespondsCheckMove response = new ServerRespondsCheckMove(games.getGame(message.getGameID()).validMoves(message.getPlace()),message.getGameID());

        this.serverCache.getUserCon(socket).sendData(response.getFile());
    }

    private void handleClientSendsInvite(Event e, Socket socket) throws IOException{
        System.out.println("processing invite");
        ClientSendsInvite message = (ClientSendsInvite) e;
        User sentFrom = this.serverCache.getUser(socket);
        User sendTo = serverCache.getUser(message.getUserTo());
        Invite inv = new Invite(sentFrom.getUsername(), sendTo.getUsername(), genInviteID());

        sendTo.addInviteSent(inv);
        sentFrom.addInviteGot(inv);

        ServerSendsInvite response = new ServerSendsInvite(sentFrom.getUsername(),sendTo,inv.getInviteID());
        ServerRespondsInvite response2 = new ServerRespondsInvite(sentFrom);

        this.serverCache.getConnection(sentFrom.getUserID()).sendData(response2.getFile());
        this.serverCache.getConnection(sendTo.getUserID()).sendData(response.getFile());

    }

    private void handleClientRespondsInvite(Event e, Socket s) throws IOException {
        System.out.println("processing invite response");
        ClientRespondsInvite message = (ClientRespondsInvite) e;
        User sentUser = this.serverCache.getUser(s);
        User fromUser = serverCache.getUser(message.getUsername());
        int gID;
        fromUser.removeInviteGot(message.getInviteID());
        sentUser.removeInviteSent(message.getInviteID());
        gID = genGameID();
        games.addGame(new Game(gID,sentUser,fromUser));

        ServerRespondsInvite response1 = new ServerRespondsInvite(sentUser);
        ServerRespondsInvite response2 = new ServerRespondsInvite(fromUser);

        this.serverCache.getConnection(sentUser.getUserID()).sendData(response1.getFile());
        this.serverCache.getConnection(fromUser.getUserID()).sendData(response2.getFile());

    }

    private void handleClientSendsLogin(Event e, Socket socket) throws IOException, ClassNotFoundException{
        //TODO check the username is valid, check the database to see if that user exists
        // if the user does not exists, send rejection
        // else fetch it, set the user fields and pass it to gui
        // remove old uid from serverthread
        System.out.println("processing login request");
        ClientSendsLogin message = (ClientSendsLogin) e;
        User user = null;
        String reason = "";
        if(checkUsername(message.getUsername())){
            user = serverCache.getUser(message.getUsername());

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
        System.out.println("processing registration");
        ClientSendsRegistration message = (ClientSendsRegistration) e;
        User user = null;
        String reason = "";
        if(checkPassword(message.getPassword())){
                if(!checkUsername(message.getUsername())){
                    user = serverCache.getUser(socket);
                    user.setUsername(message.getUsername());
                    user.setPassword(message.getPassword());
                    user.setEmail(message.getEmail());

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
            if(u.getUsername() == username){
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
