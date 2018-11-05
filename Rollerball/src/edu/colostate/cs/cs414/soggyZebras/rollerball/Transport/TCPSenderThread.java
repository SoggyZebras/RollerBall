package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPSenderThread implements Runnable{
    private Socket socket;
    private ObjectOutputStream oout;
    private MessageQueue queue;

    /**
     *
     * @param socket
     * @throws IOException
     */
    protected TCPSenderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.queue = new MessageQueue();
        this.oout = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run() {
        while(socket != null){

            forward(this.queue.take());



        }
    }

    protected void sendData(String dataToSend){
        this.queue.add(dataToSend);
    }

    private void forward(String dataToSend) {
        synchronized(this.socket) {
            try {

                //Write data to the output stream
                oout.writeObject(dataToSend);
                oout.flush();


            } catch (IOException e) {
                System.out.println("IO Exception in TCP Sender Thread");
                e.printStackTrace();
            }
        }
    }
}
