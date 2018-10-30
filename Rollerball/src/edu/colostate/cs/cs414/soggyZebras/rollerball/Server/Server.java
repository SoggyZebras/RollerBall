package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.testgames.TwoRooks;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.*;

import java.io.IOException;
import java.net.Socket;

public class Server implements Node {

    // NOTE: change type to TwoRooks for the demo
    Game game = new TwoRooks();

    //=======NETWORK SETUP=======//

    //Local variables for networking
    private int serverPort;
    private TCPServerCache serverCache;
    private TCPServerThread serverThread;

    private Server(int port,int numConnections){

        //instantiate variables and create required threads
        this.serverPort = port;
        this.serverCache = new TCPServerCache(numConnections);
        this.serverThread = new TCPServerThread(this, serverCache, this.serverPort);

    }

    private void initiate(){
        //Start server thread(send/receive threads)
        this.serverThread.run();
    }

    @Override
    public void onEvent(Event e, Socket socket) throws IOException, ClassNotFoundException {
        //React to messages sent to the server
        switch(e.getType()){

            case Client_Make_Move: handleMakeMove(e,socket);break;

            case Client_Request_Game_State:

            case Client_Request_Check_Move: handleCheckMove(e,socket);break;

            case Client_Sends_Game_Invite:

            case Client_Sends_Get_History:

            case Client_Sends_Login:

            case Client_Sends_Register:

            default:
        }
    }

    private void handleMakeMove(Event e ,Socket socket) throws IOException {
        //When client requests to make a move, TODO: alter the game state
        //Return new game state
        handleClientRequestGameState(e,socket);
        
    }

    private void handleClientRequestGameState(Event e, Socket socket) throws IOException {
        //When client asks for a new game state, create a wireformat and send it to the client
        ServerRespondsGameState message = new ServerRespondsGameState(game.getBoard());
        this.serverCache.getConnection(socket).sendData(message.getFile());
    }

    private void handleCheckMove(Event e, Socket socket) throws IOException, ClassNotFoundException {
        //When client asks for available spaces, get possible moves from game
        ClientRequestsCheckMove inMessage = (ClientRequestsCheckMove) e;
        ServerRespondsCheckMove outMessage = new ServerRespondsCheckMove(game.validMoves(inMessage.getPlace()));
        this.serverCache.getConnection(socket).sendData(outMessage.getFile());
    }

    //Start  Server
    public static void main(String args[]) throws NumberFormatException {
        Server s = new Server(5003,128);
        s.initiate();

    }
}
