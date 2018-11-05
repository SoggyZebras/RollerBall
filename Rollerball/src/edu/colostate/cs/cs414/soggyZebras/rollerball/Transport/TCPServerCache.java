package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class TCPServerCache {

    private ArrayList<TCPConnection> cache;

    /**
     *
     * @param i
     */
    public TCPServerCache(int i){
        //initializes the arraylist
        this.cache = new ArrayList<TCPConnection>(i);
    }

    protected void addConnection(TCPConnection c) {
        //add TCPConnection to the cache
        this.cache.add(c);
    }

    /**
     *
     * @param s
     * @return TCPConnection
     */
    public TCPConnection getConnection(Socket s) {
        //access TCPConnection from the cache
        for(int i = 0; i < cache.size();i++) {
            if(cache.get(i).getSocket() == s) {
                return cache.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param ip
     * @param p
     * @return TCPConnection
     */
    public TCPConnection getConnection(byte[] ip, int p) {
        for(int i = 0; i < this.cache.size();++i) {
            if(cache.get(i).getSocket().getPort() == p) {
                if(Arrays.equals(cache.get(i).getSocket().getInetAddress().getAddress(),ip)) {
                    return cache.get(i);
                }
            }
        }
        return null;
    }
}
