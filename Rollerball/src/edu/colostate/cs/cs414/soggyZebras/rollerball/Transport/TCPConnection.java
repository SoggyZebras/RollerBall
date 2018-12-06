package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;

public class TCPConnection {
    int BIT_LENGTH = 1024;
    private TCPSenderThread senSocket = null;
    private TCPReceiverThread recSocket = null;
    private Socket serverSocket = null;
    private Node node = null;
    private int ID;
    private SecureRandom secRand = null;


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
        this.senSocket = new TCPSenderThread(this.node,this.serverSocket);
        this.recSocket = new TCPReceiverThread(this.node,server);

    }


    public void initiate() {

        this.senSocket.start();
        this.recSocket.start();

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

    public void setConID(int id) { this.ID = id;}

}
