package edu.colostate.cs.cs414.soggyZebras.rollerball.Client;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client {

    // TODO: does Client need a game object?
    boolean debug = false;

    //========== NETWORK SETUP ==========//

    //Local Networking Variables
    String serverHost;
    int serverPort;
    Selector selector;

    public Client(String serverAddress, int serverPort) throws IOException {

        // Check if the arguments have been passed correctly
        if (serverAddress == null){
            throw new IOException("Invalid serverAddress");
        }

        // Try to assign given variables and create the selector
        try{
            this.serverHost = serverAddress;
            this.serverPort = serverPort;
            this.selector = Selector.open();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void initialize() throws IOException, InterruptedException {

        // print debuggin info
        if(debug){
            System.out.println("Starting Client thread");
        }

        this.startClient();
    }

    private void startClient() throws IOException, InterruptedException {

        // Create a socket for the client           [1]
        // configure the socket for non-blocking    [2]
        // register socket with the selector        [3]
        // connect channel to server                [4]
        // connect selector key to server           [5]

        SocketChannel channel = SocketChannel.open();                           //[1]
        channel.configureBlocking(false);                                       //[2]
        channel.register(selector, SelectionKey.OP_CONNECT);                    //[3]
        channel.connect(new InetSocketAddress(this.serverHost,this.serverPort));//[4]

        selector.select();

        Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();  //[5]
        SelectionKey key = keys.next();
        if(key.isConnectable()){
            this.connect(key);
        }

    }

    private void connect(SelectionKey key) throws IOException {
        // finish setting up the connection to the server
        SocketChannel channel = (SocketChannel) key.channel();
        channel.finishConnect();
        key.interestOps(SelectionKey.OP_WRITE);
    }

    //========= END NETWORK SETUP =========//

    public boolean makeMove(Location from, Location to) {
        return false;
    }

    public Game getGameState() {
        return new Game();
    }

}
