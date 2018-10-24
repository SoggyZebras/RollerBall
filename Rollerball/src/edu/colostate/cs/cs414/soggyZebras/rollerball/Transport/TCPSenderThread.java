package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPSenderThread implements Runnable{
    private Socket socket;
    private DataOutputStream dout;
    private MessageQueue queue;

    protected TCPSenderThread(Socket socket) {
        this.socket = socket;
        this.queue = new MessageQueue();
    }

    public void run() {
        while(socket != null){

            forward(this.queue.take());



        }
    }

    protected void sendData(byte[] dataToSend){
        this.queue.add(dataToSend);
    }

    private void forward(byte[] dataToSend) {
        synchronized(this.socket) {
            try {
                dout = new DataOutputStream(socket.getOutputStream());
                int dataLength = dataToSend.length;
                dout.writeInt(dataLength);
                dout.write(dataToSend, 0, dataLength);
                dout.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
