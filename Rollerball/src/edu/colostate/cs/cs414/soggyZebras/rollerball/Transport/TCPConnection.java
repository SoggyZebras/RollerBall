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
    private AES aes = null;
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
        secRand = new SecureRandom();

        BigInteger p = new BigInteger("13179394509466763138221553323842293368130732042012400728802565219135071314050996293375273427542995900208096376809564770263078506883407384430879468894721377");
        BigInteger g = new BigInteger("7963168919447132983818442271245611164210363988091292494795179291105111048964748254069972867853133193415971572362952980987181916888444753431478184844368864");
        BigInteger a =  new BigInteger(p.bitLength(),secRand);
        this.aes = new AES();


        //create receiver socket to communicate
        this.senSocket = new TCPSenderThread(this.node,this.serverSocket,aes,g,a,p);
        this.recSocket = new TCPReceiverThread(this.node,server,aes,a,p);

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
