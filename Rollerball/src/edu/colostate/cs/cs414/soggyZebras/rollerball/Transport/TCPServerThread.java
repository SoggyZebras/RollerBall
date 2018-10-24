package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerThread implements Runnable{

    private ServerSocket svSocket = null;
    private TCPServerCache cache = null;
    private Socket regSocket = null;
    private TCPConnection connection = null;
    private Node node = null;

    public TCPServerThread(Node node, TCPServerCache c) {
        try {
            this.node = node;
            svSocket = new ServerSocket(0);
            System.out.println("Listening on port " + svSocket.getLocalPort());
            this.cache = c;
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public TCPServerThread(Node node, TCPServerCache c, int port) {
        try {
            this.node = node;
            svSocket = new ServerSocket(port);
            System.out.println("Listening on port " + port);
            this.cache = c;
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }


    @Override
    public void run() {

        while(true) {
            try {
                //Accept incoming connection
                this.regSocket = svSocket.accept();
                connection = new TCPConnection(node,regSocket);
                connection.initiate();
                this.cache.addConnection(connection);


            }
            catch(IOException ioe) {
                try {
                    this.regSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public synchronized int getPort() {
        return this.svSocket.getLocalPort();
    }

    public synchronized Socket getSocket() {
        return this.regSocket;
    }
}
