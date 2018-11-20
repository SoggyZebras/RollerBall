package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class TCPServerCache {

    private ArrayList<User> cache;

    public TCPServerCache(){
        //initializes the arraylist
        this.cache = new ArrayList<User>();
    }

    protected void addUser(User u) {
        //add TCPConnection to the cache
        this.cache.add(u);
    }

    /**
     *
     * @param s
     * @return TCPConnection
     */
    public User getUser(Socket s) {
        //access TCPConnection from the cache
        for(int i = 0; i < cache.size();i++) {
            if(cache.get(i).getUserConnection().getSocket() == s) {
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
    public User getUser(byte[] ip, int p) {
        for(int i = 0; i < this.cache.size();++i) {
            if(cache.get(i).getUserConnection().getSocket().getPort() == p) {
                if(Arrays.equals(cache.get(i).getUserConnection().getSocket().getInetAddress().getAddress(),ip)) {
                    return cache.get(i);
                }
            }
        }
        return null;
    }
}
