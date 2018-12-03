package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.net.Socket;

public class TCPConnection {
    private TCPSenderThread senSocket;
    private TCPReceiverThread recSocket;
    private Socket serverSocket;
    private Node node;
    private int ID;

    /**
     *
     * @param node
     * @param server
     * @throws IOException
     */
    public TCPConnection(Node node,Socket server,int id) throws IOException {
        //create TCP sender with socket given
        this.serverSocket = server;
        this.node = node;
        this.ID = id;

        //create receiver socket to communicate
        this.recSocket = new TCPReceiverThread(this.node,server);
        this.senSocket = new TCPSenderThread(this.serverSocket);

    }


    public void initiate() {

        this.recSocket.start();
        this.senSocket.start();

    }

    /**
     *
     * @param data
     */
    public void sendData(String data){
        // send data to queue
        this.senSocket.sendData(data);

    }

    /**
     *
     * @return Socket
     */
    protected Socket getSocket() {
        return this.serverSocket;
    }

    public Node getNode() {
        return this.node;
    }

    public int getConID() { return this.ID;}

}
