package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import java.util.ArrayList;

public class MessageQueue {

    private ArrayList<String> queue;

    protected MessageQueue() {
        this.queue = new ArrayList<String>();
    }

    public synchronized void add(String s) {
        // Add a string to the message queue

        //If the queue was empty, notify()
        if (this.isEmpty()) {
            this.queue.add(s);
            notifyAll();
        } else {
            this.queue.add(s);

        }
    }

    protected synchronized String take() {
        //Take a string from the message queue
        try {

            //If the queue is empty, wait untill there is something in it
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
