package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.net.Socket;

public class TCPConnection {
    private TCPSenderThread senSocket;
    private TCPReceiverThread recSocket;
    private Socket serverSocket;
    private Node node;

    public TCPConnection(Node node,Socket server) throws IOException {
        //create TCP sender with socket given
        this.serverSocket = server;
        this.node = node;

        //create receiver socket to communicate
        this.recSocket = new TCPReceiverThread(this.node,server);
        this.senSocket = new TCPSenderThread(this.serverSocket);

    }

    public void initiate() {
        new Thread(this.recSocket).start();
        new Thread(this.senSocket).start();

    }

    public void sendData(byte[] data){
        // send data to queue
        this.senSocket.sendData(data);

    }

    protected Socket getSocket() {
        return this.serverSocket;
    }

    public Node getNode() {
        return this.node;
    }
}
