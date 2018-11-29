package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Server implements Node,Runnable {


    GameCache games;
    ArrayList<Integer> gameIDs;
    ArrayList<Integer> inviteIDs;
    Random random;

    //=======NETWORK SETUP=======//

    //Local variables for networking
    private int serverPort;
    private TCPServerCache serverCache;
    private TCPServerThread serverThread;
    ArrayList<User> userList;

    public Server(int port){

        //instantiate variables and create required threads
        this.serverPort = port;
        this.serverCache = new TCPServerCache();
        games = new GameCache();
        this.serverThread = new TCPServerThread(this, serverCache, this.serverPort);

    }


    public void run(){
        //Start server thread(send/receive threads
        games.addGame(new Game(0));

        this.serverThread.run();
    }

    @Override
    public void onEvent(Event e, Socket socket) throws IOException, ClassNotFoundException {
        //React to messages sent to the server
        switch(e.getType()){

            case Client_Make_Move: handleMakeMove(e,socket);break;

            case Client_Request_Game_State:

            case Client_Request_Check_Move: handleCheckMove(e,socket);break;

            case Client_Sends_Game_Invite: handleClientSendsInvite(e,socket);break;

            case Client_Sends_Get_History:

            case Client_Sends_Login:

            case Client_Sends_Register:

            default:
        }
    }

    private void handleMakeMove(Event e ,Socket socket) throws IOException {
        //When client requests to make a move, TODO: alter the game state
        ClientMakeMove message =(ClientMakeMove) e;
       games.getGame(message.getGameID()).makeMove(message.getTo(),message.getFrom());
        handleClientRequestGameState(e,socket);
        
    }

    private void handleClientRequestGameState(Event e, Socket socket) throws IOException {
        //When client asks for a new game state, create a wireformat and send it to the client
        ClientRequestGameState message = (ClientRequestGameState) e;
        ServerRespondsGameState response = new ServerRespondsGameState(games.getGame(message.getGameID()).getBoard(), message.getGameID());
        this.serverCache.getUser(socket).sendData(response.getFile());
    }

    private void handleCheckMove(Event e, Socket socket) throws IOException, ClassNotFoundException {
        //When client asks for available spaces, get possible moves from game
        ClientRequestsCheckMove message = (ClientRequestsCheckMove) e;

        ServerRespondsCheckMove response = new ServerRespondsCheckMove(games.getGame(message.getGameID()).validMoves(message.getPlace()),message.getGameID());

        this.serverCache.getUser(socket).sendData(response.getFile());
    }

    private void handleClientSendsInvite(Event e, Socket socket){
        ClientSendsInvite message = (ClientSendsInvite) e;
        User sentFrom = this.serverCache.getUser(socket);
        Invite inv = new Invite(sentFrom,message.getUserTo(),genInviteID());
        message.getUserTo().addInviteSent(inv);
        sentFrom.addInviteGot(inv);

    }

    //Check each game and make sure the random numbe generated isn't already in use.
    private int genGameID(){
       int id = random.nextInt();

       while(!gameIDs.contains(id)){
           id = random.nextInt();
       }
       gameIDs.add(id);
       return id;
    }

    private int genInviteID(){
        int id = random.nextInt();

        while(!inviteIDs.contains(id)){
            id = random.nextInt();
        }
        inviteIDs.add(id);
        return id;
    }

    public static void main(String []args){
        Server s = new Server(5000);
        s.run();
    }
}
