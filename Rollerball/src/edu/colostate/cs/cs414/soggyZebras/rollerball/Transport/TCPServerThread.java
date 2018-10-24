package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerThread implements Runnable{

    private ServerSocket serverSocket = null;
    private TCPServerCache serverCache = null;
    private Socket socket = null;
    private TCPConnection connection = null;
    private Node node = null;

    public TCPServerThread(Node node, TCPServerCache c) {
        try {
            this.node = node;
            serverSocket = new ServerSocket(5000);
            System.out.println("Listening on port " + serverSocket.getLocalPort());
            this.serverCache = c;
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public TCPServerThread(Node node, TCPServerCache c, int port) {
        try {
            this.node = node;
            serverSocket = new ServerSocket(port);
            System.out.println("Listening on port " + port);
            this.serverCache = c;
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }


    @Override
    public void run() {

        while(true) {
            try {
                //Accept incoming connection
                this.socket = serverSocket.accept();
                connection = new TCPConnection(node,socket);
                connection.initiate();
                this.serverCache.addConnection(connection);

            }
            catch(IOException ioe) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public synchronized int getPort() {
        return this.serverSocket.getLocalPort();
    }

    public synchronized Socket getSocket() {
        return this.socket;
    }
}
