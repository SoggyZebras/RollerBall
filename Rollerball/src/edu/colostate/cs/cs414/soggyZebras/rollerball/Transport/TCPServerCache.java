package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Transport.TCPConnection;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class TCPServerCache {

    private ArrayList<User> cache;
    private ArrayList<TCPConnection> connections;

    public TCPServerCache(){
        //initializes the arraylist
        this.cache = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public boolean UserLoggedIn(int id){
        for(TCPConnection c: connections){
            if(c.getConID() == id){
                return true;
            }
        }
        return false;
    }

    protected void addUser(User u) {
        //add TCPConnection to the cache
        this.cache.add(u);
    }

    public TCPConnection getConnection(int id){
        for(TCPConnection c : connections){
            if(c.getConID() == id){
                return c;
            }
        }
        return null;
    }

    /**
     *
     * @param s
     * @return TCPConnection
     */
    public User getUser(Socket s) {
        //access TCPConnection from the cache
        for(int i = 0; i < cache.size();i++) {
            if(getConnection(cache.get(i).getUserID()).getSocket() == s) {
                return cache.get(i);
            }
        }
        return null;
    }

    public TCPConnection getUserCon(Socket s){
        //access TCPConnection from the cache
        for(int i = 0; i < connections.size();i++) {
            if(getConnection(cache.get(i).getUserID()).getSocket() == s) {
                return connections.get(i);
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
            if(u.getUsername().equals(s)){
                return u;
            }
        }
        return null;
    }

    public void addConnection(TCPConnection c){
        connections.add(c);
    }

    public void removeConnection(int id){
        for(TCPConnection c: connections){
            if(c.getConID() == id){
                connections.remove(c);
            }
        }
    }

    public ArrayList<User> getAllUsers(){
        return cache;
    }
}
