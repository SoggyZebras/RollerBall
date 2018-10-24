package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import java.util.ArrayList;

public class MessageQueue {

    private ArrayList<byte[]> queue;

    protected MessageQueue() {
        this.queue = new ArrayList<byte[]>();
    }

    public synchronized void add(byte[] b) {
        if (this.isEmpty()) {
            this.queue.add(b);
            notifyAll();
        } else {
            this.queue.add(b);

        }
    }

    protected synchronized byte[] take() {
        try {
            if (this.isEmpty()) {
                wait();
                return this.queue.remove(0);
            } else {
                return this.queue.remove(0);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
