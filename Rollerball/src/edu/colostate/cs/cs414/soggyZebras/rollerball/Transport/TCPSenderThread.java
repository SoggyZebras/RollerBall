package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Server;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;
import sun.awt.image.PixelConverter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class TCPSenderThread extends Thread{
    private SecureRandom rnd = new SecureRandom();
    private Node node;
    private Socket socket;
    private ObjectOutputStream dout;
    private MessageQueue queue;

    /**
     *
     * @param socket
     * @throws IOException
     */
    protected TCPSenderThread(Node node, Socket socket) throws IOException {
        this.node = node;
        this.socket = socket;
        this.queue = new MessageQueue();
        this.dout = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run(){
        while (socket != null) {
            forward(this.queue.take());
        }
    }

    protected void sendData(String dataToSend){
        this.queue.add(dataToSend);
    }

    private void forward(String dataToSend){
        try {
            synchronized (socket) {
                //Write data to the output stream
                dout.writeObject(dataToSend);
                dout.flush();

            }
            } catch(IOException e){
                System.out.println("IO Exception in TCP Sender Thread");
                try {
                    this.socket.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }
            }
        }


}
