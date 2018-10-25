package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Server;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.EventFactory;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class TCPReceiverThread implements Runnable{
    private Socket socket;
    private DataInputStream din;
    private Node node;

    protected TCPReceiverThread(Node node,Socket socket) throws IOException {
        this.socket = socket;
        this.node = node;
        din = new DataInputStream(socket.getInputStream());
    }

    public void run() {
        int dataLength;
        while(socket != null){
            try {
                //read the length of the byte[]
                dataLength = din.readInt();

                //read the byte[] fully
                byte[] data = new byte[dataLength];
                din.readFully(data, 0, dataLength);

                EventFactory.work(data,this.node,this.socket);
            }
            catch(SocketException se) {
                break;
            }
            catch(IOException ioe) {
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return this.socket;
    }
}
