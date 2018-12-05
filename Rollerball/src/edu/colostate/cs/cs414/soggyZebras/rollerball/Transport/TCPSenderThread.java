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
    private RSA rsa;
    private AES aes;
    private boolean first = true;
    private boolean second = true;

    /**
     *
     * @param socket
     * @throws IOException
     */
    protected TCPSenderThread(Node node, Socket socket, RSA rsa, AES aes) throws IOException {
        this.node = node;
        this.socket = socket;
        this.queue = new MessageQueue();
        this.dout = new ObjectOutputStream(socket.getOutputStream());
        this.rsa = rsa;
        this.aes = aes;
    }

    public void run(){
        while (socket != null) {

            try {
                if(first){

                    dout.writeObject(this.rsa.getMyE());
                    dout.writeObject(this.rsa.getN());
                    dout.flush();
                    first = false;
                }
                else if(second){
                    if(node instanceof Server) {
                        String secretKey = randomString(64);
                        aes.setSecret(secretKey);
                        System.out.println(secretKey);
                        dout.writeObject(rsa.encrypt(secretKey));
                        dout.flush();
                    }
                    second = false;
                }
                else {
                    forward(this.queue.take());
                }
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
                dout.writeObject(aes.encrypt(dataToSend,aes.getSecret()));
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
