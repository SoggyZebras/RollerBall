package edu.colostate.cs.cs414.soggyZebras.rollerball.Server;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPSenderThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Event;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.net.Socket;

public class Server implements Node {

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
    public void onEvent(Event e, Socket socket) {
        //React to messages sent to the server
        switch(e.getType()){

            case Client_Make_Move: System.out.print("revieved make move");

            case Client_Request_Game_State:

            case Client_Sends_Game_Invite:

            case Client_Sends_Get_History:

            case Client_Sends_Login:

            case Client_Sends_Register:

            default:
        }
    }

    //Start  Server
    public static void main(String args[]) throws NumberFormatException, IOException {
        Server s = new Server(5000,5);
        s.initiate();

    }
}
