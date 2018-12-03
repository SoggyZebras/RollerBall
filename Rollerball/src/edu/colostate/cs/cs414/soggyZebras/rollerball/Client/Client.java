package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu.MenuGUI;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class Client implements Node {

    MenuGUI gui;

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
        serverConnection = new TCPConnection(this, serverSocket,0);
        serverConnection.initiate();
    }

    @Override
    public void onEvent(Event e, Socket socket) throws IOException{

        switch(e.getType()){

            case Server_Responds_Check_Move: handleServerCheckMove(e);break;

            case Server_Sends_Invite: handleServerSendsInvite(e,socket);break;

            case Server_Responds_Invite: handleServerRespondsInvite(e);break;

            case Server_Responds_Registration: handleServerRespondsRegistration(e,socket);break;

            case Server_Responds_Login: handleServerRespondsLogin(e,socket);break;

            case Server_Responds_Refresh: handleServerRespondsRefresh(e,socket);break;

            case Server_Responds_Deregister: handleServerRespondsDeregister(e, socket);break;

            default:
        }

    }


    //========= END NETWORK SETUP =========//


    //============ INTERFACE ==============//


    public void sendInvite(String name) throws IOException{
        ClientSendsInvite message = new ClientSendsInvite(name,0);
        serverConnection.sendData(message.getFile());
    }

    public void respondInvite(String name, int id) throws IOException{
        ClientRespondsInvite message = new ClientRespondsInvite(name,id);
        serverConnection.sendData(message.getFile());
    }

    public void register(String username, String password, String email) throws IOException{
        ClientSendsRegistration message = new ClientSendsRegistration(username,password,email);
        serverConnection.sendData(message.getFile());
    }

    public void login(String username, String password) throws IOException{
        ClientSendsLogin message = new ClientSendsLogin(username,password);
        serverConnection.sendData(message.getFile());
    }

    public void logout(int uid) throws IOException{
        ClientSendsLogout message = new ClientSendsLogout(uid);
        serverConnection.sendData(message.getFile());
    }

    public void deregister(int id) throws IOException{
        ClientSendsDeregister message = new ClientSendsDeregister(id);
        serverConnection.sendData(message.getFile());
    }

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
    public boolean checkValidMove(Location place, int gID) {
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

    public void refreshClient(int userID) throws IOException {
        ClientSendsRefresh message = new ClientSendsRefresh(userID);
        serverConnection.sendData(message.getFile());
    }




    //========== END INTERFACE ===========//



    //============ HANDLES ===============//


   /*
    private void handleGameState(Event e) {
        // When server sends an updated game state, recompile the game and give it to the ui
        ServerRespondsGameState message = (ServerRespondsGameState) e;
        this.gui.updateState(message.getMap());
    }
    */

    /**
     *
     * @param e
     */
    private void handleServerCheckMove(Event e){
        ServerRespondsCheckMove message = (ServerRespondsCheckMove) e;
        gui.updateValidMoves(message.getGameID(), message.getList());
    }

    private void handleServerRespondsInvite(Event e){
        ServerRespondsInvite message = (ServerRespondsInvite) e;
        gui.refresh(message.getUser());
    }

    private void handleServerRespondsLogin(Event e, Socket socket){
        ServerRespondsLogin message = (ServerRespondsLogin) e;
        gui.onLoginResponse(message.getUser(), message.getReject_reason());

    }

    private void handleServerRespondsRegistration(Event e, Socket socket) throws IOException{
        System.err.println("handling registration from server");
        ServerRespondsRegistration message = (ServerRespondsRegistration) e;
        gui.onRegisterResponse(message.getUser(), message.getReason());
        // TODO: should we call logout here?
        logout(message.getUser().getUserID());
    }

    private void handleServerRespondsRefresh(Event e, Socket socket){
        ServerRespondsRefresh message = (ServerRespondsRefresh) e;
        gui.refresh(message.getUser());

    }

    private void handleServerSendsInvite(Event e, Socket socket){
        ServerSendsInvite message = (ServerSendsInvite) e;
        gui.refresh(message.getUserTo());

    }

    private void handleServerRespondsDeregister(Event e, Socket socket){
        ServerRespondsDeregister message = (ServerRespondsDeregister) e;
        gui.refresh(message.getUser());
    }


    //========= END HANDLES ========//


    public void setGui(MenuGUI p){
        this.gui = p;
    }

}
