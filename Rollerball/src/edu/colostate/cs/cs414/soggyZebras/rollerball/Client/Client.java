package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.game.RollerballPanel;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerThread;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class Client implements Node {

    RollerballPanel gui;
    User myUser;

    // TODO: does Client need a game object?
    private boolean debug = true;

    //========== NETWORK SETUP ==========//

    //Local Networking Variables
    private String serverHost;
    private int serverPort;
    private Socket serverSocket;
    private TCPConnection serverConnection;

    /**
     *
     * @param serverAddress
     * @param serverPort
     * @throws IOException
     */
    public Client(String serverAddress, int serverPort) throws IOException {

        // Check if the arguments have been passed correctly
        if (serverAddress == null){
            throw new IOException("Invalid serverAddress");
        }

        //Set server address and port
        this.serverHost = serverAddress;
        this.serverPort = serverPort;

    }

    /**
     *
     * @throws IOException
     */
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
            case Server_Sends_Connect: handleServerSendsConnect(e);break;

            case Server_Responds_Game_State:handleGameState(e);break;

            case Server_Responds_Check_Move: handleServerCheckMove(e);break;

            case Server_Sends_Invite: handleServerSendsInvite(e,socket);break;

            case Server_Responds_Invite: handleServerRespondsInvite(e);break;

            case Server_Responds_Registration: handleServerRespondsRegistration(e,socket);break;

            case Server_Responds_Login: handleServerRespondsLogin(e,socket);break;

            case Server_Responds_Invite_Refresh: handleServerRespondsRefresh(e,socket);break;

            default:
        }

    }

    private void handleServerSendsConnect(Event e){
        ServerSendsConnect message = (ServerSendsConnect) e;
        this.myUser = message.getUser();
    }

    //========= END NETWORK SETUP =========//


    //============ INTERFACE ==============//


    /**
     *
     * @param from
     * @param to
     * @return boolean
     */
    public boolean makeMove(Location from, Location to, int gID) {
        // Create make move wireformat with given variables and send to server
        try {
            ClientMakeMove moveMessage = new ClientMakeMove(from, to, gID);
            serverConnection.sendData(moveMessage.getFile());

        } catch (Exception e){
            if(debug){
                System.out.println(e.getMessage());
            }
            return false;
        }
        return true;
    }

    /**
     *
     * @return boolean
     */
    public boolean getGameState(int gID) {
        // Create get game state wireformat and send it to the server
        try {
            ClientRequestGameState updateMessage = new ClientRequestGameState(gID);
            serverConnection.sendData(updateMessage.getFile());
        } catch (IOException eio){
            eio.getCause();
            return false;
        }
        return true;
    }

    /**
     *
     * @param place
     * @return boolean
     */
    public boolean checkValidMove(Location place, int gID) throws ClassNotFoundException {
        //Ask the server for the valid moves of a board tile
        try {
            ClientRequestsCheckMove checkMessage = new ClientRequestsCheckMove(place, gID);
            serverConnection.sendData(checkMessage.getFile());
            return true;
        } catch(IOException e){
            e.getCause();
            return false;
        }
    }






    //========== END INTERFACE ===========//


    /**
     *
     * @param e
     */
    private void handleGameState(Event e) {
        // When server sends an updated game state, recompile the game and give it to the ui
        ServerRespondsGameState message = (ServerRespondsGameState) e;
        this.gui.updateState(message.getMap());
    }

    /**
     *
     * @param e
     */
    private void handleServerCheckMove(Event e){
        ServerRespondsCheckMove message = (ServerRespondsCheckMove) e;
        gui.updateValidMoves(message.getList());
    }

    private void handleServerRespondsInvite(Event e){
        ServerRespondsInvite message = (ServerRespondsInvite) e;
        if(message.getGameID() == -1){
            myUser = message.getUserFrom();
        }
        else{
            //TODO pass id of new game to gui and update invites list
            //get back User other player, int game id
        }
    }

    private void handleServerRespondsLogin(Event e, Socket socket){
        //TODO process event to see if the login was successful
    }

    private void handleServerRespondsRegistration(Event e, Socket socket){
        //TODO process event to see if the registration was successful or not
    }

    private void handleServerRespondsRefresh(Event e, Socket socket){
        //TODO pass user and game object to GUI
    }

    private void handleServerSendsInvite(Event e, Socket socket){
        //TODO handle getting an invite from another user
        ServerSendsInvite message = (ServerSendsInvite) e;

    }

    public void setGui(RollerballPanel p){
        this.gui = p;
    }

}
