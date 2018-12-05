package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;

public class TCPConnection {
    int BIT_LENGTH = 2048;
    private TCPSenderThread senSocket;
    private TCPReceiverThread recSocket;
    private Socket serverSocket;
    private Node node;
    private int ID;
    private RSA rsa;
    private AES aes;
    private SecureRandom secRand;

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
        rsa = new RSA();
        aes = new AES();

        BigInteger p = BigInteger.probablePrime(BIT_LENGTH/2, secRand);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH/2,secRand);
        rsa.generateN(p,q);
        rsa.generatephiN(p,q);
        BigInteger e = new BigInteger(rsa.getPhiN().bitLength(),secRand);
        while(e.compareTo(BigInteger.ONE) <= 0
                || e.compareTo(rsa.getPhiN()) >= 0
                || !e.gcd(rsa.getPhiN()).equals(BigInteger.ONE)){
            e = new BigInteger(rsa.getPhiN().bitLength(),secRand);
        }
        rsa.setE(e);
        rsa.generateD();


        //create receiver socket to communicate
        this.senSocket = new TCPSenderThread(this.node,this.serverSocket,rsa,aes);
        this.recSocket = new TCPReceiverThread(this.node,server,rsa,aes);

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
