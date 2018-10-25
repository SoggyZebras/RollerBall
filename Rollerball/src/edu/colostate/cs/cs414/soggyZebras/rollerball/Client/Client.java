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

        // Try to assign given variables and create the selector
        try{
            this.serverHost = serverAddress;
            this.serverPort = serverPort;
            initialize();

        } catch(IOException e){
            if(debug) {
                e.printStackTrace();
            }
        }
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
    public void onEvent(Event e, Socket socket) throws IOException, ClassNotFoundException {

        switch(e.getType()){
            case Server_Responds_Game_State:handleGameState(e);break;

            case Server_Responds_Game_Invite:

            case Server_Responds_Get_History:

            case Server_Responds_Register:

            case Server_Responds_Login:

            default:
        }

    }

    //========= END NETWORK SETUP =========//

    public boolean makeMove(Location from, Location to) {
        // Create wireformat with given variables and send to server
        try {
            ClientMakeMove moveMessage = new ClientMakeMove(from, to);
            serverConnection.sendData(moveMessage.getBytes());
            System.out.println(serverConnection.getSocket().getPort());
        } catch (Exception e){
            if(debug){
                System.out.println(e.getMessage());
            }
            return false;
        }
        return true;
    }

    public boolean getGameState(Event e, Socket socket) throws IOException {
        try {
            ClientRequestGameState updateMessage = new ClientRequestGameState();
            serverConnection.sendData(updateMessage.getBytes());
        } catch (IOException eio){
            eio.getCause();
            return false;
        }
        return true;
    }

    private void handleGameState(Event e) throws IOException, ClassNotFoundException {
        ServerRespondsGameState message = new ServerRespondsGameState(e.getBytes());
        Game g = new Game(message.getMap());
        this.gui.updateState(g);
    }

    public void setDebug(){
        this.debug = true;
    }

    public void setGui(RollerballPanel p){
        this.gui = p;
    }

}
