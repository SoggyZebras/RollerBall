package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Server;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.EventFactory;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Base64;

public class TCPReceiverThread extends Thread implements Serializable{
    private Socket socket;
    private ObjectInputStream din;
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
            this.din = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("bad input stream");
            e.printStackTrace();
        }
        while(socket != null){
            try {
                //Read data from input stream
                String data = (String)din.readObject();
                byte[] dat = Base64.getDecoder().decode(data);
                ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(dat));
                int i = oin.readInt();
                EventFactory.work(data,i,this.node,this.socket);
            }
            catch(SocketException se) {
                System.out.println("Socket Exception in TCP Receiver Thread");
                break;
            }
            catch(IOException ioe) {
                System.out.println("IO Exception in TCP Receiver Thread");
                ioe.printStackTrace();
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
