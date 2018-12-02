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

    public User getUser(int i){
        for(User u : getAllUsers()){
            if(u.getUserID() == i){
                return u;
            }
        }
        return null;
    }

    public User getUser(String s){
        for(User u : getAllUsers()){
            if(u.getUsername() == s){
                return u;
            }
        }
        return null;
    }

    public ArrayList<User> getAllUsers(){
        return cache;
    }
}
