package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Database.Database.Database;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.*;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
        gameIDs = new ArrayList<>();
        inviteIDs = new ArrayList<>();
        db = new Database();
        this.serverThread = new TCPServerThread(this, serverCache, this.serverPort);

    }


    public void run(){

        //load previous state from database
        try {
            init();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        //Start server thread(send/receive threads
        this.serverThread.run();
    }

    private void init() throws SQLException {
        System.out.println("begin user init...");
        if(db.getAllUser()!= null){
            serverCache.setCache(db.getAllUser());
        }

        if(db.getAllGame() != null){
            games.setGames(db.getAllGame());
        }

    }


    @Override
    public void onEvent(Event e, Socket socket) throws IOException, ClassNotFoundException {
        //React to messages sent to the server
        switch(e.getType()){

            case eClient_Make_Move: handleMakeMove(e,socket);break;

            case eClient_Request_Check_Move: handleCheckMove(e,socket);break;

            case eClient_Sends_Invite: handleClientSendsInvite(e,socket);break;

            case eClient_Responds_Invite: handleClientRespondsInvite(e,socket);break;

            case eClient_Sends_Login: handleClientSendsLogin(e, socket);break;

            case eClient_Sends_Registration: handleClientSendsRegistration(e, socket);break;

            case eClient_Sends_Refresh: handleClientSendsRefresh(e, socket);break;

            case eClient_Sends_Deregister: handleClientSendsDeregister(e, socket);break;

            case eClient_Sends_Logout: handleClientSendsLogout(e, socket);break;
            default:
        }
    }

    private void handleMakeMove(Event e ,Socket socket) throws IOException {
        ClientMakeMove message =(ClientMakeMove) e;
        Game tmp = games.getGame(message.getGameID());
        tmp.makeMove(serverCache.getUser(socket),message.getTo(),message.getFrom());
        User p1 = serverCache.getUser(tmp.getPlayer1().getUserID());
        User p2 = serverCache.getUser(tmp.getPlayer2().getUserID());

        p1.updateGame(message.getGameID(),tmp);
        p2.updateGame(message.getGameID(),tmp);
        ServerRespondsRefresh response1 = new ServerRespondsRefresh(p1);
        ServerRespondsRefresh response2 = new ServerRespondsRefresh(p2);

        serverCache.getConnection(p1.getUserID()).sendData(response1.getFile());
        serverCache.getConnection(p2.getUserID()).sendData(response2.getFile());
        
    }

    private void handleCheckMove(Event e, Socket socket) throws IOException {
        //When client asks for available spaces, get possible moves from game
        ClientRequestsCheckMove message = (ClientRequestsCheckMove) e;

        ServerRespondsCheckMove response = new ServerRespondsCheckMove(games.getGame(message.getGameID()).validMoves(serverCache.getUser(socket),message.getPlace()),message.getGameID());
        if(response.getList() != null){
            this.serverCache.getUserCon(socket).sendData(response.getFile());
        }
    }

    private void handleClientSendsInvite(Event e, Socket socket) throws IOException {
        ClientSendsInvite message = (ClientSendsInvite) e;
        User sentFrom = this.serverCache.getUser(socket);
        User sendTo = serverCache.getUser(message.getUserTo());
        if(sentFrom == null || sendTo == null){
        }
        else {
          Invite inv = new Invite(sendTo.getUsername(), sentFrom.getUsername(), genInviteID());

          sendTo.addInviteGot(inv);
          sentFrom.addInviteSent(inv);
          db.insertInvite(inv.getInviteID(), inv.getInviter(), inv.getInvitee());

          if (serverCache.getConnection(sendTo.getUserID()) != null) {
            ServerSendsInvite response = new ServerSendsInvite(sentFrom.getUsername(), sendTo, inv.getInviteID());
            this.serverCache.getConnection(sendTo.getUserID()).sendData(response.getFile());
          }

          ServerRespondsInvite response2 = new ServerRespondsInvite(sentFrom);
          this.serverCache.getConnection(sentFrom.getUserID()).sendData(response2.getFile());
        }
    }

    private void handleClientRespondsInvite(Event e, Socket s) throws IOException {
        ClientRespondsInvite message = (ClientRespondsInvite) e;
        User sentUser = this.serverCache.getUser(s);
        User fromUser = serverCache.getUser(message.getUsername());
        int gID;
        fromUser.removeInviteSent(message.getInviteID());
        sentUser.removeInviteGot(message.getInviteID());
        gID = genGameID();
        Game newGame = new Game(gID,fromUser,sentUser);
        games.addGame(newGame);
        sentUser.addGame(newGame);
        fromUser.addGame(newGame);
     //   db.insertGame(newGame);
        db.insertGame(newGame.getGameID(), newGame);

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
                if(serverCache.matchPassword(message.getUsername(),message.getPassword())) {
                    user = serverCache.getUser(message.getUsername());
                    serverCache.getUserCon(socket).setConID(user.getUserID());
                    System.out.println("logged in");
                }
                else{
                    reason = "Incorrect Password!";
                }
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
        ClientSendsRegistration message = (ClientSendsRegistration) e;
        User user = null;
        String reason = "";
        if(checkPassword(message.getPassword())){
                if(!checkUsername(message.getUsername())){

                        user = new User(genUserID(), "", "", "");
                        user.setUsername(message.getUsername());
                        user.setPassword(message.getPassword());
                        user.setEmail(message.getEmail());
                        user.setSentInvites(new Invite[0]);
                        user.setGotInvites(new Invite[0]);
                        user.setGames(new Game[0]);
                        serverCache.getUserCon(socket).setConID(user.getUserID());
                        serverCache.addUser(user);
                        db.insertUser(user.getUserID(), user);
                    System.out.println("registered");
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
        Random random = new Random();
        ClientSendsLogout message = (ClientSendsLogout) e;
        int conid = random.nextInt();
        while(serverCache.containsConID(conid) || conid >0){
            conid = random.nextInt();
        }
        serverCache.getConnection(message.getuID()).setConID(conid);
    }

    private void handleClientSendsDeregister(Event e, Socket socket) throws IOException{
        System.err.println("processing deregistration");
        ClientSendsDeregister message = (ClientSendsDeregister) e;
        serverCache.removeUser(message.getUserID());

        db.removeUser(message.getUserID());
        ServerRespondsDeregister response = new ServerRespondsDeregister((User)null);
        serverCache.getUserCon(socket).sendData(response.getFile());
    }

    //Check each game and make sure the random numbe generated isn't already in use.
    private int genGameID(){
        Random random = new Random();
       int id = random.nextInt(Integer.MAX_VALUE);

       while(gameIDs.contains(id) || id <=0){
           id = random.nextInt(Integer.MAX_VALUE);
       }
       gameIDs.add(id);
       return id;
    }

    private boolean checkUsername(String username){
        if(username.equals("")){
            return true;
        }
        for(User u : this.serverCache.getAllUsers()){
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }


    private int genInviteID(){
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE);

        while(inviteIDs.contains(id) || id <=0){
            id = random.nextInt(Integer.MAX_VALUE);
        }
        inviteIDs.add(id);
        return id;
    }

    private int genUserID(){
        Random rand = new Random();
        int uID = rand.nextInt();
        while(serverCache.containsUserID(uID) || uID <0){uID = rand.nextInt();}
        return uID;
    }

    private boolean checkPassword(String pass){
        if(pass.equals("")){
            return false;
        }
        if(pass.matches(".*[;(),/}{\"\'].*") || pass.length() < 8){
            return false;
        }
        return true;
    }

    public static void main(String []args){
        Server s = new Server(35355);
        s.run();
    }
}
