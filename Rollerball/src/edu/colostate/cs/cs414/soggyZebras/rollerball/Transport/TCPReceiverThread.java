package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Server;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.EventFactory;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

public class TCPReceiverThread extends Thread implements Serializable{
    private Socket socket;
    private ObjectInputStream oin;
    private Node node;

    /**
     *
     * @param node
     * @param socket
     */
    protected TCPReceiverThread(Node node,Socket socket) {
        this.socket = socket;
        this.node = node;

    }

    public void run() {
        try {
            this.oin = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("bad input stream");
            e.printStackTrace();
        }
        while(socket != null){
            try {
                //Read data from input stream
                String data = (String)oin.readObject();

                EventFactory.work(data,this.node,this.socket);
            }
            catch(SocketException se) {
                System.out.println("Socket Exception in TCP Receiver Thread");
                break;
            }
            catch(IOException ioe) {
                System.out.println("IO Exception in TCP Receiver Thread");
                break;
            } catch (ClassNotFoundException e) {
                System.out.println("IO Exception in TCP Receiver Thread");
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @return Socket
     */
    public Socket getSocket() {
        return this.socket;
    }
}
