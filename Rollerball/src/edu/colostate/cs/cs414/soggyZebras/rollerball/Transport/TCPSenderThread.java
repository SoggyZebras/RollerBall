package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Server;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Node;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class TCPSenderThread extends Thread{
    private String KEY_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private SecureRandom rnd = new SecureRandom();
    private Node node;
    private Socket socket;
    private ObjectOutputStream dout;
    private MessageQueue queue;

    private boolean first = true;
    private boolean second = true;

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

            try {
                forward(this.queue.take());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    protected void sendData(String dataToSend){
        this.queue.add(dataToSend);
    }

    private void forward(String dataToSend) throws IOException{
        synchronized(this.socket) {
            try {

                //Write data to the output stream
                dout.writeObject(dataToSend);
                dout.flush();



            } catch (IOException e) {
                System.out.println("IO Exception in TCP Sender Thread");
                this.socket.close();
            }
        }
    }

    String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( KEY_SET.charAt( rnd.nextInt(KEY_SET.length())));
        return sb.toString();
    }
}
