package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPServerCache;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.ServerSendsConnect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class TCPServerThread implements Runnable{

    private ServerSocket serverSocket = null;
    private TCPServerCache serverCache = new TCPServerCache();
    private Socket socket = null;
    private TCPConnection connection = null;
    private Node node = null;
    private Random rand = new Random();
    private ArrayList<Integer> userNumbers;

    /**
     *
     * @param node
     * @param c
     */
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

    /**
     *
     * @param node
     * @param c
     * @param port
     */
    public TCPServerThread(Node node, TCPServerCache c, int port) {
        try {
            this.node = node;
            serverSocket = new ServerSocket(port);
            this.serverCache = c;
            userNumbers = new ArrayList<>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

    }

    public synchronized void removeUID(int uid){
        userNumbers.remove(uid);
    }

    @Override
    public void run() {
        System.out.println("Listening on port " + this.serverSocket.getLocalPort());
        while(true) {
            try {
                //Accept incoming connection
                this.socket = serverSocket.accept();
                connection = new TCPConnection(node,socket);
                connection.initiate();

                //get random user ID number
                int uID = rand.nextInt();
                while(userNumbers.contains(uID)){uID = rand.nextInt();}
                userNumbers.add(uID);

                //populate the server cache with a new user
                User tmp = new User(uID,"","","",connection);
                this.serverCache.addUser(tmp);
                connection.sendData(new ServerSendsConnect(tmp).getFile());

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

    /**
     *
     * @return int
     */
    public synchronized int getPort() {
        return this.serverSocket.getLocalPort();
    }

    /**
     *
     * @return Socket
     */
    public synchronized Socket getSocket() {
        return this.socket;
    }
}
