package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPSenderThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.ClientMakeMove;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Event;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.ServerRespondsGameState;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Server implements Node {

    Game game = new Game();

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
        this.serverThread.run();
    }

    @Override
    public void onEvent(Event e, Socket socket) throws IOException {
        //React to messages sent to the server
        switch(e.getType()){

            case Client_Make_Move: handleMakeMove(e,socket);break;

            case Client_Request_Game_State:

            case Client_Sends_Game_Invite:

            case Client_Sends_Get_History:

            case Client_Sends_Login:

            case Client_Sends_Register:

            default:
        }
    }

    private void handleMakeMove(Event e ,Socket socket) throws IOException {
        ServerRespondsGameState message = new ServerRespondsGameState(game.getBoard());
        this.serverCache.getConnection(socket).sendData(message.getBytes());
        
    }

    //Start  Server
    public static void main(String args[]) throws NumberFormatException, IOException {
        Server s = new Server(5003,128);
        s.initiate();

    }
}
