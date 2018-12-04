package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPSenderThread extends Thread{
    private Socket socket;
    private ObjectOutputStream dout;
    private MessageQueue queue;
    private boolean closed = false;

    /**
     *
     * @param socket
     * @throws IOException
     */
    protected TCPSenderThread(Socket socket) throws IOException {
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
                this.closed = true;
            }
        }
    }
}
