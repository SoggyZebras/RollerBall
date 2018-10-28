package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.RollerballPanel;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Node {

    RollerballPanel gui;

    // TODO: does Client need a game object?
    private boolean debug = true;

    //========== NETWORK SETUP ==========//

    //Local Networking Variables
    private String serverHost;
    private int serverPort;
    private Socket serverSocket;
    private TCPConnection serverConnection;


    public Client(String serverAddress, int serverPort) throws IOException {

        // Check if the arguments have been passed correctly
        if (serverAddress == null){
            throw new IOException("Invalid serverAddress");
        }

        //Set server address and port
        this.serverHost = serverAddress;
        this.serverPort = serverPort;

    }

    public void initialize() throws IOException {

        // print debuggin info
        if(debug){
            System.out.println("Starting Client thread");
        }
        //Setup a connection to the server
        serverSocket = new Socket(InetAddress.getByName(this.serverHost),this.serverPort);
        serverConnection = new TCPConnection(this, serverSocket);
        serverConnection.initiate();
    }

    @Override
    public void onEvent(Event e, Socket socket) {

        switch(e.getType()){
            case Server_Responds_Game_State:handleGameState(e);break;

            case Server_Responds_Check_Move: handleServerCheckMove(e);break;

            case Server_Responds_Game_Invite:

            case Server_Responds_Get_History:

            case Server_Responds_Register:

            case Server_Responds_Login:

            default:
        }

    }

    //========= END NETWORK SETUP =========//

    public boolean makeMove(Location from, Location to) {
        // Create make move wireformat with given variables and send to server
        try {
            ClientMakeMove moveMessage = new ClientMakeMove(from, to);
            serverConnection.sendData(moveMessage.getFile());

        } catch (Exception e){
            if(debug){
                System.out.println(e.getMessage());
            }
            return false;
        }
        return true;
    }

    public boolean getGameState() {
        // Create get game state wireformat and send it to the server
        try {
            ClientRequestGameState updateMessage = new ClientRequestGameState();
            serverConnection.sendData(updateMessage.getFile());
        } catch (IOException eio){
            eio.getCause();
            return false;
        }
        return true;
    }

    public boolean checkValidMove(Location place){
        //Ask the server for the valid moves of a board tile
        try {
            ClientRequestsCheckMove checkMessage = new ClientRequestsCheckMove(place);
            serverConnection.sendData(checkMessage.getFile());
            return true;
        } catch(IOException e){
            e.getCause();
            return false;
        }
    }

    private void handleGameState(Event e) {
        // When server sends an updated game state, recompile the game and give it to the ui
        ServerRespondsGameState message = (ServerRespondsGameState)e;
        Game g = new Game(message.getMap());
        this.gui.updateState(g);
    }

    private void handleServerCheckMove(Event e){
        ServerRespondsCheckMove message = (ServerRespondsCheckMove) e;
    }

    public void setDebug(){
        this.debug = true;
    }

    public void setGui(RollerballPanel p){
        this.gui = p;
    }

}
